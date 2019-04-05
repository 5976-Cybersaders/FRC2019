/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.cargointakecommands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.CargoIntakeSubsystem;

public class DeployCargoIntakeCommand extends Command {
  
  private DoubleSolenoid doubleSolenoid;

  public DeployCargoIntakeCommand(CargoIntakeSubsystem cargoIntakeSubsystem) {
        // Use requires() here to declare subsystem dependencies
            // eg. requires(chassis);
    this.doubleSolenoid = cargoIntakeSubsystem.getDoubleSolenoid();
    requires(cargoIntakeSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //this.doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //TODO Add height limit to allow intake to drop
    this.doubleSolenoid.set(DoubleSolenoid.Value.kForward);
    System.out.println("*************Deploying intake****************");
  }

  //Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.doubleSolenoid.set(DoubleSolenoid.Value.kOff);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
