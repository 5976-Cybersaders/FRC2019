/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.liftcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.LiftSubsystem;

public abstract class MoveLiftCommand extends Command {

  private WPI_TalonSRX talon;
  private int ticks;

  private int kPositionTolerance = 10; //TODO: figure out constants

  public MoveLiftCommand(LiftSubsystem liftSubsystem, int posInches, int timeout) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.ticks = (int) (posInches * toTicks(posInches));
    this.talon = liftSubsystem.getTalon();
    requires(liftSubsystem);
    setTimeout(timeout);
    setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
        talon.setSelectedSensorPosition(0, 0, 0); //TODO: Get Lift start height in ticks
        talon.setSensorPhase(true);
        talon.setInverted(true);

        talon.config_kP(0, 0.3, 0);
        talon.config_kI(0, 0, 0);
        talon.config_kD(0, 0, 0);
        talon.configAllowableClosedloopError(0, 100, 0);
        talon.configPeakOutputForward(0.7, 0);
        talon.configPeakOutputReverse(-0.7, 0);
        talon.configNominalOutputForward(0.3, 0);
        talon.configNominalOutputReverse(-0.3, 0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    this.talon.set(ControlMode.Position, ticks);
    //System.out.println("Lift Sensor Position " + this.talon.getSelectedSensorPosition() + "  Moving to: " + ticks);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut() || isAtPos();
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

  private boolean isAtPos(){
    return this.talon.getSelectedSensorPosition() - this.ticks < kPositionTolerance;
  }

  private double toTicks(double inches) { // TODO: make sure this is correct
    return inches / (Math.PI * 2.5) * 4096;
  }
}
