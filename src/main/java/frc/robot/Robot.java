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
import frc.robot.commands.cargointakecommands.DeployCargoIntakeCommand;
import frc.robot.subsystems.BackClimbSubsystem;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.CargoIntakeSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.FrontClimbSubsystem;
import frc.robot.subsystems.LiftSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static OI m_oi;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  private DriveTrainSubsystem driveTrain;
 // private LiftSubsystem liftSubsystem;
  //private CargoIntakeSubsystem cargoIntakeSubsystem;
  private CameraSubsystem cameraSubsystem;

  //private FrontClimbSubsystem frontClimbSubsystem;
  //private BackClimbSubsystem backClimbSubsystem;

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
    //liftSubsystem = new LiftSubsystem(m_oi);
    //cargoIntakeSubsystem = new CargoIntakeSubsystem(m_oi);
    driveTrain = new DriveTrainSubsystem(m_oi);
    cameraSubsystem = new CameraSubsystem(); // TODO: change CameraSubsystem constructor
    
    //frontClimbSubsystem = new FrontClimbSubsystem();
    //backClimbSubsystem = new BackClimbSubsystem();

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
    cameraSubsystem.initLimelight();
    //liftSubsystem.initTalon(); TODO: uncomment this for production code
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public DriveTrainSubsystem getDriveTrain() { return driveTrain; }
  //public LiftSubsystem getLiftSubsystem() { return liftSubsystem; }
  //public CargoIntakeSubsystem getCargoIntakeSubsystem() { return cargoIntakeSubsystem; }
  public CameraSubsystem getCameraSubsystem() { return cameraSubsystem; }
  //public FrontClimbSubsystem getFrontClimbSubsystem() { return frontClimbSubsystem; }
  //public BackClimbSubsystem getBackClimbSubsystem() { return backClimbSubsystem; }

  
}
