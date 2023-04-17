package org.sahaj.parking.utils;

import org.sahaj.parking.utils.Utils;
import java.sql.Timestamp;

/**
 * The type Constants.
 */
public class Constants {

    /**
     * The constant for nextLine string.
     */
    public static String nextLine = "\n";
    /**
     * Current directory.
     */
    public static String currentDir = System.getProperty("user.dir");

    /**
     * The constant representing utcNow.
     */
    public static Timestamp utcNow = new Timestamp(System.currentTimeMillis());

    /**
     * The constant serverBaseDir.
     */
    public static String serverBaseDir = Utils.createFilePathString(currentDir, "Data", "Server");
    /**
     * The constant modelBaseDir.
     */
    public static String modelBaseDir = Utils.createFilePathString(serverBaseDir, "Models");
    /**
     * The constant airportModelFile.
     */
    public static String airportModelFile = Utils.createFilePathString(modelBaseDir, "Airport", "model.csv");
    /**
     * The constant mallModelFile.
     */
    public static String mallModelFile = Utils.createFilePathString(modelBaseDir, "Mall", "model.csv");
    /**
     * The constant stadiumModelFile.
     */
    public static String stadiumModelFile = Utils.createFilePathString(modelBaseDir, "Stadium", "model.csv");
    /**
     * The constant parkingPlacesBaseDir.
     */
    public static String parkingPlacesBaseDir = Utils.createFilePathString(serverBaseDir, "ParkingPlaces");

    /**
     * Gets spots file.
     *
     * @param parkingNamePath the parking name path
     * @return the spots file
     */
    public static String getSpotsFile(String parkingNamePath) {
        return Utils.createFilePathString(parkingNamePath, "spots.csv");
    }

    /**
     * The constant clientDir.
     */
    public static String clientDir = Utils.createFilePathString(currentDir, "Data", "Client", "Events");
}
