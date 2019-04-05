/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Vector;

/**
 * Add your docs here.
 */
public class SmartDashboardMap {
    
    // Drive Talons
    private static final Vector<JudanValue> all = new Vector<>();
    /*
    public static final JudanValue kPFL = makeValue("P-Value FL", 0.04);
    public static final JudanValue kIFL = makeValue("I-Value FL", 0);
    public static final JudanValue kDFL = makeValue("D-Value FL", 0.4);
    public static final JudanValue kPFR = makeValue("P-Value FR", 0.04);
    public static final JudanValue kIFR = makeValue("I-Value FR", 0);
    public static final JudanValue kDFR = makeValue("D-Value FR", 0.4);
    public static final JudanValue kPRL = makeValue("P-Value RevL", 0.04);
    public static final JudanValue kIRL = makeValue("I-Value RevL", 0);
    public static final JudanValue kDRL = makeValue("D-Value RevL", 0.4);
    public static final JudanValue kPRR = makeValue("P-Value RevR", 0.04);
    public static final JudanValue kIRR = makeValue("I-Value RevR", 0);
    public static final JudanValue kDRR = makeValue("D-Value RevR", 0.4);
    public static final JudanValue ALLOWABLE_ERROR = makeValue("Allowable Error", 200);

    public static final JudanValue PEAK_VOLTAGE = makeValue("Peak Voltage", 0.5);
    public static final JudanValue NOMINAL_VOLTAGE = makeValue("Nominal Voltage", 0.25);
    public static final JudanValue RAMP_RATE = makeValue("Ramp Rate", 0);
    public static final JudanValue SIDE_INVERSION = makeValue("Inverted Side", "RIGHT");
    */

    // Lift Encoders
    public static final JudanValue LIFT_kP = makeValue("P-Value Lift", 0.4);
    public static final JudanValue LIFT_kI = makeValue("I-Value Lift", 0);
    public static final JudanValue LIFT_kD = makeValue("D-Value Lift", 0);
    public static final JudanValue LIFT_ALLOWABLE_ERROR = makeValue("Lift Allowable Error", 100);
    public static final JudanValue LIFT_PEAK_VOLTAGE = makeValue("Lift Peak Voltage", 1);
    public static final JudanValue LIFT_NOMINAL_VOLTAGE = makeValue("Lift Nominal Voltage", 0.6);

    public static final JudanValue LIFT_ENCODER_POSITION = makeValue("Lift Encoder Position", -1);
    public static final JudanValue LIFT_SPEED = makeValue("Lift Speed", -1);

    /* scoring heights for lift
    low on all
    mid hatch on rocket
    mid cargo on rocket
    cargo on shuttle
    */
    //TODO: establish defaults for these lift position values
    public static final JudanValue LIFT_TO_LOW_POS_TICKS = makeValue("Lift low position inches", 0);
    public static final JudanValue LIFT_TO_MID_HATCH_ROCKET_POS_TICKS = makeValue("Lift mid rocket inches", 1);
    //public static final JudanValue LIFT_TO_HIGH_HATCH_ROCKET_POS_TICKS = makeValue("Lift high rocket", 2);

    public static final JudanValue LIFT_TO_ROCKET_LOW_CARGO_POS_TICKS = makeValue("Lift low rocket cargo", 1);
    public static final JudanValue LIFT_TO_MID_CARGO_ROCKET_POS_TICKS = makeValue("Lift mid rocket cargo", 3);
    
    public static final JudanValue LIFT_TO_CARGO_PICKUP_POS_TICKS = makeValue("Lift pickup cargo", 0);

    public static final JudanValue LIFT_TO_SHUTTLE_CARGO = makeValue("Lift shuttle cargo", 2);

    public static final JudanValue LIFT_CURRENT_TICK_COUNT = makeValue("Lift tick count", 0);

