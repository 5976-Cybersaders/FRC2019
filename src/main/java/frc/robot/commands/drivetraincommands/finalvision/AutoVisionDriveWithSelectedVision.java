/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*
* Could improve the factor on line 152
* Could decrease/increase txdeadband
*/



package frc.robot.commands.drivetraincommands.finalvision;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.SmartDashboardMap;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.limelight.Limelight;
import frc.robot.subsystems.limelight.ControlMode.CamMode;
import frc.robot.subsystems.limelight.ControlMode.LedMode;
import frc.robot.subsystems.limelight.ControlMode.StreamType;

public class AutoVisionDriveWithSelectedVision extends Command {

  private DriveTrainSubsystem driveTrainSubsystem;
  private Limelight limelight;
  private int deadBandCounter;
  private double deadband;
  private double kp;
  private double min_cmd;
  private int txCounter;
  private boolean shouldWeGoLeft;
  private boolean isSnapMode;
  private int pipeline;

  private double txBand = (SmartDashboardMap.VISION_DRIVE_MAX_TX.getDouble() + 1) / 2; // +/-

  private VisionCommandData commandData = new VisionCommandData();

  public AutoVisionDriveWithSelectedVision(DriveTrainSubsystem driveTrainSubsystem, CameraSubsystem cameraSubsystem, int pipeline) {
    this.driveTrainSubsystem = driveTrainSubsystem;
    this.shouldWeGoLeft = false;
    this.pipeline = pipeline;
    this.limelight = cameraSubsystem.getLimelight();
    requires(driveTrainSubsystem);
    requires(cameraSubsystem);
    setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.limelight.setPipeline(pipeline);
    this.initCameraSettings(CamMode.kvision, LedMode.kforceOn, StreamType.kPiPMain); //TODO: determine stream type once second camera is plugged in
    this.deadBandCounter = 0;
    this.deadband = SmartDashboardMap.VISION_DEADBAND.getValue();
    this.kp = SmartDashboardMap.VISION_KP.getValue();
    this.min_cmd = SmartDashboardMap.VISION_MIN_CMD.getValue();
    txCounter = 5; // so that it is reported on the first iteration
    this.isSnapMode = true;
    System.out.println("\n\n\n**************************************************");
    System.out.println("Deadband: "  + deadband);
    System.out.println("KP: "  + kp);
    System.out.println("Minimum command: "  + min_cmd);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    commandData.refresh();
    if(!commandData.getTv()) {
      System.out.println("Waiting for target!!! " + commandData.getTx());
      driveTrainSubsystem.visionDrive(0, 0);
      return;
    }
    determineMode();
    if(isSnapMode) {
      handleSnapMode();
    } else {
      handleDriveMode();
    }

    driveTrainSubsystem.visionDrive(commandData.getLeftSpeed(), commandData.getRightSpeed());

    if(txCounter >= 5) {
      commandData.report();
      txCounter = 0;
    } else {
      txCounter++;
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    this.initCameraSettings(CamMode.kdriver, LedMode.kforceOn, StreamType.kPiPMain);
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
    boolean oldIsSnapMode = isSnapMode;
    if (commandData.getTa() > SmartDashboardMap.VISION_DRIVE_MAX_TA.getDouble()) {
      isSnapMode = true;
    } else if(isSnapMode) {
      if(deadBandCounter >= SmartDashboardMap.DEADBAND_COUNTER.getDouble()) {
        deadBandCounter = 0;
        isSnapMode = false;
      }
    } else {
      isSnapMode = Math.abs(commandData.getTx()) >= SmartDashboardMap.VISION_DRIVE_MAX_TX.getDouble();
    }
    if (oldIsSnapMode != isSnapMode){
      String desc = "DRIVE";
      if (isSnapMode) desc = "SNAP";
      System.out.println("Entered " + desc + " mode.  Tx: " + commandData.getTx() + " | Deadband counter: " + deadBandCounter + " | Ta: " + commandData.getTa());
    }
  }

  /* private double getSpeed(){
    double maxSpeed = SmartDashboardMap.VISION_DRIVE_MODE_SPEED.getDouble();
    double factorXAdjust = Math.sqrt(((Math.pow(txBand, 2) * (min_cmd - 1.8))/-1.8)); // adjustment along x axis for the ramping formula to ramp up and ramp down sooner
    double factorForCorrect = (-1.8/Math.pow(txBand, 2)) * Math.pow(Math.abs(commandData.getTx()) - factorXAdjust, 2) + 1.8;
    //(-1.8/Math.pow(txBand, 2)) * Math.pow(commandData.getTx() - txBand, 2) + 1.8;
    double correctedRawSpeed = maxSpeed * factorForCorrect;
    double adjustedSpeed = Math.max(correctedRawSpeed, Math.abs(min_cmd));
    double finalSpeed = Math.min(adjustedSpeed, maxSpeed);
   // System.out.println("Factor: " + factorForCorrect + " CorrectedRawSpeed: " + correctedRawSpeed + " Final speed: " + finalSpeed);
    return finalSpeed;
  } */

  public double getSpeed() {
    double maxSpeed = SmartDashboardMap.VISION_DRIVE_MODE_SPEED.getDouble();
    double absTx = Math.abs(Math.abs(commandData.getTx()) - txBand);
    double factor = 1 - absTx / txBand;
    double scaledFactor = factor * SmartDashboardMap.VISION_DRIVE_FACTOR_FOR_SPEED.getDouble();
    double scaledMaxSpeed = scaledFactor * maxSpeed;

    double adjustedSpeed = Math.max(scaledMaxSpeed, Math.abs(min_cmd));
    double finalSpeed = Math.min(adjustedSpeed, maxSpeed);
   // System.out.println("Factor: " + factorForCorrect + " CorrectedRawSpeed: " + correctedRawSpeed + " Final speed: " + finalSpeed);
    return finalSpeed;

  }

  private void handleDriveMode(){
    //If command is entered with user control true on first iteration we should snap because in the normal case we only snap when not in user control
    double speed = getSpeed();
    double lesserSpeed = (speed * SmartDashboardMap.VISION_OVER_DRIVE_FACTOR.getDouble());
      if (Math.abs(commandData.getTx()) < 2*txBand){ // prevent steering out of range of LL
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
    double maxSpeed = SmartDashboardMap.VISION_SNAP_MAX_SPEED.getDouble();
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
    if(commandData.getSteerAdjust() < -maxSpeed) {
      commandData.setSteerAdjust(-maxSpeed);
    } else if(commandData.getSteerAdjust() > maxSpeed) {
      commandData.setSteerAdjust(maxSpeed);
    }

    commandData.setLeftSpeed(commandData.getSteerAdjust()); //tx < 0 ? speed : 0;
    commandData.setRightSpeed(-commandData.getSteerAdjust()); // tx > 0 ? speed: 0;

  }

  protected int getDeadBandCounter() { return this.deadBandCounter; }
  protected DriveTrainSubsystem getDriveTrainSubsystem() { return this.driveTrainSubsystem; }



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
