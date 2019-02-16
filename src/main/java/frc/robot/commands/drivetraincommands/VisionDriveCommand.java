/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetraincommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.GenericHID.Hand;
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
  private int counter;
  private double deadband;
  private double kp;
  private double min_cmd;

  public VisionDriveCommand(DriveTrainSubsystem driveTrainSubsystem, CameraSubsystem cameraSubsystem, XboxController controller) {
    this.driveTrainSubsystem = driveTrainSubsystem;
    this.limelight = cameraSubsystem.getLimelight();
    this.controller = controller;
    requires(driveTrainSubsystem);
    requires(cameraSubsystem);
    setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.initCameraSettings(CamMode.kvision, LedMode.kforceOn, StreamType.kPiPSecondary);
    this.counter = 0;
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
    double headingError = -tx;
    double steerAdjust = 0;

    if (tx > deadband){
      steerAdjust = kp * headingError - min_cmd;
      counter = 0;
    } else if (tx < -deadband){
      steerAdjust = kp * headingError + min_cmd;
      counter = 0;
    } else {
      counter++;
    }
    // double speed = Math.pow((tx/27), 3);
    double leftStick = -controller.getY(Hand.kLeft);
    double rightStick = -controller.getY(Hand.kRight);
    double leftSpeed = (leftStick + steerAdjust); //tx < 0 ? speed : 0;
    double rightSpeed = (rightStick - steerAdjust); // tx > 0 ? speed: 0;
    System.out.println("Vision Drive\nLeft: " + leftSpeed + " | Right: " + rightSpeed + " | tx: " + tx + " | Steer: " + steerAdjust + " | Left joystick: " + leftStick + " | Right joystick: " + rightStick);
    this.driveTrainSubsystem.visionDrive(leftSpeed, rightSpeed);
  }

  private void initCameraSettings(CamMode camMode, LedMode ledMode, StreamType streamType){
    this.limelight.setCamMode(camMode);
    this.limelight.setLEDMode(ledMode);
    this.limelight.setStream(streamType);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.out.println("Vision drive cmd counter: " + counter);
    return counter >= 25;
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
    System.out.println("Interrupted, starting end");
    end();
    System.out.println("Ended");
  }
}
