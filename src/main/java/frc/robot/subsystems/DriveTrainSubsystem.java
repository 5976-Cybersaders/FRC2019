/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.drivetraincommands.TeleOpTankDrive;

import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class DriveTrainSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private static double expoFactor = 0.2;
  private WPI_TalonSRX leftMaster, leftSlave, rightMaster, rightSlave;
  private SpeedControllerGroup leftSide, rightSide;
  private List<WPI_TalonSRX> rightTalons;
  private List<WPI_TalonSRX> leftTalons;

  private OI oi;

  public DriveTrainSubsystem(OI oi){
    this.oi = oi;
    this.leftMaster = new WPI_TalonSRX(RobotMap.LEFT_MASTER_TALON_ID);
    this.leftSlave = new WPI_TalonSRX(RobotMap.LEFT_SLAVE_TALON_ID);
    this.rightMaster = new WPI_TalonSRX(RobotMap.RIGHT_MASTER_TALON_ID);
    this.rightSlave = new WPI_TalonSRX(RobotMap.RIGHT_SLAVE_TALON_ID);

    leftSide = new SpeedControllerGroup(leftMaster, leftSlave);
    rightSide = new SpeedControllerGroup(rightMaster, rightSlave);

    leftTalons = Arrays.asList(this.leftMaster, this.leftSlave);
    rightTalons = Arrays.asList(this.rightMaster, this.rightSlave);
  }

  @Override
  public void initDefaultCommand() { // TODO: update if choosing to do autonomous
    // Set the default command for a subsystem here.
    setDefaultCommand(new TeleOpTankDrive(this.oi.getDriverController(), this));
  }

  public void invertMotors(){
    List<WPI_TalonSRX> talonsToInvert = rightTalons, talonsToNotInvert = leftTalons;
    talonsToInvert.forEach(talon -> {
        talon.setSensorPhase(true);
        talon.setInverted(true);
     });
     talonsToNotInvert.forEach(talon -> {
         talon.setSensorPhase(true);
         talon.setInverted(false);
     });
  }

  public void drive(double leftSpeed, double rightSpeed) {
    leftSide.set(adjustSpeed(leftSpeed));
    rightSide.set(adjustSpeed(rightSpeed));
  }

  public void visionDrive(double leftSpeed, double rightSpeed) {
    leftSide.set(leftSpeed);
    rightSide.set(rightSpeed);
  }

  private double adjustSpeed(double d) {
    if (Math.abs(d) < 0.03) return 0;
    return Math.signum(d) * Math.pow(Math.abs(d), Math.pow(4, expoFactor));
  }

  public WPI_TalonSRX getLeftMaster() { return this.leftMaster; }
  public WPI_TalonSRX getLeftSlave() { return this.leftSlave; }
  public WPI_TalonSRX getRightMaster() { return this.rightMaster; }
  public WPI_TalonSRX getRightSlave() { return this.rightSlave; }
  
  public SpeedControllerGroup getLeftSide() { return this.leftSide; }
  public SpeedControllerGroup getRightSide() { return this.rightSide; }
}
