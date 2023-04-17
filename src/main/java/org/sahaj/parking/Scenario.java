package org.sahaj.parking;

import org.sahaj.parking.data.*;
import org.sahaj.parking.control.Controller;
import org.sahaj.parking.utils.Constants;
import org.sahaj.parking.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Scenario class representing one flow of park-unpark requet-response.
 */
public class Scenario {

    private Controller controller;
    private String requestFile;
    private String responseFile;
    private ParkingName parkingName;
    /**
     * The Responses.
     */
    List<Response> responses;
    /**
     * The Requests.
     */
    List<Request> requests;

    /**
     * Instantiates a new Scenario.
     *
     * @param requestFile  the request file
     * @param responseFile the response file
     * @param parkingPlace the parking place
     */
    public Scenario(String requestFile, String responseFile, String parkingPlace) {
        controller = Controller.getController();
        this.requestFile = requestFile;
        this.responseFile = responseFile;
        parkingName = controller.getParkingName(parkingPlace);
        requests = parseRequests();
        responses = new ArrayList<>();
    }

    /**
     * Run scenario.
     */
    public void runScenario() {
        for (Request request : requests) {
            Response response = sendRequest(request, parkingName, responses);
            responses.add(response);
        }
        serializeResult();
    }

    private Response sendRequest(Request request, ParkingName parkingName, List<Response> oldResponses) {

        RequestType requestType = request.getRequestType();
        Response response;
        switch (requestType) {
            case Park:
                response = controller.park(request, parkingName);;
                break;
            case Unpark:
                int reference = request.getReference();
                Ticket ticket = (Ticket) oldResponses.get(reference - 1);
                Response reciept = controller.unPark(ticket);
                response = reciept;
                break;
            case Wait:
                String delay = request.getObject();
                Integer timeVal = Integer.parseInt(delay.substring(0, delay.indexOf(" ")));
                String durationType = delay.substring(delay.indexOf(" ") + 1);
                Long mSec = 1L;
                switch (durationType) {
                    case "sec" :
                        mSec *= timeVal * 1000;
                        break;
                    case "min" :
                        mSec *= timeVal * 1000 * 60;
                        break;
                    case "hour" :
                        mSec *= timeVal * 1000 * 60 * 60;
                        break;
                    case "day" :
                        mSec *= timeVal * 1000 * 60 * 60 * 24;
                        break;
                    default:
                        mSec *= 1;
                        break;
                }
                Constants.utcNow.setTime(Constants.utcNow.getTime() + mSec);
                response = new Response("Increased time by " + request.getObject());
                break;
            default:
                response = new Response("Invalid input action " + request.toString());
                break;
        }
        return response;
    }

    private void serializeResult() {
        StringBuilder output = new StringBuilder();

        output.append("Request file = " + requestFile);
        output.append(Constants.nextLine);
        output.append("Response file = " + responseFile);
        output.append(Constants.nextLine);
        output.append(Constants.nextLine);

        for (int i = 0; i < requests.size(); i++) {
            output.append("Serial number : " + requests.get(i).getSerialNumber());
            output.append(Constants.nextLine);
            output.append("Request       : ");
            output.append(requests.get(i));
            output.append(Constants.nextLine);
            output.append("Response      : ");
            output.append(responses.get(i));
            output.append(Constants.nextLine);
            output.append(Constants.nextLine);
        }

        System.out.println(output);
        Utils.writeToFile(responseFile, output.toString());
    }

    private List<Request> parseRequests() {

        List<Request> inputs = new ArrayList<>();

        List<String> lines = Utils.readFromFile(requestFile);
        lines.remove(0);

        for (String line : lines) {
            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
            Integer seqNumber = Integer.parseInt(stringTokenizer.nextToken());
            RequestType requestType = RequestType.valueOf(stringTokenizer.nextToken());
            String obj = stringTokenizer.nextToken();
            Integer ref = stringTokenizer.hasMoreTokens() ? Integer.parseInt(stringTokenizer.nextToken()) : -1;

            inputs.add(new Request(seqNumber, requestType, obj, ref));
        }

        return inputs;
    }
}
