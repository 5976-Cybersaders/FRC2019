/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.liftcommands;

import frc.robot.subsystems.LiftSubsystem;

public class RaiseLiftCommand extends MoveLiftCommand {

  private RaiseLiftCommand(LiftSubsystem liftSubsystem, int posInches, int timeout) {
    super(liftSubsystem, posInches, timeout);
  }

  public static RaiseLiftCommand RaiseLiftToLowCargo(LiftSubsystem liftSubsystem){
    return new RaiseLiftCommand(liftSubsystem, 12, 10); // TODO: determine correct values, or use Shuffleboard
  }

  public static RaiseLiftCommand RaiseLiftToMiddleCargo(LiftSubsystem liftSubsystem){
    return new RaiseLiftCommand(liftSubsystem, 48, 10);
  }

  public static RaiseLiftCommand RaiseLiftToLowHatch(LiftSubsystem liftSubsystem){
    return new RaiseLiftCommand(liftSubsystem, 6, 10);
  }

}
