/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climbcommands;

import frc.robot.subsystems.BackClimbSubsystem;

public class ActuateBackPistonCommand extends ActuatePistonCommand {
  public ActuateBackPistonCommand(BackClimbSubsystem climbSubsystem) {
    super(climbSubsystem, climbSubsystem.getSolenoid());
  }
}
