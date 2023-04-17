package org.sahaj.parking.data;

import lombok.Getter;

/**
 * The type Parking name.
 */
public class ParkingName {

    @Getter
    private String placeName;

    /**
     * Instantiates a new Parking name.
     *
     * @param parkingName the parking name
     */
    public ParkingName(String parkingName) {
        placeName = parkingName;
    }
}
