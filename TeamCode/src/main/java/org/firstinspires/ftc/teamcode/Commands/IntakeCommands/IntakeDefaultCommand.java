package org.firstinspires.ftc.teamcode.Commands.IntakeCommands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

/**
 * Default command for the intake subsystem that keeps the intake stopped.
 */
public class IntakeDefaultCommand extends CommandBase {
    IntakeSubsystem intake;
    public IntakeDefaultCommand(IntakeSubsystem intake){
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        super.initialize();
        intake.stopIntake();
    }
}
