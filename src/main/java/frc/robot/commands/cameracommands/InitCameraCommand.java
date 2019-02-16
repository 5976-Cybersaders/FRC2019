/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.cameracommands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.limelight.Limelight;
import frc.robot.subsystems.limelight.ControlMode.CamMode;
import frc.robot.subsystems.limelight.ControlMode.LedMode;
import frc.robot.subsystems.limelight.ControlMode.StreamType;

/**
 * Add your docs here.
 */
public class InitCameraCommand extends InstantCommand {
  /**
   * Add your docs here. TODO: remove these if we aren't going to use them
   */
  private Limelight limelight;
  private CamMode camMode;
  private LedMode ledMode;
  private StreamType streamType;

  public InitCameraCommand(CameraSubsystem cameraSubsystem, CamMode camMode, LedMode ledMode, StreamType streamType) {
    this.limelight = cameraSubsystem.getLimelight();
    this.camMode = camMode;
    this.ledMode = ledMode;
    this.streamType = streamType;
    requires(cameraSubsystem);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    this.limelight.setCamMode(camMode);
    this.limelight.setLEDMode(ledMode);
    this.limelight.setStream(streamType);
    
  }

  protected Limelight getLimelight(){ return this.limelight; }

}
