/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.cameracommands;

import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.limelight.ControlMode.CamMode;
import frc.robot.subsystems.limelight.ControlMode.LedMode;
import frc.robot.subsystems.limelight.ControlMode.StreamType;

/**
 * Add your docs here.
 */
public class InitCameraVisionCommand extends InitCameraCommand {
  /**
   * Add your docs here.
   */
  public InitCameraVisionCommand(CameraSubsystem cameraSubsystem) {
    super(cameraSubsystem, CamMode.kvision, LedMode.kforceOn, StreamType.kPiPChangeable);
  }

}
