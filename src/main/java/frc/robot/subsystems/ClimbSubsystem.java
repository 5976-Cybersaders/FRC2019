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
public class ClimbSubsystem extends Subsystem {

  private DoubleSolenoid front;
  private DoubleSolenoid back;
  private Compressor compressor;

  public ClimbSubsystem(){
    this.front = new DoubleSolenoid(0, 1); //TODO: verify channel numbers on PCM for these args, insert into RobotMap
    this.back = new DoubleSolenoid(2, 3);
    compressor = new Compressor();
    compressor.setClosedLoopControl(true);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ClimbDoNothingCommand(this));
  }

  public DoubleSolenoid getFrontDoubleSolenoid() { return this.front; }
  public DoubleSolenoid getBackDoubleSolenoid() { return this.back; }
}
