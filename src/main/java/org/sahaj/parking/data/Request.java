package org.sahaj.parking.data;

import lombok.Getter;
import net.snowflake.client.jdbc.internal.google.gson.Gson;

/**
 * Represent Request object to interact with controller to
 * issues parking requests to given parking place.
 */
public class Request {
    /**
     * The Serial number of request.
     */
    @Getter
    Integer serialNumber;
    /**
     * The Request type(park/unpark).
     */
    @Getter
    RequestType requestType;
    /**
     * The Object.
     */
    @Getter
    String object;
    /**
     * The Reference to earlier requests if this request is dependent on it.
     */
    @Getter
    Integer reference;

    /**
     * Instantiates a new Request.
     *
     * @param serialNumber the serial number
     * @param requestType  the request type
     * @param object       the object
     * @param reference    the reference
     */
    public Request(Integer serialNumber,
                   RequestType requestType,
                   String object,
                   Integer reference) {
        this.serialNumber = serialNumber;
        this.requestType = requestType;
        this.object = object;
        this.reference = reference;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
