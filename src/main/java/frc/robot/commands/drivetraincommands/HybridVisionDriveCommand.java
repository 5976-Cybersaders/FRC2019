/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetraincommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.SmartDashboardMap;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;

public class HybridVisionDriveCommand extends VisionDriveCommand {

  private int driverControlCount;
  private int driverControlCycles;
  private int visionControlCount;
  private int visionControlCycles;

  public HybridVisionDriveCommand(DriveTrainSubsystem driveTrainSubsystem, CameraSubsystem cameraSubsystem, XboxController controller) {
    super(driveTrainSubsystem, cameraSubsystem, controller);
    this.driverControlCount = visionControlCount = 0;
  }

  @Override
  protected void initialize() {
    super.initialize();
    this.driverControlCycles = SmartDashboardMap.VISION_DRIVER_CONTROL_COUNT.getIntValue();
    this.visionControlCycles = SmartDashboardMap.VISION_CONTROL_COUNT.getIntValue();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (this.driverControlCount < driverControlCycles){
      getDriveTrainSubsystem().drive(-getXboxController().getY(Hand.kLeft), -getXboxController().getY(Hand.kRight));
      driverControlCount++;
      if (driverControlCount >= driverControlCycles){
        visionControlCount = 0;
      }
    } else if (this.visionControlCount < visionControlCycles){
      super.execute();
      visionControlCount++;
      if (visionControlCount >= visionControlCycles){
        driverControlCount = 0;
      }
    }
  }
}
