/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystems.limelight.Limelight;
import frc.robot.subsystems.limelight.ControlMode.CamMode;
import frc.robot.subsystems.limelight.ControlMode.LedMode;
import frc.robot.subsystems.limelight.ControlMode.StreamType;

/**
 * Add your docs here.
 */
public class CameraSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private Limelight limelight;

  public CameraSubsystem() {
    limelight = new Limelight();
    System.out.println("Camera subsystem created with limelight " + limelight);
  }

  public void initLimelight(){
    this.limelight.setCamMode(CamMode.kdriver);
    this.limelight.setLEDMode(LedMode.kforceOff);
    this.limelight.setStream(StreamType.kPiPMain);
  }

  @Override
  public void initDefaultCommand() {
  }
  
  public Limelight getLimelight() { return limelight; }
}
