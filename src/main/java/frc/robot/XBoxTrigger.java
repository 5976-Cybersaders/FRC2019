/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Add your docs here.
 */
public class XBoxTrigger implements CMHDigitalInput {

    private XboxController controller;
    private GenericHID.Hand hand;

    public XBoxTrigger(XboxController controller, GenericHID.Hand hand) {
        this.controller = controller;
        this.hand = hand;
    }

    @Override
    public boolean get(){
        return controller.getTriggerAxis(hand) > 0.5;
    }
}
