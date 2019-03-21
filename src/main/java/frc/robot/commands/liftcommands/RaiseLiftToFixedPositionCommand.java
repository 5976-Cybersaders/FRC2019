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
  private RaiseLiftToFixedPositionCommand(LiftSubsystem liftSubsystem, int ticks) {
    super(liftSubsystem, ticks, false);
  }

  private RaiseLiftToFixedPositionCommand(LiftSubsystem liftSubsystem, int ticks, boolean exitWhenAtPos){
    super(liftSubsystem, ticks, exitWhenAtPos);
  }

  public static RaiseLiftToFixedPositionCommand RaiseLiftToLowHatch(LiftSubsystem liftSubsystem){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem,
      SmartDashboardMap.LIFT_TO_LOW_POS_TICKS.getIntValue());
  }

  public static RaiseLiftToFixedPositionCommand RaiseLiftToMidRocketHatch(LiftSubsystem liftSubsystem){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem,
      SmartDashboardMap.LIFT_TO_MID_HATCH_ROCKET_POS_TICKS.getIntValue());
  }

  // public static RaiseLiftToFixedPositionCommand RaiseLiftToHighRocketHatch(LiftSubsystem liftSubsystem){
  //   return new RaiseLiftToFixedPositionCommand(liftSubsystem, SmartDashboardMap.LIFT_TO_HIGH_HATCH_ROCKET_POS_TICKS.getIntValue(), 10);
  // }

  public static RaiseLiftToFixedPositionCommand RaiseLiftToMidRocketCargo(LiftSubsystem liftSubsystem){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem,
      SmartDashboardMap.LIFT_TO_MID_CARGO_ROCKET_POS_TICKS.getIntValue());
  }

  public static RaiseLiftToFixedPositionCommand RaiseLiftToShuttleCargo(LiftSubsystem liftSubsystem){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem,
      SmartDashboardMap.LIFT_TO_SHUTTLE_CARGO.getIntValue());
  }

  public static RaiseLiftToFixedPositionCommand RaiseLiftToCargoPickup(LiftSubsystem liftSubsystem){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem, 
      SmartDashboardMap.LIFT_TO_CARGO_PICKUP_POS_TICKS.getIntValue());
  }

  public static RaiseLiftToFixedPositionCommand RaiseLiftToRocketLowCargo(LiftSubsystem liftSubsystem){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem,
      SmartDashboardMap.LIFT_TO_ROCKET_LOW_CARGO_POS_TICKS.getIntValue());
  }

  public static RaiseLiftToFixedPositionCommand LowerLiftByInches(LiftSubsystem liftSubsystem, int ticksLower){
    return new RaiseLiftToFixedPositionCommand(liftSubsystem,
      liftSubsystem.getTalon().getSelectedSensorPosition() - ticksLower, true);
  }

}
