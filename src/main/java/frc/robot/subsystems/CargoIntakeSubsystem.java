/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.cargointakecommands.CargoIntakeCommand;

/**
 * Add your docs here.
 */
public class CargoIntakeSubsystem extends Subsystem {

  private DoubleSolenoid doubleSolenoid; 
  private Talon talon;
  private OI oi;

  public CargoIntakeSubsystem(OI oi){
    this.doubleSolenoid =new DoubleSolenoid(4,5); 
    this.talon = new Talon(RobotMap.INTAKE_TALON_ID);
    this.oi = oi;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new CargoIntakeCommand(this.oi.getDriverController(), this));
  }

  public Talon getTalon(){
    return this.talon;
  }

  public DoubleSolenoid getDoubleSolenoid(){
    return this.doubleSolenoid;
  }
   
}
