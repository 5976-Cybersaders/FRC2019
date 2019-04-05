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

public class VisionDriveCommand extends Command {

  private DriveTrainSubsystem driveTrainSubsystem;
  private Limelight limelight;
  private XboxController controller;
  private int deadBandCounter;
  private double deadband;
  private double kp;
  private double min_cmd;
  private int txCounter;

  public VisionDriveCommand(DriveTrainSubsystem driveTrainSubsystem, CameraSubsystem cameraSubsystem, XboxController controller) {
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
    this.initCameraSettings(CamMode.kvision, LedMode.kforceOn, StreamType.kPiPChangeable); //TODO: determine stream type once second camera is plugged in
    this.deadBandCounter = 0;
    this.deadband = SmartDashboardMap.VISION_DEADBAND.getValue();
    this.kp = SmartDashboardMap.VISION_KP.getValue();
    this.min_cmd = SmartDashboardMap.VISION_MIN_CMD.getValue();
    System.out.println("Deadband: "  + deadband);
    System.out.println("KP: "  + kp);
    System.out.println("Minimum command: "  + min_cmd);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double tx = limelight.getdegRotationToTarget();
    txCounter++;
    if (txCounter >= 25) { 
      SmartDashboardMap.VISION_TX.putNumber(tx);
      txCounter = 0;
    }
    double headingError = -tx;
    double steerAdjust = 0;
    if (tx > deadband){
      steerAdjust = kp * headingError - min_cmd;
      deadBandCounter = 0;
    } else if (tx < -deadband){
      steerAdjust = kp * headingError + min_cmd;
      deadBandCounter = 0;
    } else {
      deadBandCounter++;
    }
    // double speed = Math.pow((tx/27), 3);
    double leftStick = -controller.getY(Hand.kLeft);
    double rightStick = -controller.getY(Hand.kRight);
    double leftSpeed = (leftStick + steerAdjust); //tx < 0 ? speed : 0;
    double rightSpeed = (rightStick - steerAdjust); // tx > 0 ? speed: 0;
    System.out.println("Vision Drive\nLeft: " + leftSpeed + " | Right: " + rightSpeed + " | tx: " + tx + " | Steer: " + steerAdjust + " | Left joystick: " + leftStick + " | Right joystick: " + rightStick);
    this.driveTrainSubsystem.visionDrive(leftSpeed, rightSpeed);
  }

  @Override
  protected boolean isFinished() {
    System.out.println("Vision deadband drive cmd counter: " + getDeadBandCounter());
    return getDeadBandCounter() >= 25;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.initCameraSettings(CamMode.kdriver, LedMode.kforceOff, StreamType.kPiPChangeable);
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
