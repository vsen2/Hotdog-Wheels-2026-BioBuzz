package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

@TeleOp(name = "X-Drive Chassis & Shooter TeleOp", group = "TeleOp")
public class TeleopTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        // Initialize Drivetrain Motors from HardwareMap
        DcMotor frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backLeft   = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor backRight  = hardwareMap.get(DcMotor.class, "backRight");

        // Reverse right-side drive motors
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set drive motors to brake on zero power
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize Shooter Subsystem
        ShooterSubsystem shooter = new ShooterSubsystem(
            new String[]{
                RobotConstants.Hardware.SHOOTER_MOTOR_1,
                RobotConstants.Hardware.HOOD_SERVO,
                RobotConstants.Hardware.RAMP_SERVO,
                "F"
            },
            hardwareMap
        );

        boolean lastDpadUp = false;
        boolean lastDpadDown = false;

        telemetry.addData("Status", "Initialized and Ready!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Xbox Controller Inputs for Chassis
            double forward = -gamepad1.left_stick_y;
            double strafe  = gamepad1.left_stick_x;
            double turn    = gamepad1.right_stick_x;

            double flPower = forward + strafe + turn;
            double frPower = forward - strafe - turn;
            double blPower = forward - strafe + turn;
            double brPower = forward + strafe - turn;

            double maxPower = Math.max(Math.abs(flPower), Math.max(Math.abs(frPower),
                              Math.max(Math.abs(blPower), Math.abs(brPower))));
            if (maxPower > 1.0) {
                flPower /= maxPower;
                frPower /= maxPower;
                blPower /= maxPower;
                brPower /= maxPower;
            }

            double speedMultiplier = gamepad1.left_bumper ? 0.5 : 1.0;

            frontLeft.setPower(flPower * speedMultiplier);
            frontRight.setPower(frPower * speedMultiplier);
            backLeft.setPower(blPower * speedMultiplier);
            backRight.setPower(brPower * speedMultiplier);

            // Shooter Speed Control (DPAD UP = Faster, DPAD DOWN = Slower)
            if (gamepad1.dpad_up && !lastDpadUp) {
                shooter.increaseSpeed(0.05);
            }
            lastDpadUp = gamepad1.dpad_up;

            if (gamepad1.dpad_down && !lastDpadDown) {
                shooter.decreaseSpeed(0.05);
            }
            lastDpadDown = gamepad1.dpad_down;

            // Trigger Shoot (Button A)
            if (gamepad1.a) {
                shooter.feedShoot();
            } else {
                shooter.resetFeed();
            }

            // Telemetry Feedback
            telemetry.addData("Status", "Running");
            telemetry.addData("Shooter Target Speed", "%.2f", shooter.getTargetSpeed());
            telemetry.addData("Shooter Motor Power", "%.2f", shooter.getSpeed());
            telemetry.update();
        }
    }
}
