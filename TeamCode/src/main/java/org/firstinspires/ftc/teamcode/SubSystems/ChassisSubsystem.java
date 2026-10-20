package org.firstinspires.ftc.teamcode.Subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS.Pose2D;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.command.MecanumControllerCommand;
import com.seattlesolvers.solverslib.command.Robot;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.nio.file.Path;

public class ChassisSubsystem extends SubsystemBase {
    MotorEx fl, fr, bl, br;
    HardwareMap hardwareMap;
    SparkFunOTOS otos;
    PIDController PIDchassis;
    IMU imu;
    MecanumDrive drive;
    LLResult LLresults;
    Follower follower;
//    Follower follower;

    public ChassisSubsystem(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;

        fl = new MotorEx(hardwareMap, RobotConstants.Hardware.FRONT_LEFT_MOTOR,
                RobotConstants.Hardware.DRIVE_MOTOR_TYPE);
        fr = new MotorEx(hardwareMap, RobotConstants.Hardware.FRONT_RIGHT_MOTOR,
                RobotConstants.Hardware.DRIVE_MOTOR_TYPE);
        bl = new MotorEx(hardwareMap, RobotConstants.Hardware.BACK_LEFT_MOTOR,
                RobotConstants.Hardware.DRIVE_MOTOR_TYPE);
        br = new MotorEx(hardwareMap, RobotConstants.Hardware.BACK_RIGHT_MOTOR,
                RobotConstants.Hardware.DRIVE_MOTOR_TYPE);
//        fl.setInverted(true);
//        bl.setInverted(true);
        fl.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        this.drive = new MecanumDrive(fl, fr, bl, br);
        follower = Constants.createFollower(hardwareMap);
        otos = hardwareMap.get(SparkFunOTOS.class, RobotConstants.Hardware.OTOS_SENSOR);
        otos.setAngularUnit(AngleUnit.DEGREES);
        imu = hardwareMap.get(IMU.class, "imu");

//        this.follower = Constants.createFollower(hardwareMap);

        PIDchassis = new PIDController(
                RobotConstants.Tuning.CHASSIS_TURN_PID_COEFFICIENTS[0],
                RobotConstants.Tuning.CHASSIS_TURN_PID_COEFFICIENTS[1],
                RobotConstants.Tuning.CHASSIS_TURN_PID_COEFFICIENTS[2]);
    }


    public void initBlue(){
        imu.initialize(new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        ));
    }
    public Pose getPosition(){
        return follower.getPose();
    }
    public void initRed(){
        otos.setPosition(new Pose2D(0,0,Math.toRadians(90)));
    }
    public void setStartPos(Pose startPos){
        follower.setStartingPose(startPos);
    }
    public void driveRobotOriented(double strafeSpeed, double forwardSpeed, double turnSpeed){
        drive = new MecanumDrive(fl, fr, bl, br);
        drive.driveRobotCentric(strafeSpeed, forwardSpeed, turnSpeed);
    }

    public void driveFieldOriented(double strafeSpeed, double forwardSpeed, double turnSpeed){
        drive = new MecanumDrive(fl, fr, bl, br);
        drive.driveFieldCentric(strafeSpeed, forwardSpeed, turnSpeed,
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
    }

    public void fieldOriented(double strafeSpeed, double forwardSpeed, double turn) {
        double yaw = otos.getPosition().h; // calculated from IMU
//        double yaw = Math.toDegrees(otos.getPosition().h);
        drive = new MecanumDrive(fl, fr, bl, br);
        drive.driveFieldCentric(strafeSpeed, forwardSpeed, turn, yaw);
//
//        double rotX = (strafeSpeed * Math.cos(yaw)) + (forwardSpeed * Math.sin(yaw));
//        double rotY = (strafeSpeed * Math.sin(yaw)) - (forwardSpeed * Math.cos(yaw));
//        drive.driveRobotCentric(rotX, rotY, turn, false);

    }

    public void resetYaw(){
        imu.resetYaw();
    }

    public Pose3D getPoseLL(){
        if (LLresults.isValid()){
            return LLresults.getBotpose_MT2();
        } else {
            return null;
        }
    }
    public void setStartPoseOTOS(Pose2D pose){
        otos.resetTracking();
        otos.setPosition(pose);
    }
    public SparkFunOTOS getOtos(){
        return otos;
    }
    public Follower getFollower(){
        return follower;
    }
    public void stop(){
        drive.stop();
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}
