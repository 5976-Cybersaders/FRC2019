/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.liftcommands;

import frc.robot.SmartDashboardMap;
import frc.robot.subsystems.LiftSubsystem;

public class RaiseLiftToFixedPositionCommand extends MoveLiftCommand {

  //TODO: figure out timeout values for each command
  private RaiseLiftToFixedPositionCommand(LiftSubsystem liftSubsystem, int posInches) {
    super(liftSubsystem, posInches, false);
  }

  private RaiseLiftToFixedPositionCommand(LiftSubsystem liftSubsystem, int posInches, boolean exitWhenAtPos){
    super(liftSubsystem, posInches, exitWhenAtPos);
  }

  public static RaiseLiftToFixedPositionCommand RaiseLiftToLowHatch(LiftSubsystem liftSubsystem){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem,
      SmartDashboardMap.LIFT_TO_LOW_POS_INCHES.getIntValue());
  }

  public static RaiseLiftToFixedPositionCommand RaiseLiftToMidRocketHatch(LiftSubsystem liftSubsystem){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem,
      SmartDashboardMap.LIFT_TO_MID_HATCH_ROCKET_POS_INCHES.getIntValue());
  }

  // public static RaiseLiftToFixedPositionCommand RaiseLiftToHighRocketHatch(LiftSubsystem liftSubsystem){
  //   return new RaiseLiftToFixedPositionCommand(liftSubsystem, SmartDashboardMap.LIFT_TO_HIGH_HATCH_ROCKET_POS_INCHES.getIntValue(), 10);
  // }

  public static RaiseLiftToFixedPositionCommand RaiseLiftToMidRocketCargo(LiftSubsystem liftSubsystem){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem,
      SmartDashboardMap.LIFT_TO_MID_CARGO_ROCKET_POS_INCHES.getIntValue());
  }

  public static RaiseLiftToFixedPositionCommand RaiseLiftToShuttleCargo(LiftSubsystem liftSubsystem){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem,
      SmartDashboardMap.LIFT_TO_SHUTTLE_CARGO.getIntValue());
  }

  public static RaiseLiftToFixedPositionCommand RaiseLiftToCargoPickup(LiftSubsystem liftSubsystem){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem, 
      SmartDashboardMap.LIFT_TO_CARGO_PICKUP_POS_INCHES.getIntValue());
  }

  public static RaiseLiftToFixedPositionCommand RaiseLiftToRocketLowCargo(LiftSubsystem liftSubsystem){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem,
      SmartDashboardMap.LIFT_TO_ROCKET_LOW_CARGO_POS_INCHES.getIntValue());
  }

  public static RaiseLiftToFixedPositionCommand LowerLiftByInches(LiftSubsystem liftSubsystem, double inchesLower){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem,
      (int) (liftSubsystem.ticksToInches(liftSubsystem.getTalon().getSelectedSensorPosition()) - inchesLower), true);
  }

}
