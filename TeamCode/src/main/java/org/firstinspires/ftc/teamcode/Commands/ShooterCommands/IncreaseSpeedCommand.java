package org.firstinspires.ftc.teamcode.Commands.ShooterCommands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

/**
 * Command that increases the shooter flywheel target speed.
 */
public class IncreaseSpeedCommand extends CommandBase {
    private final ShooterSubsystem shooter;
    private final double step;

    public IncreaseSpeedCommand(ShooterSubsystem shooter) {
        this(shooter, 0.05);
    }

    public IncreaseSpeedCommand(ShooterSubsystem shooter, double step) {
        this.shooter = shooter;
        this.step = step;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        super.initialize();
        shooter.increaseSpeed(step);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
