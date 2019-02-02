/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.drivetraincommands.GearBoxBurnCommand;
import frc.robot.commands.drivetraincommands.TeleOpTankDrive;

/**
 * Add your docs here.
 */
public class DriveTrainSubsystem extends Subsystem {
  
  private Talon left, right;

  public DriveTrainSubsystem(int left, int right){
    this.left = new Talon(left);
    this.right = new Talon(right);
  }

  @Override
  public void initDefaultCommand() {
    // TODO: determine if need to set default command to TeleOpTankDrive
  }

  public Talon getLeftTalon () {
    return left;
  }
  
  public Talon getRightTalon () {
    return right;
  }
}
