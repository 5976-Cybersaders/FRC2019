/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.drivetraincommands.TeleOpTankDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class DriveTrainSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private WPI_TalonSRX leftMaster, leftSlave, rightMaster, rightSlave;

  private OI oi;

  public DriveTrainSubsystem(OI oi){
    super();
    this.oi = oi;
    this.leftMaster = new WPI_TalonSRX(RobotMap.LEFT_MASTER_TALON_ID);
    this.leftSlave = new WPI_TalonSRX(RobotMap.LEFT_SLAVE_TALON_ID);
    this.rightMaster = new WPI_TalonSRX(RobotMap.RIGHT_MASTER_TALON_ID);
    this.rightSlave = new WPI_TalonSRX(RobotMap.RIGHT_SLAVE_TALON_ID);
    
  }

  @Override
  public void initDefaultCommand() { // TODO: update if choosing to do autonomous
    // Set the default command for a subsystem here.
    setDefaultCommand(new TeleOpTankDrive(this.oi.getDriverController(), this));
  }

  public void invertMotors(){
    // TODO: find out which motors need to be inverted
  }

  public WPI_TalonSRX getLeftMaster() { return this.leftMaster; }
  public WPI_TalonSRX getLeftSlave() { return this.leftSlave; }
  public WPI_TalonSRX getRightMaster() { return this.rightMaster; }
  public WPI_TalonSRX getRightSlave() { return this.rightSlave; }
}
