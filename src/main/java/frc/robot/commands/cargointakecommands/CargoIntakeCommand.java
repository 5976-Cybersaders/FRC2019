/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.cargointakecommands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.subsystems.CargoIntakeSubsystem;

public class CargoIntakeCommand extends Command {

  private XboxController controller;
  private Talon talon;

  public CargoIntakeCommand(XboxController controller, CargoIntakeSubsystem cargoIntakeSubsystem) {
    this.controller = controller;
    this.talon = cargoIntakeSubsystem.getTalon();
    requires(cargoIntakeSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // left trigger goes backwards, right trigger goes forwards
  @Override
  protected void execute() {
    double rightTrigger = this.controller.getTriggerAxis(Hand.kRight);
    double leftTrigger = this.controller.getTriggerAxis(Hand.kLeft);
    double speed = leftTrigger > rightTrigger ? 
      -leftTrigger :
      rightTrigger;
    this.talon.set(adjustSpeed(speed));
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

  private double adjustSpeed(double speed){
    return Math.abs(speed) < 0.05 ? 0 : speed;
  }
}
