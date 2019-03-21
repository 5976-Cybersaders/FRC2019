/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.liftcommands.ManualMoveLiftCommand;

import frc.robot.SmartDashboardMap;

/**
 * Add your docs here.
 */
public class LiftSubsystem extends Subsystem {

  private WPI_TalonSRX talon;
  private OI oi;
  private boolean isInitialized;

  public LiftSubsystem(OI oi){
    this.oi = oi;
    this.talon = new WPI_TalonSRX(RobotMap.LIFT_TALON_ID);
    this.isInitialized = false;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualMoveLiftCommand(this, oi.getSecondaryController()));
  }

  public void initTalon() {
    if (isInitialized || talon == null) return;
    isInitialized = true;

    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
    talon.setSelectedSensorPosition(0, 0, 0); //TODO: Get Lift start height in ticks
    talon.setSensorPhase(true);
    talon.setInverted(true);

    //System.out.println("Setting lift p-value to " + SmartDashboardMap.LIFT_kP.getDouble());
    //talon.config_kP(0, SmartDashboardMap.LIFT_kP.getDouble(), 0);
    // talon.config_kI(0, SmartDashboardMap.LIFT_kI.getDouble(), 0);
    // talon.config_kD(0, SmartDashboardMap.LIFT_kD.getDouble(), 0);
    // talon.configAllowableClosedloopError(0, SmartDashboardMap.LIFT_ALLOWABLE_ERROR.getIntValue(), 0);
    // talon.configPeakOutputForward(SmartDashboardMap.LIFT_PEAK_VOLTAGE.getDouble(), 0);
    // talon.configPeakOutputReverse(-SmartDashboardMap.LIFT_PEAK_VOLTAGE.getDouble(), 0);
    // talon.configNominalOutputForward(SmartDashboardMap.LIFT_NOMINAL_VOLTAGE.getDouble(), 0);
    // talon.configNominalOutputReverse(-SmartDashboardMap.LIFT_NOMINAL_VOLTAGE.getDouble(), 0);


    talon.config_kP(0, 0.3, 0);
    talon.config_kI(0, 0, 0);
    talon.config_kD(0, 0, 0);
    talon.configAllowableClosedloopError(0, 100, 0);
    talon.configPeakOutputForward(0.7, 0);
    talon.configPeakOutputReverse(-0.7, 0);
    talon.configNominalOutputForward(0.3, 0);
    talon.configNominalOutputReverse(-0.3, 0);

  }

  public WPI_TalonSRX getTalon(){ return talon; }
}
