/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * Add your docs here.
 */
public class XBoxButton extends Button implements CMHDigitalInput{
    private XboxController controller;
    private int buttonNumber;

    public XBoxButton(XboxController controller, int buttonNumber){
        this.controller = controller;
        this.buttonNumber = buttonNumber;
    }

    @Override
    public boolean get(){
        return controller.getRawButton(buttonNumber);
    }

    public class RawButton {
        public static final int A = 1;
        public static final int B = 2;
        public static final int X = 3;
        public static final int Y = 4;
        public static final int LB = 5;
        public static final int RB = 6;
        public static final int BACK_BUTTON = 7;
        public static final int START_BUTTON = 8;
        public static final int LEFT_STICK = 9;
        public static final int RIGHT_STICK = 10;
    }

}
