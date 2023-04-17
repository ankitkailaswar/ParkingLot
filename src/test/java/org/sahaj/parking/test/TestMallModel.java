package org.sahaj.parking.test;

import junit.framework.TestCase;
import org.sahaj.parking.data.VehicleType;
import org.sahaj.parking.model.MallModel;
import org.sahaj.parking.utils.Constants;
import org.sahaj.parking.utils.Utils;

/**
 * The type Test mall model.
 */
public class TestMallModel extends TestCase {

    /**
     * The Mall model.
     */
    protected MallModel mallModel;
    private Long now = 0L;
    private Long exact10hrsFromNow = 10 * 60 * 60 * 1000L;
    private Long justAMomentBefore10hrsFromStart = 10 * 60 * 60 * 1000L - 1L;
    private Long justAMomentAfter10hrsFromStart = 10 * 60 * 60 * 1000L + 1L;
    private int motorcycleHourlyFees = 10;
    private int carHourlyFees = 20;
    private int busHourlyFees = 50;
    private String mallModelFile = Utils.createFilePathString(Constants.currentDir, "src", "test", "resources", "mall_model.csv");

    protected void setUp() {
        mallModel = new MallModel(mallModelFile);
    }

    /**
     * Test motorcycle fees for different parking times and scenarios.
     */
    public void testMotorcycleFees() {

        String dir = Constants.currentDir;
        int actualFees = mallModel.computeFees(now, exact10hrsFromNow, VehicleType.Motorcycle);
        int expectedFees = 10 * motorcycleHourlyFees;
        assertTrue(actualFees == expectedFees);

        actualFees = mallModel.computeFees(now, justAMomentAfter10hrsFromStart, VehicleType.Motorcycle);
        expectedFees = 11 * motorcycleHourlyFees;
        assertTrue(actualFees == expectedFees);

        actualFees = mallModel.computeFees(now, justAMomentBefore10hrsFromStart, VehicleType.Motorcycle);
        expectedFees = 10 * motorcycleHourlyFees;
        assertTrue(actualFees == expectedFees);
    }

    /**
     * Test car fees for different parking times and scenarios.
     */
    public void testCarFees() {

        int actualFees = mallModel.computeFees(now, exact10hrsFromNow, VehicleType.Car);
        int expectedFees = 10 * carHourlyFees;
        assertTrue(actualFees == expectedFees);

        actualFees = mallModel.computeFees(now, justAMomentAfter10hrsFromStart, VehicleType.Car);
        expectedFees = 11 * carHourlyFees;
        assertTrue(actualFees == expectedFees);

        actualFees = mallModel.computeFees(now, justAMomentBefore10hrsFromStart, VehicleType.Car);
        expectedFees = 10 * carHourlyFees;
        assertTrue(actualFees == expectedFees);

    }

    /**
     * Test bus fees for different parking times and scenarios.
     */
    public void testBusFees() {

        int actualFees = mallModel.computeFees(now, exact10hrsFromNow, VehicleType.Bus);
        int expectedFees = 10 * busHourlyFees;
        assertTrue(actualFees == expectedFees);

        actualFees = mallModel.computeFees(now, justAMomentAfter10hrsFromStart, VehicleType.Bus);
        expectedFees = 11 * busHourlyFees;
        assertTrue(actualFees == expectedFees);

        actualFees = mallModel.computeFees(now, justAMomentBefore10hrsFromStart, VehicleType.Bus);
        expectedFees = 10 * busHourlyFees;
        assertTrue(actualFees == expectedFees);

    }

}
