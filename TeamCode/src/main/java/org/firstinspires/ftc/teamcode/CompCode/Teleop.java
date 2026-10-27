package org.firstinspires.ftc.teamcode.CompCode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.button.Button;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.gamepad.TriggerReader;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.IntakeDefaultCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.SpitCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.DecreaseSpeedCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.FeedShooter;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.IncreaseSpeedCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.ShooterDefaultCommand;
import org.firstinspires.ftc.teamcode.Testing.RobotConstants;
import org.firstinspires.ftc.teamcode.Subsystems.ChassisSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp(name = "Blue Teleop", group = "Comp Code")
public class BlueTeleop extends CommandOpMode {
    ChassisSubsystem chassis;
    LimelightSubsystem limelight;
    ShooterSubsystem snap, crackle, pop;
    IntakeSubsystem intake;
    ShooterDefaultCommand snapDefault, crackleDefault, popDefault;
    IntakeDefaultCommand intakeDefault;
    Follower follower;
    GamepadEx driver;
    TelemetryManager telemetryM;
    Button shoot, speedUp, speedDown, spitButton;
    Trigger intakeTrigger;

    @Override
    public void initialize() {
        limelight = new LimelightSubsystem(hardwareMap);
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        driver = new GamepadEx(gamepad1);

        chassis = new ChassisSubsystem(hardwareMap);

        follower = Constants.createFollower(hardwareMap);
        snap = new ShooterSubsystem(RobotConstants.Hardware.COLOUR_SENSORS, hardwareMap);
        snapDefault = new ShooterDefaultCommand(snap);
        snap.setDefaultCommand(snapDefault);

        crackle = new ShooterSubsystem(RobotConstants.Hardware.COLOUR_SENSORS, hardwareMap);
        crackleDefault = new ShooterDefaultCommand(crackle);
        crackle.setDefaultCommand(crackleDefault);

        pop = new ShooterSubsystem(RobotConstants.Hardware.COLOUR_SENSORS, hardwareMap);
        popDefault = new ShooterDefaultCommand(pop);
        pop.setDefaultCommand(popDefault);

        intake = new IntakeSubsystem(hardwareMap);
        intakeDefault = new IntakeDefaultCommand(intake);
        intake.setDefaultCommand(intakeDefault);

        shoot = new GamepadButton(driver, GamepadKeys.Button.A);
        speedUp = new GamepadButton(driver, GamepadKeys.Button.DPAD_UP);
        speedDown = new GamepadButton(driver, GamepadKeys.Button.DPAD_DOWN);
        spitButton = new GamepadButton(driver, GamepadKeys.Button.X);

        intakeTrigger = new Trigger(() -> new TriggerReader(driver, GamepadKeys.Trigger.LEFT_TRIGGER).isDown());

        speedUp.whenPressed(
                new ParallelCommandGroup(
                        new IncreaseSpeedCommand(snap),
                        new IncreaseSpeedCommand(crackle),
                        new IncreaseSpeedCommand(pop)
                )
        );

        speedDown.whenPressed(
                new ParallelCommandGroup(
                        new DecreaseSpeedCommand(snap),
                        new DecreaseSpeedCommand(crackle),
                        new DecreaseSpeedCommand(pop)
                )
        );

        shoot.whileHeld(
                new ParallelCommandGroup(
                        new FeedShooter(snap),
                        new FeedShooter(crackle),
                        new FeedShooter(pop)
                )
        );

        intakeTrigger.whileActiveContinuous(
                new IntakeCommand(intake)
        );

        spitButton.whileHeld(
                new SpitCommand(intake)
        );
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        if (limelight.getLimelightResult() != null && limelight.getLimelightResult().isValid()) {
            chassis.getOtos().setPosition(new SparkFunOTOS.Pose2D(getRobotPoseFromCamera().getX(), getRobotPoseFromCamera().getY(), chassis.getOtos().getPosition().h));
        }
        chassis.getOtos().setAngularUnit(AngleUnit.DEGREES);
        telemetryM.addData("SNAP target speed", snap.getTargetSpeed());
        telemetryM.addData("SNAP actual speed", snap.getSpeed());
        telemetryM.update(telemetry);
    }

    private Pose getRobotPoseFromCamera() {
        Pose3D poseFromCamera = limelight.getLimelightResult().getBotpose();
        return new Pose(RobotConstants.metersToInches(poseFromCamera.getPosition().y) + 72, RobotConstants.metersToInches(-poseFromCamera.getPosition().x) + 72, limelight.getLimelightResult().getBotpose().getOrientation().getYaw(AngleUnit.DEGREES));
    }
}
