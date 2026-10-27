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
import com.seattlesolvers.solverslib.command.ParallelDeadlineGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.button.Button;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.gamepad.TriggerReader;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.Commands.ChassisCommands.BlueChassisLookToAprilTagTeleOp;
import org.firstinspires.ftc.teamcode.Commands.ChassisCommands.BlueFieldDefaultCommand;
import org.firstinspires.ftc.teamcode.Commands.ChassisCommands.ChassisLookToAprilTagTeleOp;
import org.firstinspires.ftc.teamcode.Commands.ChassisCommands.RedFieldDefaultCommand;
import org.firstinspires.ftc.teamcode.Commands.ChassisCommands.ResetPosition;
import org.firstinspires.ftc.teamcode.Commands.ControllerFeedback;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.IntakeDefaultCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.LiftIntakeArms;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.SpitCommand;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.FeedShooter;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.RevThreeToVeloUsingDistance;
import org.firstinspires.ftc.teamcode.Commands.ShooterCommands.ShooterDefaultCommand;
import org.firstinspires.ftc.teamcode.RobotConstants;
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
    BlueFieldDefaultCommand chassisDefault;
    ShooterDefaultCommand snapDefault, crackleDefault, popDefault;
    IntakeDefaultCommand intakeDefault;
    Follower follower;
    GamepadEx driver;
    TelemetryManager telemetryM;
    Button shoot, rev, lockOn, spitButton, spinMotor, resetYaw;
    Trigger intakeTrigger, holdArms;
    Boolean hasSeen;
    /// set position from auto

    @Override
    public void initialize() {
        hasSeen = false;
        limelight = new LimelightSubsystem(hardwareMap);
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        driver = new GamepadEx(gamepad1);

        chassis = new ChassisSubsystem(hardwareMap);
        chassisDefault = new BlueFieldDefaultCommand(chassis, telemetryM, driver);
        chassis.setDefaultCommand(chassisDefault);

        follower = Constants.createFollower(hardwareMap);
        snap = new ShooterSubsystem(RobotConstants.Hardware.SNAP, hardwareMap);
        snapDefault = new ShooterDefaultCommand(snap, limelight);
        snap.setDefaultCommand(snapDefault);

        crackle = new ShooterSubsystem(RobotConstants.Hardware.CRACKLE, hardwareMap);
        crackleDefault = new ShooterDefaultCommand(crackle, limelight);
        crackle.setDefaultCommand(crackleDefault);

        pop = new ShooterSubsystem(RobotConstants.Hardware.POP, hardwareMap);
        popDefault = new ShooterDefaultCommand(pop, limelight);
        pop.setDefaultCommand(popDefault);



        intake = new IntakeSubsystem(hardwareMap);
        intakeDefault = new IntakeDefaultCommand(intake);
        intake.setDefaultCommand(intakeDefault);
        spinMotor = new GamepadButton(driver, GamepadKeys.Button.X);
        lockOn = new GamepadButton(driver, GamepadKeys.Button.LEFT_BUMPER);
        rev = new GamepadButton(driver, GamepadKeys.Button.RIGHT_BUMPER);
        shoot = new GamepadButton(driver, GamepadKeys.Button.A);
        spitButton = new GamepadButton(driver, GamepadKeys.Button.X);
        resetYaw = new GamepadButton(driver, GamepadKeys.Button.START);
        chassis.initRed();


        intakeTrigger = new Trigger(()->new TriggerReader(driver, GamepadKeys.Trigger.LEFT_TRIGGER).isDown());
        holdArms = new Trigger(()->new TriggerReader(driver, GamepadKeys.Trigger.RIGHT_TRIGGER).isDown());


        rev.whileHeld(
                new ParallelCommandGroup(
                        new ControllerFeedback(driver, snap, crackle, pop),
                        new RevThreeToVeloUsingDistance(snap, crackle, pop, limelight, chassis, 'b')
                )


        );


        shoot.whenPressed(

                new ParallelDeadlineGroup(
                        new WaitCommand(RobotConstants.Teleop.SHOOTER_TIMER),
                        new FeedShooter(snap),
                        new FeedShooter(crackle),
                        new FeedShooter(pop)
                )


        );

        resetYaw.whenPressed(
                new ResetPosition(chassis)
        );

        holdArms.whileActiveContinuous(

                new ParallelCommandGroup(
                        new LiftIntakeArms(snap),
                        new LiftIntakeArms(pop)
                )


        );



        lockOn.whileHeld(
                new BlueChassisLookToAprilTagTeleOp(chassis, limelight, telemetryM, driver)
        );
        intakeTrigger.whileActiveContinuous(
                new IntakeCommand(intake)
        );
        spitButton.whileHeld(
                new SpitCommand(intake)
        );


    }

    @Override
    public void run(){
        CommandScheduler.getInstance().run();
        if(limelight.getLimelightResult().isValid()){
            chassis.getOtos().setPosition(new SparkFunOTOS.Pose2D(getRobotPoseFromCamera().getX(), getRobotPoseFromCamera().getY(),chassis.getOtos().getPosition().h));
        }
        chassis.getOtos().setAngularUnit(AngleUnit.DEGREES);
        telemetryM.addData("Not Modded Camera pos", limelight.getLimelightResult().getBotpose());
        telemetryM.addData("Modded Camera Position", getRobotPoseFromCamera());
        telemetryM.addData("OTOS position", chassis.getOtos().getPosition());
        telemetryM.addData("SNAP shooter speed", snap.getSpeed());
        telemetryM.update(telemetry);

    }
    private Pose getRobotPoseFromCamera() {
        //Fill this out to get the robot Pose from the camera's output (apply any filters if you need to using follower.getPose() for fusion)
        //Pedro Pathing has built-in KalmanFilter and LowPassFilter classes you can use for this
        Pose3D poseFromCamera = limelight.getLimelightResult().getBotpose();


        //Use this to convert standard FTC coordinates to standard Pedro Pathing coordinates
        return new Pose(RobotConstants.metersToInches(poseFromCamera.getPosition().y)+72, RobotConstants.metersToInches(-poseFromCamera.getPosition().x)+72, limelight.getLimelightResult().getBotpose().getOrientation().getYaw(AngleUnit.DEGREES));
    }
}
