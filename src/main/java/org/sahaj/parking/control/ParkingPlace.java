package org.sahaj.parking.control;

import lombok.Getter;
import org.sahaj.parking.utils.Constants;
import org.sahaj.parking.utils.Log;
import org.sahaj.parking.utils.Utils;
import org.sahaj.parking.data.*;
import org.sahaj.parking.model.Model;

import java.util.*;

/**
 * Class for Parking place.
 * Represents instance for one parking place.
 * Every parking place will be one of the supported model type
 */
public class ParkingPlace {

    @Getter
    private ParkingName parkingName;

    private Model model;

    private TimeZone timeZone;

    private int nextTicketNumber;
    /**
     * Map for the Alloted parkings places for vehicle.
     */
    Map<Integer, Map<Ticket, ParkingSlot>> allotedParkings;
    /**
     * The Available parkings spots for vehicle.
     */
    Map<Integer, Queue<ParkingSlot>> availableParkings;

    /**
     * Map to represent mapping between vehicle type and slot type.
     */
    Map<VehicleType, Integer> vehicleTypeToSlotType;

    /**
     * Instantiates a new Parking place.
     *
     * @param parkingName the parking name
     * @param model       the model for given parking places (Airport/Mall/Stadium)
     * @param spotsFile   file containing spots available with parking place for different vehicle
     * @param timeZone    time zone for parking place.
     */
    public ParkingPlace(ParkingName parkingName, Model model, String spotsFile, TimeZone timeZone) {
        this.timeZone = timeZone;
        this.model = model;
        this.parkingName = parkingName;
        allotedParkings = new HashMap<>();
        availableParkings = new HashMap<>();
        vehicleTypeToSlotType = new HashMap<>();
        nextTicketNumber = 1;

        Integer slotType = 1;
        List<String> spots = Utils.readFromFile(spotsFile);
        // remove header
        spots.remove(0);
        for (String line : spots) {


            StringTokenizer csvToken = new StringTokenizer(line, ",");

            StringTokenizer vehicleTypeToken = new StringTokenizer(csvToken.nextToken(), "/");

            while (vehicleTypeToken.hasMoreTokens()) {
                vehicleTypeToSlotType.put(VehicleType.valueOf(vehicleTypeToken.nextToken()), slotType);
            }

            Integer numSlots = Integer.parseInt(csvToken.nextToken());
            availableParkings.putIfAbsent(slotType, new LinkedList<>());

            for(int i = 1; i <= numSlots; i++) {
                availableParkings.get(slotType).add(new ParkingSlot(i));
            }

            slotType++;
        }
    }

    /**
     * Parks a give vehicle and issues a ticket.
     *
     * @param vehicleType the vehicle type
     * @return the ticket
     */
    public Ticket park(VehicleType vehicleType) {

        Integer slotType = vehicleTypeToSlotType.get(vehicleType);

        if (availableParkings.get(slotType).isEmpty()) {
            Log.w(String.format("Creating empty ticket, available Parking for given vehicle type %s is not available.", vehicleType));
            return new Ticket();
        }

        ParkingSlot slot = availableParkings.get(slotType).remove();
        allotedParkings.putIfAbsent(slotType, new HashMap<>());
        Ticket ticket = new Ticket(nextTicketNumber++, slot, Constants.utcNow.getTime(), timeZone, vehicleType, parkingName);
        allotedParkings.get(slotType).put(ticket , slot);

        return ticket;
    }

    /**
     * Un-park the vehicle for given ticket and issues a reciept.
     *
     * @param ticket the ticket
     * @return the receipt
     */
    public Receipt unPark(Ticket ticket) {

        if (ticket == null) {
            Log.e("Presented ticket is invalid. ticket => null");
            return new Receipt("Presented ticket is invalid. ticket => null");
        }

        Integer slotType = vehicleTypeToSlotType.get(ticket.getVehicleType());

        int fees = model.computeFees(ticket.getStart(), Constants.utcNow.getTime(), ticket.getVehicleType());
        int totalHours = model.computeHours(ticket.getStart(), Constants.utcNow.getTime(), ticket.getVehicleType());
        ParkingSlot slot = allotedParkings.get(slotType).remove(ticket);
        availableParkings.get(slotType).add(slot);

        return new Receipt(ticket.getTicketNumber(), ticket.getStart(), Constants.utcNow.getTime(), fees, timeZone, parkingName, totalHours);
    }
}
