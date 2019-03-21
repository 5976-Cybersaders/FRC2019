/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.liftcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.drivetraincommands.DriveStraightCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.LiftSubsystem;

public class ExtractHookAfterHatchDeliveryCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ExtractHookAfterHatchDeliveryCommandGroup(DriveTrainSubsystem driveTrain, LiftSubsystem liftSubsystem) {
    addSequential(RaiseLiftToFixedPositionCommand.LowerLiftByInches(liftSubsystem, 10)); //TODO: how many ticks?
    addSequential(new DriveStraightCommand(driveTrain, -0.3)); // negative bc driving backwards
  }
}
