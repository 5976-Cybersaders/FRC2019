/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetraincommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainBurnInSubsystem;

public class GearBoxBurnCommand extends Command {

  private DriveTrainBurnInSubsystem driveTrain;
  private double speed;
  private double currentSpeed;
  private double speedIncrement;
  private long millisTimeout;
  private long endTime;

  boolean timedOut = false;
  
  public GearBoxBurnCommand(DriveTrainBurnInSubsystem driveTrain, double speed, int seconds) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.driveTrain = driveTrain;
    requires(driveTrain);
    this.speed = speed;
    this.millisTimeout = seconds * 1000;
    this.speedIncrement = speed / 100;

    //setTimeout(seconds);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    endTime = System.currentTimeMillis() + millisTimeout;
    this.currentSpeed = 0;
    System.out.println("Init Speed: " + this.speed + " Init timeout (s): " + this.millisTimeout/1000);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (!timedOut){
      this.currentSpeed = Math.abs(currentSpeed) >= Math.abs(speed) ? speed : this.currentSpeed + this.speedIncrement;
      setSpeed(currentSpeed);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    long timeNow = System.currentTimeMillis();
    
    timedOut = System.currentTimeMillis() >= endTime;
    
    if (timedOut){
      this.currentSpeed -= this.speedIncrement;
      setSpeed(currentSpeed);
    }
    boolean returnVal = timedOut && Math.abs(this.currentSpeed) <= 0.1;
    //System.out.println("timeNow: " + timeNow + "endTime: " + endTime + "val: " + timedOut + " returnVal: " + returnVal + " currentSpeed: "  +currentSpeed);
    if (returnVal) {
      setSpeed(0);
      System.out.println("GearBoxBurnCommand finished");
    }
    return returnVal;
  }

  protected void setSpeed(double speed){

    driveTrain.getLeftTalon().set(speed);
    driveTrain.getRightTalon().set(speed);
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
