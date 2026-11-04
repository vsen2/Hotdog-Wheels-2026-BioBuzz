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

@TeleOp(name = "Drive Test 2", group = "TeleOp")
public class onlyDrivingTest extends CommandOpMode {

    private ChassisSubsystem chassisSubsystem;
    private GamepadEx driver;

    @Override
    public void initialize() {
        // Initialize Subsystems & Gamepad
        chassisSubsystem = new ChassisSubsystem(hardwareMap);
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

        telemetry.addData("Status", "running");
        telemetry.update();
    }
}
