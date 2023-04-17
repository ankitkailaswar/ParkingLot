package org.sahaj.parking.model;

import org.sahaj.parking.utils.Utils;
import org.sahaj.parking.data.VehicleType;

/**
 * The type Model.
 */
public abstract class Model {
    /**
     * Init fees from model file.
     *
     * @param modelFile the model file
     */
    abstract void initFees(String modelFile);

    /**
     * Compute fees for given vehicle based on model type, park time and unpark time.
     *
     * @param start       the start
     * @param end         the end
     * @param vehicleType the vehicle type
     * @return the int
     */
    public abstract int computeFees(Long start, Long end, VehicleType vehicleType);

    /**
     * Compute hours in parking for given vehicle type.
     *
     * @param start       the start
     * @param end         the end
     * @param vehicleType the vehicle type
     * @return the int
     */
    public abstract int computeHours(Long start, Long end, VehicleType vehicleType);

    /**
     * Compute hours in parking for given vehicle type.
     *
     * @param start          the start
     * @param end            the end
     * @param isEndExclusive the is end exclusive
     * @return the int
     */
    protected int computeHours(Long start, Long end, boolean isEndExclusive) {
        return Utils.computeHours(start, end, isEndExclusive);
    }
}