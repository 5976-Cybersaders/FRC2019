/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;

public class GearBoxBurnCommand extends Command {

  private DriveTrainSubsystem driveTrain;
  private double speed;
  private long millisTimeout;
  private long endTime;
  
  public GearBoxBurnCommand(DriveTrainSubsystem driveTrain, double speed, int seconds) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.driveTrain = driveTrain;
    requires(driveTrain);
    this.speed = speed;
    this.millisTimeout = seconds * 1000;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    endTime = System.currentTimeMillis() + millisTimeout;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    driveTrain.getLeftTalon().set(speed);
    driveTrain.getRightTalon().set(speed);
    //this.driveTrain.getServo().set(speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    long timeNow = System.currentTimeMillis();
    
    boolean val = System.currentTimeMillis() >= endTime;
    System.out.println("timeNow: " + timeNow + "endTime: " + endTime + "val: " + val);
    if (val){
      driveTrain.getLeftTalon().set(0);
      driveTrain.getRightTalon().set(0);
    }
    return val;
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
