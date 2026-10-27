package org.firstinspires.ftc.teamcode.Commands.ChassisCommands;

import com.bylazar.telemetry.TelemetryManager;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Subsystems.ChassisSubsystem;

/**
 * Default idle chassis command for tele-op.
 */
public class RobotDefaultCommands extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    ChassisSubsystem chassisSubsystem;
    TelemetryManager telemetryManager;
    GamepadEx gamepadEx;

    /**
     *
     * @param chassisSubsystem drive subsystem
     * @param telemetryManager telemetry
     */

    public RobotDefaultCommands(ChassisSubsystem chassisSubsystem, TelemetryManager telemetryManager){
        this.chassisSubsystem = chassisSubsystem;
        this.telemetryManager = telemetryManager;
        addRequirements(chassisSubsystem);
    }

    @Override
    public void execute() {
        super.execute();
    }


}
