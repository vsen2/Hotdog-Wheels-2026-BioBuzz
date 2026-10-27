package org.firstinspires.ftc.teamcode.Commands.ChassisCommands;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.ChassisSubsystem;

/**
 * Initialization command that resets the chassis OTOS start pose to origin (0, 0, 0).
 */
public class ResetPosition extends CommandBase {
    ChassisSubsystem chassis;
    public ResetPosition(ChassisSubsystem chassis){
        this.chassis = chassis;
    }

    @Override
    public void initialize() {
        super.initialize();
        chassis.setStartPoseOTOS(new SparkFunOTOS.Pose2D(0,0,0));

    }
}
