package org.firstinspires.ftc.teamcode.Commands.IntakeCommands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

/**
 * Command that activates the intake mechanism to pull game pieces in
 * and stops it when ended.
 */
public class IntakeCommand extends CommandBase {
    private final IntakeSubsystem intake;

    public IntakeCommand(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        super.initialize();
        intake.intake();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        intake.stopIntake();
    }
}
