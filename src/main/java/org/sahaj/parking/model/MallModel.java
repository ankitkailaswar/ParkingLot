package org.sahaj.parking.model;

import org.sahaj.parking.utils.Utils;
import org.sahaj.parking.data.VehicleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * The type Mall model.
 */
public class MallModel extends Model {
    /**
     * The Fees.
     */
    Map<VehicleType, Integer> fees;

    /**
     * Instantiates a new Mall model.
     *
     * @param modelFile the model file
     */
    public MallModel(String modelFile) {
        initFees(modelFile);
    }
    public void initFees(String modelFile) {
        fees = new HashMap<>();
        List<String> models = Utils.readFromFile(modelFile);

        // first line of file is a header, ignoring it
        models.remove(0);
        for (String line : models) {
            StringTokenizer tok = new StringTokenizer(line, ",");

            String vehicleTypes = tok.nextToken();
            Integer fee = Integer.parseInt(tok.nextToken());

            StringTokenizer vehicleTok = new StringTokenizer(vehicleTypes, "/");

            while (vehicleTok.hasMoreTokens()) {
                VehicleType vehicleType = VehicleType.valueOf(vehicleTok.nextToken());
                fees.put(vehicleType, fee);
            }
        }
    }

    public int computeHours(Long start, Long end, VehicleType vehicleType) {
        return computeHours(start, end, true);
    }

    public int computeFees(Long start, Long end, VehicleType vehicleType) {

        Integer totalHours = computeHours(start, end, true);

        if (totalHours <= 0) return 0;
        return fees.get(vehicleType) * totalHours;
    }
}