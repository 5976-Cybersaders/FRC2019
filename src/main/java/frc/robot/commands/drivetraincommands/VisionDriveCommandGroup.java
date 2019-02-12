/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetraincommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.cameracommands.InitCameraDriverCommand;
import frc.robot.commands.cameracommands.InitCameraVisionCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;

public class VisionDriveCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public VisionDriveCommandGroup(DriveTrainSubsystem driveTrain, CameraSubsystem cameraSubsystem, XboxController controller) {
    addSequential(new InitCameraVisionCommand(cameraSubsystem));
    addSequential(new VisionDriveCommand(driveTrain, cameraSubsystem, controller));
    addSequential(new InitCameraDriverCommand(cameraSubsystem));
  }
}
