/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.climbcommands.ClimbDoNothingCommand;

/**
 * Add your docs here.
 */
public class FrontClimbSubsystem extends ClimbSubsystem {

  private Compressor compressor;

  public FrontClimbSubsystem(){
    super(new DoubleSolenoid(0,1));
    compressor = new Compressor();
    compressor.setClosedLoopControl(true);
  }

}
