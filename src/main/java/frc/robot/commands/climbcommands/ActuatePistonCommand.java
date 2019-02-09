/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climbcommands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.ClimbSubsystem;

/**
 * Add your docs here.
 */
public abstract class ActuatePistonCommand extends InstantCommand {
  /**
   * Add your docs here.
   */

  private DoubleSolenoid doubleSolenoid;

  public ActuatePistonCommand(ClimbSubsystem climbSubsystem, DoubleSolenoid doubleSolenoid) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.doubleSolenoid = doubleSolenoid;
    requires( climbSubsystem);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
  }

  @Override
  protected void execute(){
    if (DriverStation.getInstance().getMatchTime() < 30){
      if (doubleSolenoid.get().equals(DoubleSolenoid.Value.kForward)){
        doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
      } 
      else {
        doubleSolenoid.set(DoubleSolenoid.Value.kForward);
      }
    }
  }

}
