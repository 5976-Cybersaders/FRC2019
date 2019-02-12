/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetraincommands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.subsystems.DriveTrainSubsystem;

public class TeleOpTankDrive extends Command {

    private DriveTrainSubsystem driveTrain;
    private WPI_TalonSRX leftMaster;
    private WPI_TalonSRX rightMaster;
    private XboxController controller;
    private int count, interval;

    public TeleOpTankDrive(XboxController controller, DriveTrainSubsystem driveTrain) {
        this.controller = controller;
        this.driveTrain = driveTrain;
        leftMaster = driveTrain.getLeftMaster();
        rightMaster = driveTrain.getRightMaster();
        requires(driveTrain);
        count = interval = 50;

        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        driveTrain.invertMotors();
        initTalon(leftMaster);
        initTalon(rightMaster);
    }

    @Override
    protected void execute() {
        // Negative below is intentional to reverse direction of joystick input.
        this.driveTrain.drive(-controller.getY(Hand.kLeft), -controller.getY(Hand.kRight));
        reportExecute();
    }

    private void reportExecute() {
        if (count++ >= interval) {
            //ReportHelper.reportTeleOp(leftMaster, "Left Master"); //TODO: create ReportHelper?
            //ReportHelper.reportTeleOp(rightMaster, "Right Master");
            System.out.println();
            count = 0;
        }
    }

    private void initTalon(WPI_TalonSRX talon) {
        talon.selectProfileSlot(1, 0);
        talon.configPeakOutputForward(1, 0);
        talon.configPeakOutputReverse(-1, 0);
        talon.configNominalOutputForward(0, 0);
        talon.configNominalOutputReverse(0, 0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        end();
    }
}
