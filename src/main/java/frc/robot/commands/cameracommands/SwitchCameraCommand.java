/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.cameracommands;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.CameraSubsystem;

public class SwitchCameraCommand extends Command {

  private CameraSubsystem cameraSubsystem;
  private UsbCamera nextCamera;
  private Boolean activated;

  public SwitchCameraCommand(CameraSubsystem cameras, UsbCamera nextCamera) { //TODO: cleanup
    this.cameraSubsystem = cameras;
    this.nextCamera = nextCamera;
    requires(cameras);
    setInterruptible(true);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    this.activated = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (!this.activated) {
      this.cameraSubsystem.getVideoSink().setSource(nextCamera);
      this.activated = true;
    } 
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
