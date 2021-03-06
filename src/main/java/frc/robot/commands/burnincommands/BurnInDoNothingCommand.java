/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.burnincommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.burnincommands.DriveTrainBurnInSubsystem;

public class BurnInDoNothingCommand extends Command {

  private DriveTrainBurnInSubsystem driveTrain;

  public BurnInDoNothingCommand(DriveTrainBurnInSubsystem driveTrain, int secondsTimeout) {
    this.setTimeout(secondsTimeout);
    this.driveTrain = driveTrain;
    requires(driveTrain);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    this.driveTrain.getLeftTalon().set(0);
    this.driveTrain.getRightTalon().set(0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return this.isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
