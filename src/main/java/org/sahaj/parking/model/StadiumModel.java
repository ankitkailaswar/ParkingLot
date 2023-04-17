package org.sahaj.parking.model;

import org.sahaj.parking.utils.Utils;
import org.sahaj.parking.data.VehicleType;
import java.util.*;

/**
 * The type Stadium model.
 */
public class StadiumModel extends Model {

    /**
     * The Fees.
     */
    Map<VehicleType, List<Integer>> fees;
    /**
     * The Flat perhour rate.
     */
    Map<VehicleType, Integer> flatPerhourRate;
    /**
     * The Flat perhour rate start hour.
     */
    Map<VehicleType, Integer> flatPerhourRateStartHour;


    /**
     * Instantiates a new Stadium model.
     *
     * @param modelFile the model file
     */
    public StadiumModel(String modelFile) {
        initFees(modelFile);
    }
    public void initFees(String modelFile) {
        fees = new HashMap<>();
        flatPerhourRate = new HashMap<>();
        flatPerhourRateStartHour = new HashMap<>();

        List<String> models = Utils.readFromFile(modelFile);
        // remove header
        models.remove(0);
        String oldVehicleTypes = "";
        Integer fee = 0;
        for (String line : models) {
            StringTokenizer tok = new StringTokenizer(line, ",");

            String vehicleTypes = tok.nextToken();
            Integer startInterval = Integer.parseInt(tok.nextToken());
            Integer endInterval = Integer.parseInt(tok.nextToken());
            Integer currentFee = Integer.parseInt(tok.nextToken());
            fee = currentFee + (vehicleTypes.equals(oldVehicleTypes) ? fee : 0);
            oldVehicleTypes = vehicleTypes;

            StringTokenizer vehicleTok = new StringTokenizer(vehicleTypes, "/");

            while (vehicleTok.hasMoreTokens()) {
                VehicleType vehicleType = VehicleType.valueOf(vehicleTok.nextToken());
                if (endInterval == -1) {
                    flatPerhourRate.put(vehicleType, currentFee);
                    flatPerhourRateStartHour.put(vehicleType, startInterval);
                } else {
                    fees.putIfAbsent(vehicleType, new ArrayList<>());

                    for (int hour = startInterval; hour < endInterval; hour++) {
                        fees.get(vehicleType).add(hour, fee);
                    }
                }
            }
        }

        return;
    }

    public int computeHours(Long start, Long end, VehicleType vehicleType) {
        return computeHours(start, end, true);
    }

    public int computeFees(Long start, Long end, VehicleType vehicleType) {

        int totalFees = 0;
        Integer totalHours = computeHours(start, end, true);

        if (totalHours > 0) {

            if (totalHours < fees.get(vehicleType).size()) {
                totalFees = fees.get(vehicleType).get(totalHours - 1);
            } else {
                Integer flatRateStartHour = flatPerhourRateStartHour.get(vehicleType);
                Integer flatHours = totalHours - flatRateStartHour;
                totalFees = fees.get(vehicleType).get(flatRateStartHour - 1) + flatPerhourRate.get(vehicleType) * flatHours;
            }
        }

        return totalFees;
    }
}