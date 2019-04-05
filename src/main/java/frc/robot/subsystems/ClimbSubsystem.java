/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.climbcommands.ClimbDoNothingCommand;

/**
 * Add your docs here.
 */
abstract public class ClimbSubsystem extends Subsystem {
  private DoubleSolenoid solenoid;

  public ClimbSubsystem(DoubleSolenoid solenoid){
    this.solenoid = solenoid; 
    this.solenoid.set(DoubleSolenoid.Value.kOff);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ClimbDoNothingCommand(this));
  }

  public DoubleSolenoid getSolenoid(){
    return solenoid;
  }
  

}
