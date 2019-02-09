/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.liftcommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.subsystems.LiftSubsystem;

public class ManualMoveLiftCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */

  public ManualMoveLiftCommandGroup(XboxController controller, LiftSubsystem liftSubsystem) {
    setInterruptible(true);
    addSequential(new InitLiftTalonCommand(liftSubsystem));
    addSequential(new ManualMoveLiftCommand(liftSubsystem, controller));
  }
}
