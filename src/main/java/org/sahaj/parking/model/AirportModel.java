package org.sahaj.parking.model;

import org.sahaj.parking.utils.Utils;
import org.sahaj.parking.data.VehicleType;

import java.util.*;

/**
 * Model class to represent parking places like Airport.
 */
public class AirportModel extends Model {

    /**
     * List of hour->Fees for each vehicle type.
     */
    Map<VehicleType, List<Integer>> fees;
    /**
     * Fixed Daily rate for each vehicle type.
     */
    Map<VehicleType, Integer> dailyRate;
    /**
     * The Daily rate thrshold for each vehicle type.
     */
    Map<VehicleType, Integer> dailyRateThrshold;

    /**
     * Instantiates a new Airport model.
     *
     * @param modelFile the model file
     */
    public AirportModel(String modelFile) {
        initFees(modelFile);
    }
    public void initFees(String modelFile) {
        fees = new HashMap<>();
        dailyRate = new HashMap<>();
        dailyRateThrshold = new HashMap<>();

        List<String> models = Utils.readFromFile(modelFile);

        // first line of file is a header, ignoring it
        models.remove(0);

        for (String line : models) {
            StringTokenizer tok = new StringTokenizer(line, ",");

            String vehicleTypes = tok.nextToken();
            Integer startInterval = Integer.parseInt(tok.nextToken());
            Integer endInterval = Integer.parseInt(tok.nextToken());
            Integer currentFee = Integer.parseInt(tok.nextToken());
            Integer fee = currentFee;

            StringTokenizer vehicleTok = new StringTokenizer(vehicleTypes, "/");

            while (vehicleTok.hasMoreTokens()) {
                VehicleType vehicleType = VehicleType.valueOf(vehicleTok.nextToken());
                fees.putIfAbsent(vehicleType, new ArrayList<>());
                for (int hour = startInterval; hour < endInterval; hour++) {
                    fees.get(vehicleType).add(hour, fee);
                }
                if (endInterval == -1) {
                    dailyRate.put(vehicleType, currentFee);
                    dailyRateThrshold.put(vehicleType, startInterval);
                }
            }
        }
    }

    public int computeHours(Long start, Long end, VehicleType vehicleType) {
        return computeHours(start, end, false);
    }

    public int computeFees(Long start, Long end, VehicleType vehicleType) {

        int totalFees = 0;
        Integer totalHours = computeHours(start, end, false);

        if (totalHours >= 0) {

            // since it follows end time exclusivity and had daily charges, 24 hours
            // will be considered differently
            // 1. 24 hrs - 1sec => charges of 8-24 slot
            // 2. 24 hours => each day charge
            // 3. 24 hours + 1 sec => 2 * each day charge
            if (totalHours < dailyRateThrshold.get(vehicleType)) {
                totalFees = fees.get(vehicleType).get(totalHours);
            } else {
                boolean isFullDay = totalHours * 60 * 60 * 1000 == end - start ? true : false;
                int totalDays = totalHours/24 +(isFullDay ? 0 : 1);
                totalFees = totalDays * dailyRate.get(vehicleType);
            }
        }

        return totalFees;
    }
}