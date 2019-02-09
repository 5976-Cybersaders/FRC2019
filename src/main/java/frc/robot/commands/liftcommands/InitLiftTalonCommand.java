/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.liftcommands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.LiftSubsystem;
import frc.robot.SmartDashboardMap;

public class InitLiftTalonCommand extends InstantCommand {

  private WPI_TalonSRX talon;
  public static boolean isInitialized;

  public InitLiftTalonCommand(LiftSubsystem liftSubsystem) { //TODO: cleanup and correct
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.talon = liftSubsystem.getTalon();
    requires(liftSubsystem);
  }

  private void initTalon(WPI_TalonSRX talon) {
    if (isInitialized) return;
    isInitialized = true;

    if (talon == null) return;

    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
    //talon.setSelectedSensorPosition(0, 0, 0); //TODO: Get Lift start height in ticks
    talon.setSensorPhase(true);
    talon.setInverted(true);

    System.out.println("Setting lift p-value to " + SmartDashboardMap.LIFT_kP.getDouble());
    talon.config_kP(0, SmartDashboardMap.LIFT_kP.getDouble(), 0);
    // talon.config_kI(0, SmartDashboardMap.LIFT_kI.getDouble(), 0);
    // talon.config_kD(0, SmartDashboardMap.LIFT_kD.getDouble(), 0);
    // talon.configAllowableClosedloopError(0, SmartDashboardMap.LIFT_ALLOWABLE_ERROR.getIntValue(), 0);
    // talon.configPeakOutputForward(SmartDashboardMap.LIFT_PEAK_VOLTAGE.getDouble(), 0);
    // talon.configPeakOutputReverse(-SmartDashboardMap.LIFT_PEAK_VOLTAGE.getDouble(), 0);
    // talon.configNominalOutputForward(SmartDashboardMap.LIFT_NOMINAL_VOLTAGE.getDouble(), 0);
    // talon.configNominalOutputReverse(-SmartDashboardMap.LIFT_NOMINAL_VOLTAGE.getDouble(), 0);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isInitialized = false;
    this.initTalon(this.talon);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    isInitialized = false;
  }
}
