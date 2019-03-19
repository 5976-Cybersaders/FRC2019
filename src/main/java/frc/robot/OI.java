/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.XBoxButton.RawButton;

import frc.robot.commands.cameracommands.SwitchCameraCommand;
import frc.robot.commands.cargointakecommands.DeployCargoIntakeCommand;
import frc.robot.commands.cargointakecommands.RetractCargoIntakeCommand;
import frc.robot.commands.climbcommands.ActuateBackPistonCommand;
import frc.robot.commands.climbcommands.ActuateFrontPistonCommand;
import frc.robot.commands.drivetraincommands.HybridVisionDriveCommand;
import frc.robot.commands.drivetraincommands.VisionDriveCommand;
import frc.robot.commands.drivetraincommands.VisionDriveCompleteAutonomous;
import frc.robot.commands.drivetraincommands.VisionDriveConSelectedSide;
import frc.robot.commands.drivetraincommands.VisionDriveWIthAutoCorrectAndDriveStraight;
import frc.robot.commands.liftcommands.ExtractHookAfterHatchDeliveryCommandGroup;
import frc.robot.commands.liftcommands.RaiseLiftToFixedPositionCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  private final XboxController DRIVER_CONTROLLER = new XboxController(0); 
  private final XboxController SECONDARY_CONTROLLER = new XboxController(1);

  private XBoxButton switchCamera1;

  private XBoxButton liftToLowHatch;
  private XBoxButton liftToMiddleRocketHatch;
  private XBoxButton liftToRocketLowCargo;
  private XBoxButton liftToMiddleCargo;
  private XBoxTrigger liftToShuttleCargo;
  private XBoxTrigger liftToCargoPickup;
  
  private XBoxButton actuateFrontPiston;
  private XBoxButton actuateBackPiston;
  private XBoxButton visionDrive;
  private XBoxButton visionDriveLeft;
  private XBoxButton deployCargoIntakeControl;
  private XBoxButton extractHookAfterHatch;

  public OI(Robot robot){
    System.out.println("We made it to the OI bois :)");
    
    // Note -- the sticks on the DRIVER_CONTROLLER are used by the TeleOpDriveCommand
    // Note -- the triggers on the DRIVER_CONTROLLER are used by the CargoIntakeCommand
    this.switchCamera1 = new XBoxButton(DRIVER_CONTROLLER, RawButton.X);
    this.visionDrive = new XBoxButton(DRIVER_CONTROLLER, RawButton.B);
    this.visionDriveLeft = new XBoxButton(DRIVER_CONTROLLER, RawButton.Y);
    this.actuateFrontPiston = new XBoxButton(DRIVER_CONTROLLER, RawButton.RB);
    this.actuateBackPiston = new XBoxButton(DRIVER_CONTROLLER, RawButton.LB);

    // Note -- the left stick on the SECONDARY_CONTROLLER are used by the ManualMoveLiftCommand
    liftToLowHatch = new XBoxButton(SECONDARY_CONTROLLER, RawButton.A);
    liftToMiddleRocketHatch = new XBoxButton(SECONDARY_CONTROLLER, RawButton.B);
    //liftToHighHatch = new XBoxButton(SECONDARY_CONTROLLER, RawButton.A);
    liftToRocketLowCargo = new XBoxButton(SECONDARY_CONTROLLER, RawButton.X);
    liftToMiddleCargo = new XBoxButton(SECONDARY_CONTROLLER, RawButton.Y);

    liftToCargoPickup = new XBoxTrigger(SECONDARY_CONTROLLER, Hand.kLeft);
    liftToShuttleCargo = new XBoxTrigger(SECONDARY_CONTROLLER, Hand.kRight);

    deployCargoIntakeControl = new XBoxButton(SECONDARY_CONTROLLER, RawButton.RB);
    extractHookAfterHatch = new XBoxButton(SECONDARY_CONTROLLER, RawButton.LB); // TODO: create CommandGroup for after scoring a hatch

    System.out.println("Adios amigos :(");
  }

  public OI(){
  }

  public void bindButtons(Robot robot){
    System.out.println("*** Binding buttons ***");
    
    this.switchCamera1.whenPressed(new SwitchCameraCommand(robot.getCameraSubsystem()));
    this.visionDrive.whileHeld(new VisionDriveCompleteAutonomous(robot.getDriveTrain(), robot.getCameraSubsystem(), DRIVER_CONTROLLER, false));
    this.visionDriveLeft.whileHeld(new VisionDriveCompleteAutonomous(robot.getDriveTrain(), robot.getCameraSubsystem(), DRIVER_CONTROLLER, true));
    
    //this.actuateFrontPiston.whileHeld(new ActuateFrontPistonCommand(robot.getClimbSubsystem()));
    //this.actuateBackPiston.whileHeld(new ActuateBackPistonCommand(robot.getClimbSubsystem()));
    
    //liftToLowHatch.whileHeld(RaiseLiftToFixedPositionCommand.RaiseLiftToLowHatch(robot.getLiftSubsystem()));
    //liftToMiddleRocketHatch.whileHeld(RaiseLiftToFixedPositionCommand.RaiseLiftToMidRocketHatch(robot.getLiftSubsystem()));
    //liftToMiddleCargo.whileHeld(RaiseLiftToFixedPositionCommand.RaiseLiftToMidRocketCargo(robot.getLiftSubsystem()));

    //liftToRocketLowCargo.whileHeld(RaiseLiftToFixedPositionCommand.RaiseLiftToShuttleCargo(robot.getLiftSubsystem()));
    
    //TODO: figure out binding a command to a trigger
    //liftToCargoPickup.whileHeld(RaiseLiftToFixedPositionCommand.RaiseLiftToCargoPickup(robot.getLiftSubsystem()));
    //liftToShuttleCargo.whileHeld(RaiseLiftToFixedPositionCommand.RaiseLiftToShuttleCargo(robot.getLiftSubsystem()));
    
    deployCargoIntakeControl.whenPressed(new DeployCargoIntakeCommand(robot.getCargoIntakeSubsystem()));
    
    //TODO: ****complete this command group***
    extractHookAfterHatch.whenPressed(new ExtractHookAfterHatchDeliveryCommandGroup(
      robot.getDriveTrain(), robot.getLiftSubsystem()));

    System.out.println("*** Buttons bound ***");
  }

  public XboxController getDriverController() { return this.DRIVER_CONTROLLER; }
  public XboxController getSecondaryController() { return this.SECONDARY_CONTROLLER; }
}
