package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.LimeLightSubsystem;

/**
 * Condition command that finishes when the Limelight subsystem detects a valid target result.
 */
public class LimelightSees extends CommandBase {
    private final LimeLightSubsystem limelightSubsystem;

    public LimelightSees(LimeLightSubsystem limelightSubsystem) {
        this.limelightSubsystem = limelightSubsystem;
    }

    @Override
    public boolean isFinished() {
        return limelightSubsystem.getLimelightResult() != null && limelightSubsystem.getLimelightResult().isValid();
    }
}
