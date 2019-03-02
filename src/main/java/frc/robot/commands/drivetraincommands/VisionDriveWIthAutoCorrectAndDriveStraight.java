/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetraincommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.SmartDashboardMap;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.limelight.Limelight;
import frc.robot.subsystems.limelight.ControlMode.CamMode;
import frc.robot.subsystems.limelight.ControlMode.LedMode;
import frc.robot.subsystems.limelight.ControlMode.StreamType;

public class VisionDriveWIthAutoCorrectAndDriveStraight extends Command {

  private DriveTrainSubsystem driveTrainSubsystem;
  private Limelight limelight;
  private XboxController controller;
  private int deadBandCounter;
  private double deadband;
  private double kp;
  private double min_cmd;
  private int txCounter;

  private double txBeforeCorrection;
  private boolean wasUserControl;

  public VisionDriveWIthAutoCorrectAndDriveStraight(DriveTrainSubsystem driveTrainSubsystem, CameraSubsystem cameraSubsystem, XboxController controller) {
    this.driveTrainSubsystem = driveTrainSubsystem;
    this.limelight = cameraSubsystem.getLimelight();
    this.controller = controller;
    txCounter = 0;
    requires(driveTrainSubsystem);
    requires(cameraSubsystem);
    setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.initCameraSettings(CamMode.kvision, LedMode.kforceOn, StreamType.kPiPMain); //TODO: determine stream type once second camera is plugged in
    this.deadBandCounter = 0;
    this.deadband = SmartDashboardMap.VISION_DEADBAND.getValue();
    this.kp = SmartDashboardMap.VISION_KP.getValue();
    this.min_cmd = SmartDashboardMap.VISION_MIN_CMD.getValue();
    wasUserControl = true;
    txBeforeCorrection = 90;
    SmartDashboardMap.VISION_TX_BEFORE_CORRECTION.putNumber(txBeforeCorrection);
    System.out.println("Deadband: "  + deadband);
    System.out.println("KP: "  + kp);
    System.out.println("Minimum command: "  + min_cmd);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double leftStick = -controller.getY(Hand.kLeft);
    double rightStick = -controller.getY(Hand.kRight);
    double tx = limelight.getdegRotationToTarget();
    double leftSpeed = 0;
    double rightSpeed = 0;
    double steerAdjust = 0;
    boolean isUserControl = Math.abs(rightStick) > SmartDashboardMap.VISION_STICK_DEADBAND.getDouble() || Math.abs(leftStick) > SmartDashboardMap.VISION_STICK_DEADBAND.getDouble();
    if (wasUserControl && !isUserControl){
      txBeforeCorrection = tx;
      SmartDashboardMap.VISION_TX_BEFORE_CORRECTION.putNumber(txBeforeCorrection);
    }
    txCounter++;
    if (txCounter >= 25) { 
      SmartDashboardMap.VISION_TX.putNumber(tx);
      txCounter = 0;
    }
    if (isUserControl){
      wasUserControl = true;
      if (txBeforeCorrection > 89){
        txBeforeCorrection = tx;
        SmartDashboardMap.VISION_TX_BEFORE_CORRECTION.putNumber(txBeforeCorrection);
      }
      if (Math.abs(leftStick) > SmartDashboardMap.VISION_STICK_DEADBAND.getDouble()){
        getDriveTrainSubsystem().drive(leftStick, leftStick); // drive straight w/ one stick
      } else {
        if (txBeforeCorrection > 0){
          leftSpeed = rightStick;
          rightSpeed = rightStick * 0.15;
        } else {
          leftSpeed = rightStick * 0.15;
          rightSpeed = rightStick;
        }
      }
    } else {
      wasUserControl = false;
      double headingError = -tx;
      if (tx > deadband){
        steerAdjust = kp * headingError - min_cmd;
        deadBandCounter = 0;
      } else if (tx < -deadband){
        steerAdjust = kp * headingError + min_cmd;
        deadBandCounter = 0;
      } else {
        deadBandCounter++;
      }
      leftSpeed = (leftStick + steerAdjust); //tx < 0 ? speed : 0;
      rightSpeed = (rightStick - steerAdjust); // tx > 0 ? speed: 0;
    }
    System.out.println("Vision Drive\nLeft: " + leftSpeed + " | Right: " + rightSpeed + " | tx: " + tx + " | Steer: " + steerAdjust + " | Left joystick: " + leftStick + " | Right joystick: " + rightStick + " | isUserControl: " + isUserControl + " | txBeforeCorrection: " + txBeforeCorrection);
    this.driveTrainSubsystem.visionDrive(leftSpeed, rightSpeed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.initCameraSettings(CamMode.kdriver, LedMode.kforceOff, StreamType.kPiPMain);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }

  private void initCameraSettings(CamMode camMode, LedMode ledMode, StreamType streamType){
    this.limelight.setCamMode(camMode);
    this.limelight.setLEDMode(ledMode);
    this.limelight.setStream(streamType);
  }

  protected int getDeadBandCounter() { return this.deadBandCounter; }
  protected DriveTrainSubsystem getDriveTrainSubsystem() { return this.driveTrainSubsystem; }
  protected XboxController getXboxController() { return this.controller; }
}
