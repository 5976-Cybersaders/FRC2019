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
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.limelight.Limelight;


public class VisionDriveCommand extends Command {

  private DriveTrainSubsystem driveTrainSubsystem;
  private Limelight limelight;
  private XboxController controller;
  private int counter;

  public VisionDriveCommand(DriveTrainSubsystem driveTrainSubsystem, CameraSubsystem cameraSubsystem, XboxController controller) {
    this.limelight = cameraSubsystem.getLimelight();
    this.controller = controller;
    requires(driveTrainSubsystem);
    requires(cameraSubsystem);
    setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.counter = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double kp = -0.1;
    double min_cmd = 0.05;
    double headingError = -limelight.getdegRotationToTarget();
    double tx = limelight.getdegRotationToTarget();
    double steerAdjust = 0;

    if (tx > 1.0){
      steerAdjust = kp * headingError - min_cmd;
      counter = 0;
    } else if (tx < -1.0){
      steerAdjust = kp * headingError + min_cmd;
      counter = 0;
    } else {
      counter++;
    }

    double leftSpeed = controller.getY(Hand.kLeft) + steerAdjust;
    double rightSpeed = controller.getY(Hand.kRight) - steerAdjust;
    System.out.println("Vision Drive\nLeft Speed: " + leftSpeed + " | Right Speed: " + rightSpeed);

    this.driveTrainSubsystem.drive(leftSpeed, rightSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return counter >= 25;
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
}
