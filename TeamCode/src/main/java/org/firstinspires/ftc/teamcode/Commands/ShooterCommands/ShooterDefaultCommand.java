package org.firstinspires.ftc.teamcode.Commands.ShooterCommands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;

/**
 * Default command for the ShooterSubsystem that continuously sets the flywheel motor
 * to the target setpoint.
 */
public class ShooterDefaultCommand extends CommandBase {
    private final ShooterSubsystem shooter;

    public ShooterDefaultCommand(ShooterSubsystem shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        super.execute();
        shooter.setShooterMotor(shooter.getTargetSpeed());
    }
}
