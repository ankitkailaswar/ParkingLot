package org.sahaj.parking.data;

import lombok.Getter;
import net.snowflake.client.jdbc.internal.google.gson.Gson;
import org.sahaj.parking.utils.Utils;

import java.util.TimeZone;

/**
 * The type Receipt.
 */
public class Receipt extends Response {
    @Getter
    private int recieptNumber;
    @Getter
    private transient Long start;
    @Getter
    private transient Long end;
    @Getter
    private String startTime;
    @Getter
    private String endTime;
    @Getter
    private Integer totalHours;
    @Getter
    private Integer fees;
    @Getter
    private ParkingName parkingName;
    @Getter
    private String message;

    /**
     * Instantiates a new Receipt.
     *
     * @param recieptNumber the reciept number
     * @param start         the start time
     * @param end           the end time
     * @param fees          the fees for parking.
     * @param timeZone      the time zone for parking place
     * @param parkingName   the parking name
     * @param totalHours    the total hours vehicle was parked.
     */
    public Receipt(int recieptNumber, Long start, Long end, Integer fees, TimeZone timeZone, ParkingName parkingName, Integer totalHours) {
        this.recieptNumber = recieptNumber;
        this.start = start;
        this.end = end;
        this.startTime = Utils.getLocalTime(start, timeZone);
        this.endTime = Utils.getLocalTime(end, timeZone);
        this.totalHours = totalHours;
        this.fees = fees;
        this.parkingName = parkingName;
    }

    /**
     * Instantiates a new Receipt.
     *
     * @param message the message
     */
    public Receipt(String message) {
        this.message = "Sorry, Can not issue receipt. Reason : " + message;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}