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
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.cameracommands.SwitchCameraCommand;

/**
 * Add your docs here.
 */
public class CameraSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private VideoSink videoSink;
  private UsbCamera camera1, camera2;

  public CameraSubsystem(int device1, int device2) {
    CameraServer cameraServer = CameraServer.getInstance();
    this.camera1 = cameraServer.startAutomaticCapture(device1);
    //this.camera2 = cameraServer.startAutomaticCapture(device2);

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    table.getEntry("camMode").setNumber(1); // todo: remove magic numbers

    
    System.out.println("Pre getting video sink");
    this.videoSink = cameraServer.getServer();
    
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new SwitchCameraCommand(this, camera1));
  }

  public VideoSink getVideoSink() { return this.videoSink; }
  public UsbCamera getCamera1() { return this.camera1; }
  public UsbCamera getCamera2() { return this.camera2; }
}
