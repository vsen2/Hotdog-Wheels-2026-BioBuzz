package org.firstinspires.ftc.teamcode.Commands.ShooterCommands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

/**
 * Command that feeds a ball into the shooter flywheel using feedShoot()
 * and resets the feeder to stow position when ended.
 */
public class FeedShooter extends CommandBase {
    private final ShooterSubsystem shooter;

    public FeedShooter(ShooterSubsystem shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        super.initialize();
        shooter.feedShoot();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        shooter.resetFeed();
    }
}
