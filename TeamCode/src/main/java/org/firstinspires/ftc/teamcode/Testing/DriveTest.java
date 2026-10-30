package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.SpitCommand;
import org.firstinspires.ftc.teamcode.Subsystems.ChassisSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

@TeleOp(name = "Drive Test", group = "TeleOp")
public class DriveTest extends CommandOpMode {

    private ChassisSubsystem chassisSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private GamepadEx driver;

    @Override
    public void initialize() {
        // Initialize Subsystems & Gamepad
        chassisSubsystem = new ChassisSubsystem(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        driver = new GamepadEx(gamepad1);

        // Set Default Chassis Command (Field-Oriented Drive)
        chassisSubsystem.setDefaultCommand(new RunCommand(() -> {
            double forward = -driver.getLeftY();
            double strafe  = driver.getLeftX();
            double turn    = driver.getRightX();
            chassisSubsystem.driveFieldOriented(forward, strafe, turn);
        }, chassisSubsystem));

        // Bind Reset Yaw to DPAD DOWN
        driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
            .whenPressed(new InstantCommand(chassisSubsystem::resetYaw, chassisSubsystem));

        // Bind Intake to Button A (Run while held)
        driver.getGamepadButton(GamepadKeys.Button.A)
            .whileHeld(new IntakeCommand(intakeSubsystem));

        // Bind Spit to Button X (Run while held)
        driver.getGamepadButton(GamepadKeys.Button.X)
            .whileHeld(new SpitCommand(intakeSubsystem));

        telemetry.addData("Status", "running");
        telemetry.update();
    }
}
