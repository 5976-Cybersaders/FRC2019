/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.liftcommands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.SmartDashboardMap;
import frc.robot.subsystems.LiftSubsystem;

public class ManualMoveLiftCommand extends Command {

  private static final int UPPER_LIMIT_TICKS = 20000;
  private static final int LOWER_LIMIT_TICKS = 1;

  private WPI_TalonSRX talon;
  private XboxController controller;
  private int counter = 0;

  public ManualMoveLiftCommand(LiftSubsystem liftSubsystem, XboxController controller) {
    this.talon = liftSubsystem.getTalon();
    this.controller = controller;
    requires(liftSubsystem);

    setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    int speedAdjustment = 4;
    double rawSpeed = this.adjustSpeed(this.controller.getY(Hand.kLeft));
    double adjustedSpeed = rawSpeed / speedAdjustment; 

    if (this.talon.getSelectedSensorPosition() < LOWER_LIMIT_TICKS) {
      if(adjustedSpeed > 0) {
        this.talon.set(adjustedSpeed);
      }
      else {
        this.talon.set(0);
      }
      System.out.println("Lift below lower limit");
    }
    else if (this.talon.getSelectedSensorPosition() > UPPER_LIMIT_TICKS){
      if(adjustedSpeed < 0) {
        this.talon.set(adjustedSpeed);
      }
      else {
        this.talon.set(0);
      }
      System.out.println("Lift above upper limit");
    }
    else {
      this.talon.set(adjustedSpeed);
    }

    //System.out.println("Manual Move Lift current position " + this.talon.getSelectedSensorPosition());
    counter = (counter % 25) + 1;
    if (counter == 25) { 
      SmartDashboardMap.LIFT_ENCODER_POSITION.putNumber(this.talon.getSelectedSensorPosition());
      SmartDashboardMap.LIFT_SPEED.putNumber(adjustedSpeed);
      System.out.println("Raw: " + rawSpeed + " Adj: " + adjustedSpeed);
    }
  }

  // TODO: decide if this is necessary
  // TODO: do we need an encoder on the lift? yes
  private double adjustSpeed(double speed){
    return Math.abs(speed) < 0.1 ? 0 : speed;
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
    InitLiftTalonCommand.isInitialized = false;
  }
}
