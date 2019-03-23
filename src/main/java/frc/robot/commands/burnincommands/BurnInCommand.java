/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.burnincommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.burnincommands.BurnInDoNothingCommand;
import frc.robot.commands.burnincommands.GearBoxBurnCommand;

class BurnCommandGroup extends CommandGroup {

  DriveTrainBurnInSubsystem driveTrainBurnIn;
  int runTimeSeconds = 20;
  int waitTimeSeconds = 2*runTimeSeconds;
  int iterations = 2;
  BurnCommandGroup(DriveTrainBurnInSubsystem driveTrainBurnIn){
    // this.addCommand(-0.1, runTimeSeconds, iterations);
    // this.addCommand(-0.2, runTimeSeconds, iterations);
    // this.addCommand(-0.3, runTimeSeconds, iterations);
    // this.addCommand(-0.4, runTimeSeconds, iterations);
    // this.addCommand(-0.5, runTimeSeconds, iterations);
    // this.addCommand(-0.6, runTimeSeconds, iterations);
    // this.addCommand(-0.7, runTimeSeconds, iterations);
    // this.addCommand(-0.8, runTimeSeconds, iterations);
    this.driveTrainBurnIn = driveTrainBurnIn;
    addCommand(0.3, runTimeSeconds, 8);
    addCommand(-0.3, runTimeSeconds, 8);
    addCommand(0.5, runTimeSeconds, iterations);
    addCommand(-0.5, runTimeSeconds, iterations);
    addCommand(0.7, runTimeSeconds, 1);
    addCommand(-0.7, runTimeSeconds, 1);

  }

  private void addCommand(double speed, int runSeconds, int iterations){
    for (int i = 0; i < iterations; i++){
      SingleStepBurnCommandGroup singleStep = new SingleStepBurnCommandGroup(speed, runSeconds);
      this.addSequential(singleStep);
    }
  }

  class SingleStepBurnCommandGroup extends CommandGroup {
    SingleStepBurnCommandGroup(double speed, int runSeconds){
      int waitSeconds = runSeconds * 2;
      this.addSequential(new GearBoxBurnCommand(driveTrainBurnIn, speed, runSeconds));
      this.addSequential(new BurnInDoNothingCommand(driveTrainBurnIn, waitSeconds));
    }
  }
}