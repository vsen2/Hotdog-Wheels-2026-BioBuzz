package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Testing.RobotConstants;

public class LimelightSubsystem {
    private Limelight3A limelight;

    public LimelightSubsystem(HardwareMap hardwareMap) {
        this.limelight = hardwareMap.get(Limelight3A.class, RobotConstants.Hardware.LIME_LIGHT);
        this.limelight.setPollRateHz(100);
        this.limelight.start();
    }

    public Limelight3A getLimelight() {
        return limelight;
    }

    public double getShooterSpeedUsingLL() {
        return Math.sqrt(1 / limelight.getLatestResult().getTa()) * RobotConstants.Tuning.TA_TO_SPEED;
    }

    public LLResult getLimelightResult() {
        return limelight.getLatestResult();
    }
}
