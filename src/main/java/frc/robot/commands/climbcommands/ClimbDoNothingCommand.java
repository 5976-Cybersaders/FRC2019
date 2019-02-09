/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climbcommands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbDoNothingCommand extends Command {

  private DoubleSolenoid front;
  private DoubleSolenoid back;

  public ClimbDoNothingCommand(ClimbSubsystem climbSubsystem) {
    this.front = climbSubsystem.getFrontDoubleSolenoid();
    this.back = climbSubsystem.getBackDoubleSolenoid();
    requires(climbSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    this.front.set(DoubleSolenoid.Value.kOff);
    this.back.set(DoubleSolenoid.Value.kOff);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
