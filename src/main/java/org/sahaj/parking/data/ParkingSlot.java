package org.sahaj.parking.data;

import lombok.Getter;

/**
 * The type Parking slot.
 */
public class ParkingSlot {

    @Getter
    private int id;

    /**
     * Instantiates a new Parking slot.
     *
     * @param id the id
     */
    public ParkingSlot(int id) {
        this.id = id;
    }

}