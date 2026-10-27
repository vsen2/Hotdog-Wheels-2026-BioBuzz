package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotConstants {

    // Hardware Members
    public DcMotor frontLeft = null;
    public DcMotor backLeft = null;
    public DcMotor frontRight = null;
    public DcMotor backRight = null;

    public DcMotor intakeMotor = null;
    public DcMotor liftMotor = null;
    public Servo clawServo = null;

    public IMU imu = null;

    // Reference to FTC HardwareMap
    private HardwareMap hwMap = null;

    public RobotConstants() {
        // Constructor remains empty
    }

    /**
     * Initialize all hardware interfaces.
     * Call this inside runOpMode() in TeleOp and Autonomous.
     */
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        // Map Drivetrain Motors
        frontLeft  = hwMap.get(DcMotor.class, "frontLeft");
        backLeft   = hwMap.get(DcMotor.class, "backLeft");
        frontRight = hwMap.get(DcMotor.class, "frontRight");
        backRight  = hwMap.get(DcMotor.class, "backRight");

        // Set Drivetrain Motor Directions
        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set Drivetrain RunModes & Brake Modes
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Map Mechanism Hardware
        intakeMotor = hwMap.get(DcMotor.class, "intake");
        liftMotor   = hwMap.get(DcMotor.class, "lift");
        clawServo   = hwMap.get(Servo.class, "claw");

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize IMU
        imu = hwMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
        imu.initialize(parameters);
    }
}

