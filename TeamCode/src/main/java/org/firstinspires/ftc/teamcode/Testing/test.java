package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "X-Drive Chassis TeleOp", group = "TeleOp")
public class test extends LinearOpMode {

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
            // Xbox Controller Inputs
            // Note: left_stick_y is inverted on FTC gamepads (up is negative)
            double forward = -gamepad1.left_stick_y;
            double strafe  = gamepad1.left_stick_x;
            double turn    = gamepad1.right_stick_x;

            // X-Drive Kinematics Power Equations
            double flPower = forward + strafe + turn;
            double frPower = forward - strafe - turn;
            double blPower = forward - strafe + turn;
            double brPower = forward + strafe - turn;

            // Normalize powers so no motor exceeds 1.0 (100% power)
            double maxPower = Math.max(Math.abs(flPower), Math.max(Math.abs(frPower),
                              Math.max(Math.abs(blPower), Math.abs(brPower))));
            if (maxPower > 1.0) {
                flPower /= maxPower;
                frPower /= maxPower;
                blPower /= maxPower;
                brPower /= maxPower;
            }

            // Hold Left Bumper for 50% speed precision mode
            double speedMultiplier = gamepad1.left_bumper ? 0.5 : 1.0;

            // Send power to motors
            frontLeft.setPower(flPower * speedMultiplier);
            frontRight.setPower(frPower * speedMultiplier);
            backLeft.setPower(blPower * speedMultiplier);
            backRight.setPower(brPower * speedMultiplier);

            // Telemetry Feedback
            telemetry.addData("Status", "Running");
            telemetry.addData("Speed Mode", speedMultiplier == 0.5 ? "SLOW (50%)" : "FULL (100%)");
            telemetry.addData("FL Power", "%.2f", flPower * speedMultiplier);
            telemetry.addData("FR Power", "%.2f", frPower * speedMultiplier);
            telemetry.addData("BL Power", "%.2f", blPower * speedMultiplier);
            telemetry.addData("BR Power", "%.2f", brPower * speedMultiplier);
            telemetry.update();
        }
    }
}
