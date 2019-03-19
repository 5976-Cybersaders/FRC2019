/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetraincommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.SmartDashboardMap;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.limelight.Limelight;
import frc.robot.subsystems.limelight.ControlMode.CamMode;
import frc.robot.subsystems.limelight.ControlMode.LedMode;
import frc.robot.subsystems.limelight.ControlMode.StreamType;

public class VisionDriveCompleteAutonomous extends Command {

  private DriveTrainSubsystem driveTrainSubsystem;
  private Limelight limelight;
  private XboxController controller;
  private int deadBandCounter;
  private double deadband;
  private double kp;
  private double min_cmd;
  private int txCounter;
  private boolean shouldWeGoLeft;
  private boolean isSnapMode;

  private VisionCommandData commandData = new VisionCommandData();

  public VisionDriveCompleteAutonomous(DriveTrainSubsystem driveTrainSubsystem, CameraSubsystem cameraSubsystem, XboxController controller, boolean shouldWeGoLeft) {
    this.driveTrainSubsystem = driveTrainSubsystem;
    this.shouldWeGoLeft = shouldWeGoLeft;
    this.limelight = cameraSubsystem.getLimelight();
    this.controller = controller;
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
    txCounter = 25; // so that it is reported on the first iteration
    this.isSnapMode = true;
    System.out.println("Deadband: "  + deadband);
    System.out.println("KP: "  + kp);
    System.out.println("Minimum command: "  + min_cmd);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    /*
      IF first entry -> go into snap mode (calling snap(), then calling handleAutonomousControl()
      ELSE not first entry
        IF in snap mode
          IF deadBandCounter > threshold -> transition to handleUserControl()
          ELSE continue handleAutonomousControl()
        ELSE
          IF tx < threshold -> continue handleUserControl()
          ELSE go snap(), then handleAutonomousControl()
    */

    /*
      starting position <-- *initialize*
      while (ta < 11) { <-- *execute start*
        correct angle //  correcting mode
        while (Math.abs(tx) < 22) {
          drive towards center line // driving mode
        }
      } <-- *execute end*
    */
      if(txCounter == 25) {
        commandData.report();
        txCounter = 0;
      } else {
        txCounter++;
      }

    commandData.refresh();
    determineMode();
    if(isSnapMode) {
      handleSnapMode();
    } else {
      handleDriveMode();
    }
    if(commandData.getTa() < 12) {
      driveTrainSubsystem.visionDrive(commandData.getLeftSpeed(), commandData.getRightSpeed());
    } else {
      driveTrainSubsystem.visionDrive(0, 0);

    }
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

  private void reportData(){
    SmartDashboardMap.VISION_TX.putNumber(commandData.getTx());
  }

  public void determineMode() {
    if(isSnapMode) {
      if(deadBandCounter >= 25) {
        deadBandCounter = 0;
        isSnapMode = false;
      }
    } else {
      isSnapMode = Math.abs(commandData.getTx()) >= 20;
    }
  }

  private void handleDriveMode(){
    //If command is entered with user control true on first iteration we should snap because in the normal case we only snap when not in user control
    double speed = 0.257;
    double lesserSpeed = speed * SmartDashboardMap.VISION_OVER_DRIVE_FACTOR.getDouble();
      if (Math.abs(commandData.getTx()) < 20){ // prevent steering out of range of LL
        if (!shouldWeGoLeft){
          commandData.setLeftSpeed(speed);
          commandData.setRightSpeed(lesserSpeed);
        } else {
          commandData.setLeftSpeed(lesserSpeed);
          commandData.setRightSpeed(speed);
        }
      }
    }

  private void handleSnapMode(){
    double headingError = -commandData.getTx();
    if (commandData.getTx() > deadband){
      commandData.setSteerAdjust(kp * headingError - min_cmd);
      deadBandCounter = 0;
    } else if (commandData.getTx() < -deadband){
      commandData.setSteerAdjust(kp * headingError + min_cmd);
      deadBandCounter = 0;
    } else {
      deadBandCounter++;
    }
    commandData.setLeftSpeed(commandData.getSteerAdjust()); //tx < 0 ? speed : 0;
    commandData.setRightSpeed(-commandData.getSteerAdjust()); // tx > 0 ? speed: 0;

  }

  protected int getDeadBandCounter() { return this.deadBandCounter; }
  protected DriveTrainSubsystem getDriveTrainSubsystem() { return this.driveTrainSubsystem; }
  protected XboxController getXboxController() { return this.controller; }



  private class VisionCommandData {
    double tx;
    boolean tv;
    double ta;
    double leftSpeed;
    double rightSpeed;
    double steerAdjust;

    public void refresh() {
      tx = limelight.getdegRotationToTarget();
      tv = limelight.getIsTargetFound();
      ta = limelight.getTargetArea();
      leftSpeed = 0;
      rightSpeed = 0;
      steerAdjust = 0;
    }



    public void report(){
      SmartDashboardMap.VISION_LEFT_SPEED.putNumber(leftSpeed);
      SmartDashboardMap.VISION_RIGHT_SPEED.putNumber(rightSpeed);
      System.out.println("Vision Drive\nLeft: " + leftSpeed + " | Right: " + rightSpeed + " | tx: " + tx + " | Steer: " + steerAdjust + " | Deadband counter: " + deadBandCounter + " | isSnapMode: " + isSnapMode + " | Ta: " + commandData.getTa());
    }

    public double getTx() {
      return tx;
    }

    public void setTx(double tx) {
      this.tx = tx;
    }
 
    public double getLeftSpeed() {
      return leftSpeed;
    }

    public void setLeftSpeed(double leftSpeed) {
      this.leftSpeed = leftSpeed;
    }

    public double getRightSpeed() {
      return rightSpeed;
    }

    public void setRightSpeed(double rightSpeed) {
      this.rightSpeed = rightSpeed;
    }

    public double getSteerAdjust() {
      return steerAdjust;
    }

    public void setSteerAdjust(double steerAdjust) {
      this.steerAdjust = steerAdjust;
    }

    public boolean getTv() {
      return this.tv;
    }

    public double getTa() {
      return this.ta;
    }
  }

}
