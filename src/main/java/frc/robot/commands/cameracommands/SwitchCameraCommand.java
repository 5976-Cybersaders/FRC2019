/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.cameracommands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.limelight.ControlMode.CamMode;
import frc.robot.subsystems.limelight.ControlMode.LedMode;
import frc.robot.subsystems.limelight.ControlMode.StreamType;

public class SwitchCameraCommand extends InstantCommand {

  private CameraSubsystem cameraSubsystem;

  public SwitchCameraCommand(CameraSubsystem cameras) {
    this.cameraSubsystem = cameras;
    requires(cameras);
  }

  public StreamType getNextStreamMode() {
    switch(this.cameraSubsystem.getLimelight().getStream()) {
      case kPiPMain:
        this.cameraSubsystem.getLimelight().setPipeline(8);
        this.cameraSubsystem.getLimelight().setCamMode(CamMode.kvision);
        this.cameraSubsystem.getLimelight().setLEDMode(LedMode.kforceOn);
        return StreamType.kPiPSecondary;
        
      case kPiPSecondary:
      this.cameraSubsystem.getLimelight().setPipeline(8);
      this.cameraSubsystem.getLimelight().setCamMode(CamMode.kvision);
        this.cameraSubsystem.getLimelight().setLEDMode(LedMode.kforceOn);
        return StreamType.kStandard;
      case kStandard:
      this.cameraSubsystem.getLimelight().setPipeline(8);
      this.cameraSubsystem.getLimelight().setCamMode(CamMode.kvision);
        this.cameraSubsystem.getLimelight().setLEDMode(LedMode.kforceOn);
        return StreamType.kPiPMain;
    }
    return StreamType.kPiPMain;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    StreamType type = this.getNextStreamMode();
    this.cameraSubsystem.getLimelight().setStream(type);
    System.out.println("\n\nSwitching LimeLight to mode " + type + "\n\n");
  }
}
