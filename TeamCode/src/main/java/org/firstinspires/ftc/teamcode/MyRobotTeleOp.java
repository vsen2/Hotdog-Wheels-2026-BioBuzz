package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "BIOBUZZ TeleOp (Modular)", group = "Competition")
public class MyRobotTeleOp extends LinearOpMode {

    // Instantiate hardware class
    RobotConstants robot = new RobotConstants();

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize all hardware with one line
        robot.init(hardwareMap);

        telemetry.addData("Status", "Hardware Initialized Successfully");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;

            if (gamepad1.back) {
                robot.imu.resetYaw();
            }

            double botHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            double speedMultiplier = gamepad1.left_bumper ? 0.4 : 1.0;
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);

            // Drive motor outputs referencing 'robot' instance
            robot.frontLeft.setPower(((rotY + rotX + rx) / denominator) * speedMultiplier);
            robot.backLeft.setPower(((rotY - rotX + rx) / denominator) * speedMultiplier);
            robot.frontRight.setPower(((rotY - rotX - rx) / denominator) * speedMultiplier);
            robot.backRight.setPower(((rotY + rotX - rx) / denominator) * speedMultiplier);

            // Mechanism controls
            if (gamepad2.right_trigger > 0.1) {
                robot.intakeMotor.setPower(gamepad2.right_trigger);
            } else if (gamepad2.left_trigger > 0.1) {
                robot.intakeMotor.setPower(-gamepad2.left_trigger);
            } else {
                robot.intakeMotor.setPower(0.0);
            }

            robot.liftMotor.setPower(-gamepad2.left_stick_y);

            if (gamepad2.a) {
                robot.clawServo.setPosition(0.0);
            } else if (gamepad2.b) {
                robot.clawServo.setPosition(0.5);
            }
        }
    }
}