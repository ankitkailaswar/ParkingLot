package org.sahaj.parking.test;

import junit.framework.TestCase;
import org.sahaj.parking.data.VehicleType;
import org.sahaj.parking.model.AirportModel;
import org.sahaj.parking.utils.Constants;
import org.sahaj.parking.utils.Utils;

/**
 * Test cases to validate airport model.
 */
public class TestAirportModel extends TestCase {
    private String modelFile = Utils.createFilePathString(Constants.currentDir, "src", "test", "resources", "airport_model.csv");
    /**
     * The Model name.
     */
    protected AirportModel model;

    private Long now = 0L;
    private Long nextMomentFromNow = 1L;

    private Long exact8hrsFromNow = 8 * 60 * 60 * 1000L;
    private Long justAMomentBefore8hrsFromStart = 8 * 60 * 60 * 1000L - 1L;
    private Long justAMomentAfter8hrsFromStart = 8 * 60 * 60 * 1000L + 1L;

    private Long oneDay = 24 * 60 * 60 * 1000L;
    private Long justBeforeOneDay = 24 * 60 * 60 * 1000L - 1L;
    private Long justAfterOneDay = 24 * 60 * 60 * 1000L + 1L;

    private int motorcycle_0_1_Fees = 0;
    private int motorcycle_1_8_Fees = 40;
    private int motorcycle_8_24_Fees = 60;
    private int motorcycle_each_day_Fees = 80;


    private Long exact12hrsFromNow = 12 * 60 * 60 * 1000L;
    private Long justAMomentBefore12hrsFromStart = 12 * 60 * 60 * 1000L - 1L;
    private Long justAMomentAfter12hrsFromStart = 12 * 60 * 60 * 1000L + 1L;

    private int car_0_12_Fees = 60;
    private int car_12_24_Fees = 80;
    private int car_each_day_Fees = 100;

    protected void setUp() {
        model = new AirportModel(modelFile);
    }

    /**
     * Test motorcycle fees for different parking times and scenarios.
     */
    public void testMotorcycleFees() {

        int actualFees = model.computeFees(now, nextMomentFromNow, VehicleType.Motorcycle);
        int expectedFees = motorcycle_0_1_Fees;
        assertTrue(actualFees == expectedFees);

        actualFees = model.computeFees(now, exact8hrsFromNow, VehicleType.Motorcycle);
        expectedFees = motorcycle_8_24_Fees;
        assertTrue(actualFees == expectedFees);

        actualFees = model.computeFees(now, justAMomentBefore8hrsFromStart, VehicleType.Motorcycle);
        expectedFees = motorcycle_1_8_Fees;
        assertTrue(actualFees == expectedFees);

        actualFees = model.computeFees(now, justAMomentAfter8hrsFromStart, VehicleType.Motorcycle);
        expectedFees = motorcycle_8_24_Fees;
        assertTrue(actualFees == expectedFees);

        actualFees = model.computeFees(now, oneDay, VehicleType.Motorcycle);
        expectedFees = motorcycle_each_day_Fees;
        assertTrue(actualFees == expectedFees);

        actualFees = model.computeFees(now, justBeforeOneDay, VehicleType.Motorcycle);
        expectedFees = motorcycle_8_24_Fees;
        assertTrue(actualFees == expectedFees);

        actualFees = model.computeFees(now, justAfterOneDay, VehicleType.Motorcycle);
        expectedFees = 2 * motorcycle_each_day_Fees;
        assertTrue(actualFees == expectedFees);
    }

    /**
     * Test motorcycle fees for different parking times and scenarios.
     */
    public void testCarFees() {
        int actualFees = model.computeFees(now, nextMomentFromNow, VehicleType.Car);
        int expectedFees = car_0_12_Fees;
        assertTrue(actualFees == expectedFees);

        actualFees = model.computeFees(now, exact12hrsFromNow, VehicleType.Car);
        expectedFees = car_12_24_Fees;
        assertTrue(actualFees == expectedFees);

        actualFees = model.computeFees(now, justAMomentBefore12hrsFromStart, VehicleType.Car);
        expectedFees = car_0_12_Fees;
        assertTrue(actualFees == expectedFees);

        actualFees = model.computeFees(now, justAMomentAfter12hrsFromStart, VehicleType.Car);
        expectedFees = car_12_24_Fees;
        assertTrue(actualFees == expectedFees);

        actualFees = model.computeFees(now, oneDay, VehicleType.Car);
        expectedFees = car_each_day_Fees;
        assertTrue(actualFees == expectedFees);

        actualFees = model.computeFees(now, justBeforeOneDay, VehicleType.Car);
        expectedFees = car_12_24_Fees;
        assertTrue(actualFees == expectedFees);

        actualFees = model.computeFees(now, justAfterOneDay, VehicleType.Car);
        expectedFees = 2 * car_each_day_Fees;
        assertTrue(actualFees == expectedFees);

    }

}
