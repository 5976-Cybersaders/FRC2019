/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.BurnInDoNothingCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.cargointakecommands.DeployCargoIntakeCommand;
import frc.robot.commands.drivetraincommands.GearBoxBurnCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.CargoIntakeSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveTrainBurnInSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.HatchControlSubsystem;
import frc.robot.subsystems.LiftSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static OI m_oi;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  private DriveTrainBurnInSubsystem driveTrainBurnIn;
  private DriveTrainSubsystem driveTrain;
  private LiftSubsystem liftSubsystem;
  private CargoIntakeSubsystem cargoIntakeSubsystem;
  private HatchControlSubsystem hatchControlSubsystem;
  private CameraSubsystem cameraSubsystem;

  private ClimbSubsystem climbSubsystem;

  private Command burnInCommand;

  private int counter = 0;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    System.out.println("****** BEGIN ROBOT INIT ******");
    SmartDashboardMap.reportAll();
    m_oi = new OI(this);
    counter = 0;
    //this.liftSubsystem = new LiftSubsystem(m_oi);
    //this.hatchControlSubsystem = new HatchControlSubsystem();
    //this.cargoIntakeSubsystem = new CargoIntakeSubsystem(m_oi);
    this.driveTrain = new DriveTrainSubsystem(m_oi);
    this.cameraSubsystem = new CameraSubsystem(); // TODO: change CameraSubsystem constructor
    
    //this.climbSubsystem = new ClimbSubsystem();

    m_oi.bindButtons(this);

    System.out.println("****** END ROBOT INIT ******");
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();
    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
    this.cameraSubsystem.initLimelight();
    //liftSubsystem.initTalon(); TODO: uncomment this for production code
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    burnInCommand = null;
    cameraSubsystem.initLimelight();
    //liftSubsystem.initTalon(); TODO: uncomment this for production code
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    counter++;
    if (this.burnInCommand == null && counter > 10){
      // this.burnInCommand = new BurnCommandGroup();
      // this.burnInCommand.start();
    }
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public DriveTrainSubsystem getDriveTrain() { return this.driveTrain; }
  public LiftSubsystem getLiftSubsystem() { return this.liftSubsystem; }
  public CargoIntakeSubsystem getCargoIntakeSubsystem() { return this.cargoIntakeSubsystem; }
  public HatchControlSubsystem getHatchControlSubsystem() { return this.hatchControlSubsystem; }
  public CameraSubsystem getCameraSubsystem() { return this.cameraSubsystem; }
  public ClimbSubsystem getClimbSubsystem() { return this.climbSubsystem; }

  class BurnCommandGroup extends CommandGroup {
    int runTimeSeconds = 20;
    int waitTimeSeconds = 2*runTimeSeconds;
    int iterations = 2;
    BurnCommandGroup(){
      // this.addCommand(-0.1, runTimeSeconds, iterations);
      // this.addCommand(-0.2, runTimeSeconds, iterations);
      // this.addCommand(-0.3, runTimeSeconds, iterations);
      // this.addCommand(-0.4, runTimeSeconds, iterations);
      // this.addCommand(-0.5, runTimeSeconds, iterations);
      // this.addCommand(-0.6, runTimeSeconds, iterations);
      // this.addCommand(-0.7, runTimeSeconds, iterations);
      // this.addCommand(-0.8, runTimeSeconds, iterations);

      this.addCommand(0.3, runTimeSeconds, 8);
      this.addCommand(-0.3, runTimeSeconds, 8);
      this.addCommand(0.5, runTimeSeconds, iterations);
      this.addCommand(-0.5, runTimeSeconds, iterations);
      this.addCommand(0.7, runTimeSeconds, 1);
      this.addCommand(-0.7, runTimeSeconds, 1);

    }

    private void addCommand(double speed, int runSeconds, int iterations){
      for (int i = 0; i < iterations; i++){
        SingleStepBurnCommandGroup singleStep = new SingleStepBurnCommandGroup(speed, runSeconds);
        this.addSequential(singleStep);
      }
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
