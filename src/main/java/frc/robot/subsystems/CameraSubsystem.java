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

/**
 * Add your docs here.
 */
public class CameraSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private UsbCamera camera1, camera2;
  private CameraServer cameraServer;
  private ShuffleboardTab shuffleTab;
  private Limelight limelight;

  public CameraSubsystem(int device1, int device2) {
    shuffleTab = Shuffleboard.getTab("Video");
    cameraServer = CameraServer.getInstance();
    limelight = new Limelight();

    
    
  }

  @Override
  public void initDefaultCommand() {
  }
  
  public Limelight getLimelight() { return limelight; }
  public VideoSink getVideoSink() { return cameraServer.getServer();  }
  public UsbCamera getCamera1() { return this.camera1; }
  public UsbCamera getCamera2() { return this.camera2; }
}
