package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.OTOSConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.RobotConstants;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants().
            mass(13.335).
            headingPIDFCoefficients(new PIDFCoefficients(1.7, 0.01, 0.01, 0.03)).
            forwardZeroPowerAcceleration(-659.5692806276545).
            lateralZeroPowerAcceleration(-0.48801899326893067).
            translationalPIDFCoefficients(new PIDFCoefficients(0.1, 0.0006, 0.01, 0))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.0003, 0.000175, 0.00002, 0.0006, 0.01))
            ;
    public static OTOSConstants localizerConstants = new OTOSConstants()
            .hardwareMapName(RobotConstants.Hardware.OTOS_SENSOR)
            .offset(new SparkFunOTOS.Pose2D(-4.5, 4.8 , Math.toRadians(-90)))
            .linearUnit(DistanceUnit.INCH)
            .angleUnit(AngleUnit.RADIANS)
            .linearScalar(100) //1.0619549601275917
            .angularScalar(0.9914974734485172);
    public static PathConstraints pathConstraints = new PathConstraints(0.5, 100, 10, 0.5);
    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName(RobotConstants.Hardware.frontRight)
            .rightRearMotorName(RobotConstants.Hardware.backRight)
            .leftRearMotorName(RobotConstants.Hardware.backLeft)
            .leftFrontMotorName(RobotConstants.Hardware.frontLeft)
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(RobotConstants.Pedro.X_VELOCITY)
            .yVelocity(RobotConstants.Pedro.Y_VELOCITY);
    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .OTOSLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}
