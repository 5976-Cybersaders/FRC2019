/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.DoNothingCommand;

/**
 * Add your docs here.
 */
public class CargoIntakeSubsystem extends Subsystem {

  private WPI_TalonSRX talon;

  public CargoIntakeSubsystem(int port){
    this.talon = new WPI_TalonSRX(port);
  }

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new DoNothingCommand(10));
  }

  public WPI_TalonSRX getTalon(){
    return this.talon;
  }
}
