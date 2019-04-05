/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.climbcommands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.ClimbSubsystem;

/**
 * Add your docs here.
 */
public abstract class ActuatePistonCommand extends Command {
  /**
   * Add your docs here.
   */

  private DoubleSolenoid doubleSolenoid;
  private boolean pistonIsForward;

  public ActuatePistonCommand(ClimbSubsystem climbSubsystem, DoubleSolenoid doubleSolenoid) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.doubleSolenoid = doubleSolenoid;
    requires(climbSubsystem);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    pistonIsForward = false;
  }

  protected void execute(){
    if (!pistonIsForward && DriverStation.getInstance().getMatchTime() < 30) {
      doubleSolenoid.set(DoubleSolenoid.Value.kForward);
      pistonIsForward = true;
      System.out.println(getClass().getSimpleName() + " moving piston Forward");
    }
  }

  @Override
  protected void end() {
    doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    System.out.println(getClass().getSimpleName() + " ENDED!!!!!!");
  }

  @Override
  protected void interrupted() {
    System.out.println(getClass().getSimpleName() + " INTERPUTED!!!!!!");
    end();
  }

  protected boolean isFinished() {
    return false;
  }
  
}