    // Vision
    // Used to determine when snapping is complete.  We need 'n' iterations where Tx is w/in +/- this value in degrees.
    public static final JudanValue VISION_DEADBAND = makeValue("Vision_Deadband", 1.5);
    // Used when snapping to determine snap speed proportional to Tx.
    public static final JudanValue VISION_KP = makeValue("Vision_KP", -0.02);
    // Used when snapping to help ensure enough speed is applied to force robot into the vision deadband.
    public static final JudanValue VISION_MIN_CMD = makeValue("Vision_Min_Cmd", -0.2); // previously -0.1
    // Used when driving after a snap.  A value of -1 will cause the robot to drive straight forward.  The larger the positive value the harder the robot will overdrive.
    public static final JudanValue VISION_OVER_DRIVE_FACTOR = makeValue("Vision Overdrive", -.075);
    // Used to control rate of acceleration/deceleration during drive mode.  
    public static final JudanValue VISION_DRIVE_FACTOR_FOR_SPEED = makeValue("Vision Factor speed", 30);
    // Max speed allowed when driving after a snap.
    public static final JudanValue VISION_DRIVE_MODE_SPEED = makeValue("Vision drive mode speed", 0.5);
    // Used to stop from driving forward.  A lower value stops us sooner.
    public static final JudanValue VISION_DRIVE_MAX_TA = makeValue("Vision Max TA", 3.5);
    // Used to control how far we can drive until we consider the target is about to go out of view.
    public static final JudanValue VISION_DRIVE_MAX_TX = makeValue("Vision Max TX", 34);
    // Used to limit max speed during snapping.  The idea is, we may be able to limit oscillation if we don't go too fast during a snap.
    public static final JudanValue VISION_SNAP_MAX_SPEED = makeValue("Vision Snap Max Speed", .5);
    // Determines number of cycles within deadband to exit snap mode.
    public static final JudanValue DEADBAND_COUNTER = makeValue("DeadbandCounter", 6);

    // Used for reporting output only.
    public static final JudanValue VISION_TX = makeValue("Vision_tx", 90);
    // Used for reporting output only.
    public static final JudanValue VISION_LEFT_SPEED = makeValue("Vision left speed", 0);
    // Used for reporting output only.
    public static final JudanValue VISION_RIGHT_SPEED = makeValue("Vision right speed", 0);

    // Used in old commands only.
    public static final JudanValue PIPELINE_NUMBER_CHANGE = makeValue("PipelineChange", 9);
    public static final JudanValue VISION_CONTROL_COUNT = makeValue("Vision control count", 1);
    public static final JudanValue VISION_DRIVER_CONTROL_COUNT = makeValue("Vision_Driver control count", 1);
    public static final JudanValue VISION_STICK_DEADBAND = makeValue("Vision_stick deadband", 0.10);
    public static final JudanValue VISION_TX_BEFORE_CORRECTION = makeValue("Vision_tx before correction", 90);
    public static final JudanValue VISION_FIRST_TX_BEFORE_CORRECTION = makeValue("Vision_first_tx BC", 90);
    public static final JudanValue VISION_AUTODRIVE_SPEED = makeValue("Vision autodrive speed", 0.5);
    // End used in old commans only.

    public static final JudanValue LIFT_UPPER_TICK_LIMIT = makeValue("Vision upper tick limit", 28000);
    public static final JudanValue LIFT_LOWER_TICK_LIMIT = makeValue("Vision upper tick limit", 0);
    public static final JudanValue LIFT_SPEED_ADJUSTMENT = makeValue("Vision Speed Adjustment", 4);
    


    

    public static void reportAll() {
        all.forEach(SmartDashboardMap::report);
    }

    private static JudanValue makeValue(String name, double defaultValue) {
        JudanValue value = new JudanValue(name, defaultValue);
        all.add(value);
        return value;
    }

    private static JudanValue makeValue(String name, String defaultValue) {
        JudanValue value = new JudanValue(name, defaultValue);
        all.add(value);
        return value;
    }

    private static void report(JudanValue variable) {
        System.out.println(variable.getKey() + ": " + variable.getDouble());
    }
}
