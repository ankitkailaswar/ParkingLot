package org.sahaj.parking.control;

import org.sahaj.parking.utils.Constants;
import org.sahaj.parking.utils.Log;
import org.sahaj.parking.utils.Utils;
import org.sahaj.parking.data.*;
import org.sahaj.parking.model.Model;

import java.util.*;

/**
 * Controller class,
 * 1. Initialises and maintains multiple parking places
 * 1. Routes parking requests to specific parking places.
 */
public class Controller {

    private Map<ParkingName, ParkingPlace> parkingPlaces;
    private Map<String, ParkingName> parkingNames;
    private Map<Ticket, ParkingPlace> ticketToParkingPlace;
    private ModelFactory modelFactory;

    private static Controller controller = null;

    /**
     * Returns controller instance.
     *
     * @return the controller
     */
    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    private Controller() {

        Log.d("Initialising controller...");

        // init primitive members
        parkingNames = new HashMap<>();
        parkingPlaces = new HashMap<>();
        ticketToParkingPlace = new HashMap<>();

        // init custom members
        modelFactory = new ModelFactory();

        // init parking places
        initParkingPlaces(ModelType.Airport);
        initParkingPlaces(ModelType.Mall);
        initParkingPlaces(ModelType.Stadium);

        Log.d("Initialised controller successfully.");


    }

    private void initParkingPlaces(ModelType modelType) {

        String modelBasePath = Utils.createFilePathString(Constants.parkingPlacesBaseDir, modelType.toString());
        List<String> places = Utils.getAllChildDirsName(modelBasePath);
        Model model = modelFactory.getModel(modelType);
        for (String place : places) {
            String basePath = Utils.createFilePathString(modelBasePath, place);
            String spotsFile = Constants.getSpotsFile(basePath);

            if (!Utils.fileExsists(spotsFile)) continue;

            ParkingName parkingName = new ParkingName(place);
            ParkingPlace parkingPlace = new ParkingPlace(parkingName, model, spotsFile, TimeZone.getDefault());
            parkingPlaces.put(parkingName, parkingPlace);
            parkingNames.put(place, parkingName);
        }
    }

    /**
     * Request to park a vehicle to given parking place.
     *
     * @param request     the request object, having vehicle specifications
     * @param parkingName name of parking place
     * @return the ticket issued for parking space
     */
    public Ticket park(Request request, ParkingName parkingName) {
        ParkingPlace parkingPlace = parkingPlaces.get(parkingName);
        Ticket ticket = parkingPlace.park(VehicleType.valueOf(request.getObject()));
        ticketToParkingPlace.put(ticket, parkingPlace);
        return ticket;
    }

    /**
     * Request to un-park a vehicle for given ticket.
     *
     * @param ticket the ticket issued while parking the vehicle
     * @return the receipt of parking with parking details.
     */
    public Receipt unPark(Ticket ticket) {
        if (!ticket.getIsValidTicket()) {
            return new Receipt("Presented ticket is invalid. ticket => " + ticket.toString());
        }

        ParkingPlace parkingPlace = parkingPlaces.get(ticket.getParkingName());
        return parkingPlace.unPark(ticket);
    }

    /**
     * Returns parking name object from its name.
     *
     * @param parkingNameString string of the parking name
     * @return the parking name object
     */
    public ParkingName getParkingName(String parkingNameString) {
        return parkingNames.get(parkingNameString);
    }
}