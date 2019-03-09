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

  private VisionCommandData commandData = new VisionCommandData();

  public VisionDriveWIthAutoCorrectAndDriveStraight(DriveTrainSubsystem driveTrainSubsystem, CameraSubsystem cameraSubsystem, XboxController controller) {
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
    this.initCameraSettings(CamMode.kvision, LedMode.kforceOn, StreamType.kPiPMain); //TODO: determine stream type once second camera is plugged in
    this.deadBandCounter = 0;
    this.deadband = SmartDashboardMap.VISION_DEADBAND.getValue();
    this.kp = SmartDashboardMap.VISION_KP.getValue();
    this.min_cmd = SmartDashboardMap.VISION_MIN_CMD.getValue();
    txCounter = 25; // so that it is reported on the first iteration
    commandData.setWasUserControl(true);
    commandData.setTxBeforeCorrection(90);
    commandData.setFirstTxBeforeCorrection(90);
    SmartDashboardMap.VISION_TX_BEFORE_CORRECTION.putNumber(commandData.getTxBeforeCorrection());
    System.out.println("Deadband: "  + deadband);
    System.out.println("KP: "  + kp);
    System.out.println("Minimum command: "  + min_cmd);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    commandData.refresh();
    if(commandData.getTv()) {
      reportData();
      if (commandData.shouldSnap()){
        commandData.snap();
      }
      if (commandData.isUserControl()){
        handleUserControl();
      } else {
        handleAutonomousControl();
      }
      commandData.report();
      this.driveTrainSubsystem.visionDrive(commandData.getLeftSpeed(), commandData.getRightSpeed());
    } else {
      //TODO: Do something mayyyyyyyyyyybbbbbbbbbbbeeeeeeeeee ;)
      SmartDashboardMap.VISION_TX.putNumber(90);
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

  private void handleUserControl(){
    commandData.setWasUserControl(true);
    //If command is entered with user control true on first iteration we should snap because in the normal case we only snap when not in user control
    if (commandData.getTxBeforeCorrection() > 89){
      commandData.snap();
    }
    if (Math.abs(commandData.getLeftStick()) > SmartDashboardMap.VISION_STICK_DEADBAND.getDouble()){
      getDriveTrainSubsystem().drive(commandData.getLeftStick(), commandData.getLeftStick()); // drive straight w/ one stick
    } else {
      if (Math.abs(commandData.getTx()) < 20){ // prevent steering out of range of LL
        if (commandData.getFirstTxBeforeCorrection() > 0){
          commandData.setLeftSpeed(commandData.getRightStick());
          commandData.setRightSpeed(commandData.getRightStick() * SmartDashboardMap.VISION_OVER_DRIVE_FACTOR.getDouble());
        } else {
          commandData.setLeftSpeed(commandData.getRightStick() * SmartDashboardMap.VISION_OVER_DRIVE_FACTOR.getDouble());
          commandData.setRightSpeed(commandData.getRightStick());
        }
      }
    }
  }

  private void handleAutonomousControl(){
    if (Math.abs(commandData.getTxBeforeCorrection()) < 0.00000001){
      return;
    }
    commandData.setWasUserControl(false);
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
    commandData.setLeftSpeed(commandData.getLeftStick() + commandData.getSteerAdjust()); //tx < 0 ? speed : 0;
    commandData.setRightSpeed(commandData.getRightStick() - commandData.getSteerAdjust()); // tx > 0 ? speed: 0;

  }

  protected int getDeadBandCounter() { return this.deadBandCounter; }
  protected DriveTrainSubsystem getDriveTrainSubsystem() { return this.driveTrainSubsystem; }
  protected XboxController getXboxController() { return this.controller; }



  private class VisionCommandData {
    double leftStick;
    double rightStick;
    double tx;
    boolean tv;
    double txBeforeCorrection = 0;
    double firstTxBeforeCorrection = 90;
    double leftSpeed;
    double rightSpeed;
    double steerAdjust;
    boolean isUserControl;
    boolean wasUserControl = true;

    public void refresh() {
      leftStick = -controller.getY(Hand.kLeft);
      rightStick = -controller.getY(Hand.kRight);
      tx = limelight.getdegRotationToTarget();
      tv = limelight.getIsTargetFound();
      leftSpeed = 0;
      rightSpeed = 0;
      steerAdjust = 0;
      isUserControl = Math.abs(rightStick) > SmartDashboardMap.VISION_STICK_DEADBAND.getDouble() || Math.abs(leftStick) > SmartDashboardMap.VISION_STICK_DEADBAND.getDouble();
    }

    public boolean shouldSnap(){
      return wasUserControl && !isUserControl;
    }

    public void snap(){
      txBeforeCorrection = tx;
      SmartDashboardMap.VISION_TX_BEFORE_CORRECTION.putNumber(txBeforeCorrection);
      if (Math.abs(firstTxBeforeCorrection) > 89){
        firstTxBeforeCorrection = txBeforeCorrection;
      }
    }

    public void report(){
      SmartDashboardMap.VISION_LEFT_SPEED.putNumber(leftSpeed);
      SmartDashboardMap.VISION_RIGHT_SPEED.putNumber(rightSpeed);
      System.out.println("Vision Drive\nLeft: " + leftSpeed + " | Right: " + rightSpeed + " | tx: " + tx + " | Steer: " + steerAdjust + " | Left joystick: " + leftStick + " | Right joystick: " + rightStick + " | isUserControl: " + isUserControl + " | txBeforeCorrection: " + txBeforeCorrection);
    }

    public double getLeftStick() {
      return leftStick;
    }

    public void setLeftStick(double leftStick) {
      this.leftStick = leftStick;
    }

    public double getRightStick() {
      return rightStick;
    }

    public void setRightStick(double rightStick) {
      this.rightStick = rightStick;
    }

    public double getTx() {
      return tx;
    }

    public void setTx(double tx) {
      this.tx = tx;
    }

    public double getTxBeforeCorrection(){
      return this.txBeforeCorrection;
    }

    public void setTxBeforeCorrection(double txBeforeCorrection){
      this.txBeforeCorrection = txBeforeCorrection;
    }

    public double getFirstTxBeforeCorrection(){
      return firstTxBeforeCorrection;
    }

    public void setFirstTxBeforeCorrection(double firstTxBeforeCorrection){
      this.firstTxBeforeCorrection = firstTxBeforeCorrection;
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

    public boolean isUserControl() {
      return isUserControl;
    }

    public void setIsUserContorl(boolean isUserControl) {
      this.isUserControl = isUserControl;
    }

    public void setWasUserControl(boolean wasUserControl){
      this.wasUserControl = wasUserControl;
    }

    public boolean getTv() {
      return this.tv;
    }
  }

}
