package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.SpitCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.ShooterDefaultCommand;
import org.firstinspires.ftc.teamcode.Subsystems.ChassisSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

@TeleOp(name = "Drive, Intake & Shooter Test", group = "TeleOp")
public class DriveIntakeShooter extends CommandOpMode {

    private ChassisSubsystem chassisSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private GamepadEx driver;

    @Override
    public void initialize() {
        // Initialize Subsystems & Gamepad
        chassisSubsystem = new ChassisSubsystem(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap, telemetry);
        driver = new GamepadEx(gamepad1);

        // Set Default Chassis Command (Field-Oriented Drive)
        chassisSubsystem.setDefaultCommand(new RunCommand(() -> {
            double forward = -driver.getLeftY();
            double strafe  = driver.getLeftX();
            double turn    = driver.getRightX();
            chassisSubsystem.driveFieldOriented(forward, strafe, turn);
        }, chassisSubsystem));

        // Set Default Shooter Command (Continuously maintains target flywheel speed)
        shooterSubsystem.setDefaultCommand(new ShooterDefaultCommand(shooterSubsystem));

        // Bind Reset Yaw to DPAD DOWN
        driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new InstantCommand(chassisSubsystem::resetYaw, chassisSubsystem));

        // Bind Intake to Button A (Run while held)
        driver.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(new IntakeCommand(intakeSubsystem));

        // Bind Spit to Button X (Run while held)
        driver.getGamepadButton(GamepadKeys.Button.X)
                .whileHeld(new SpitCommand(intakeSubsystem));

        // Bind Feeder/Shoot to Button B (Feeds ball while held, stows when released)
        driver.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(new InstantCommand(shooterSubsystem::feedShoot, shooterSubsystem))
                .whenReleased(new InstantCommand(shooterSubsystem::resetFeed, shooterSubsystem));

        telemetry.addData("Status", "Running DriveIntakeShooter OpMode");
        telemetry.update();
    }
}
