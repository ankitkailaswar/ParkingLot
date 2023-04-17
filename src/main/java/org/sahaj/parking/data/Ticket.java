package org.sahaj.parking.data;

import lombok.Getter;
import net.snowflake.client.jdbc.internal.google.gson.Gson;
import org.sahaj.parking.utils.Utils;

import java.util.TimeZone;

/**
 * Ticket is a response object for park requests.
 */
public class Ticket extends Response {

    @Getter
    private String message;
    @Getter
    private Boolean isValidTicket;
    @Getter
    private Integer ticketNumber;
    @Getter
    private ParkingSlot slot;
    @Getter
    private transient Long start;
    @Getter
    private String startTime;
    @Getter
    private ParkingName parkingName;
    @Getter
    private VehicleType vehicleType;

    /**
     * Instantiates a new Ticket.
     *
     * @param ticketNumber the ticket number
     * @param slot         the slot
     * @param start        the start
     * @param timeZone     the time zone
     * @param vehicleType  the vehicle type
     * @param parkingName  the parking name
     */
    public Ticket(int ticketNumber, ParkingSlot slot, Long start, TimeZone timeZone, VehicleType vehicleType, ParkingName parkingName) {
        this.ticketNumber = ticketNumber;
        this.slot = slot;
        this.start = start;
        this.startTime = Utils.getLocalTime(start, timeZone);
        isValidTicket = true;
        this.vehicleType = vehicleType;
        this.parkingName = parkingName;
        message = "Have a great day !!!!";
    }

    /**
     * Instantiates a new Ticket.
     */
    public Ticket() {
        message = "Sorry, parking not available. All slots are occupied.";
        isValidTicket = false;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
