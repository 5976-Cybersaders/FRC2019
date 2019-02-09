/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.cargointakecommands.CargoIntakeCommand;

/**
 * Add your docs here.
 */
public class CargoIntakeSubsystem extends Subsystem {

  private WPI_TalonSRX talon;
  private OI oi;

  public CargoIntakeSubsystem(OI oi){
    this.talon = new WPI_TalonSRX(RobotMap.INTAKE_TALON_ID);
    this.oi = oi;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new CargoIntakeCommand(this.oi.getDriverController(), this));
  }

  public WPI_TalonSRX getTalon(){
    return this.talon;
  }
}
