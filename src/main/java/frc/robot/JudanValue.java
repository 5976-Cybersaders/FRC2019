/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * In commemoration of code ninja emeritus 2
 */
public class JudanValue {
    private String key;
    private double defaultValue;
    private String defaultString;

    public JudanValue(String key, double defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        SmartDashboard.putNumber(key, defaultValue);
    }

    public JudanValue(String key, String defaultString) {
        this.key = key;
        this.defaultString = defaultString;
        SmartDashboard.putString(key, defaultString);
    }

    public double getValue() {
        return SmartDashboard.getNumber(key, defaultValue);
    }

    public int getIntValue() {
        return (int) getValue();
    }

    public String getKey() {
        return key;
    }

    public double getDouble() {
        return getValue();
    }

    public String getString() {
        return SmartDashboard.getString(key, defaultString);
    }
}
