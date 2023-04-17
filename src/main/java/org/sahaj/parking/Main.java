package org.sahaj.parking;

import org.sahaj.parking.data.*;
import org.sahaj.parking.utils.Log;
import org.sahaj.parking.utils.Utils;
import org.sahaj.parking.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * The Main class.
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Log.d("Parsing parking events from input files.");
        List<Scenario> scenarios = createScenarios();
        Log.d("Running parking events parsed from input files.");
        runScenarios(scenarios);

        return;
    }

    /**
     * Run scenarios.
     *
     * @param scenarios the scenarios
     */
    public static void runScenarios(List<Scenario> scenarios) {
        for (int i = 0; i < scenarios.size(); i++) {
            Log.d("Scenario " + (int)(i + 1));
            scenarios.get(i).runScenario();
        }
    }

    /**
     * Create scenarios list.
     *
     * @return the list
     */
    public static List<Scenario> createScenarios() {

        List<Scenario> scenarios = new ArrayList<>();

        for (ModelType modeltype : ModelType.values()) {
            String baseModelPath = Utils.createFilePathString(Constants.clientDir, modeltype.toString());
            List<String> parkingPlaces = Utils.getAllChildDirsName(baseModelPath);
            for (String parkingPlace : parkingPlaces) {
                String requestFile = Utils.createFilePathString(baseModelPath, parkingPlace, "request.csv");
                String responseFile = Utils.createFilePathString(baseModelPath, parkingPlace, "response.csv");
                scenarios.add(new Scenario(requestFile, responseFile, parkingPlace));
            }
        }

        return scenarios;
    }
}
