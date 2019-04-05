/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climbcommands.tempcommands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.SolenoidBurnSubsystem;

public class SolenoidBurnCommand extends Command {

  SolenoidBurnSubsystem subsystem;
  int counter = 0;
  DoubleSolenoid.Value direction;

  public SolenoidBurnCommand(SolenoidBurnSubsystem subsystem) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.subsystem = subsystem;
    requires(subsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Initing solenoid command******");
    counter = 0;
    direction = DoubleSolenoid.Value.kForward;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    counter += 1;
    System.out.println(counter + " Execute " + direction);
    if(counter == 10) {
      if(direction == Value.kForward) {
        direction = Value.kReverse;
      } else {
        direction = Value.kForward;
      }
      System.out.println("Setting solenoid direction to " + direction.toString() + "   ********************8");
      counter = 0;
    }
    subsystem.getSolenoid().set(direction);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.out.println("Finsiehd!");
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("ENded");
  }

  


}
