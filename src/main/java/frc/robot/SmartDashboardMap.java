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

    // Vision
    public static final JudanValue VISION_DEADBAND = makeValue("Vision_Deadband", 1.5);
    public static final JudanValue VISION_KP = makeValue("Vision_KP", -0.025);
    public static final JudanValue VISION_MIN_CMD = makeValue("Vision_Min_Cmd", -0.1);
    public static final JudanValue VISION_CONTROL_COUNT = makeValue("Vision control count", 1);
    public static final JudanValue VISION_DRIVER_CONTROL_COUNT = makeValue("Vision_Driver control count", 1);
    public static final JudanValue VISION_TX = makeValue("Vision_tx", 90);

    //debug


    

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
