package org.firstinspires.ftc.teamcode.Commands.ChassisCommands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.ChassisSubsystem;

/**
 * Initialization command that resets the chassis IMU yaw orientation.
 */
public class ResetYaw extends CommandBase {
    ChassisSubsystem chassis;
    public ResetYaw(ChassisSubsystem chassis){
        this.chassis = chassis;
    }

    @Override
    public void initialize() {
        super.initialize();
        chassis.resetYaw();

    }
}
