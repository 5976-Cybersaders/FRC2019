/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.GearBoxBurnCommand;

/**
 * Add your docs here.
 */
public class DriveTrainSubsystem extends Subsystem {
  
  private Talon left, right;

  private Servo servo; //for testing purposes todo: remove this

  public DriveTrainSubsystem(int left, int right, int servo){
    this.left = new Talon(left);
    this.right = new Talon(right);
    this.servo = new Servo(servo);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ExampleCommand(this));
  }
  public Talon getLeftTalon () {
    return left;
  }
  public Talon getRightTalon () {
    return right;
  }

  public Servo getServo(){
    return this.servo;
  }
}
