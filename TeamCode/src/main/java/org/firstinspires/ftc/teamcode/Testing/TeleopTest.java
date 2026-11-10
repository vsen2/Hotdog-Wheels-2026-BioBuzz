package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.RobotConstants;

@TeleOp(name = "X-Drive Chassis", group = "TeleOp")
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

        telemetry.addData("Status", "Initialized and Ready!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // X-Drive / Holonomic Drive:
            // Left Stick Y = Forward/Backward, Left Stick X = Strafe (Side-to-Side), Right Stick X = Turn
            double forward = -gamepad1.left_stick_y;
            double strafe  = gamepad1.left_stick_x;
            double turn    = gamepad1.right_stick_x;

            double flPower = forward + strafe + turn;
            double frPower = forward - strafe - turn;
            double blPower = forward - strafe + turn;
            double brPower = forward + strafe - turn;

            // Normalize motor powers if any exceed 1.0
            double maxPower = Math.max(Math.abs(flPower), Math.max(Math.abs(frPower),
                              Math.max(Math.abs(blPower), Math.abs(brPower))));
            if (maxPower > 1.0) {
                flPower /= maxPower;
                frPower /= maxPower;
                blPower /= maxPower;
                brPower /= maxPower;
            }

            double speedMultiplier = (gamepad1.left_bumper || gamepad1.x) ? 0.5 : 1.0;

            frontLeft.setPower(flPower * speedMultiplier);
            frontRight.setPower(frPower * speedMultiplier);
            backLeft.setPower(blPower * speedMultiplier);
            backRight.setPower(brPower * speedMultiplier);

            // Telemetry Feedback
            telemetry.addData("Status", "Running");
            telemetry.addData("Forward", "%.2f", forward);
            telemetry.addData("Strafe", "%.2f", strafe);
            telemetry.addData("Turn", "%.2f", turn);
            telemetry.addData("Slow Mode", (gamepad1.left_bumper || gamepad1.x) ? "Enabled (50%)" : "Disabled");
            telemetry.update();
        }
    }
}
