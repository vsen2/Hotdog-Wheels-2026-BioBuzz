package org.firstinspires.ftc.teamcode.Commands.IntakeCommands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;

/**
 * Command that reverses the intake mechanism to eject/spit out game pieces
 * and stops it when ended.
 */
public class SpitCommand extends CommandBase {
    IntakeSubsystem intake;
    public SpitCommand(IntakeSubsystem intake){
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        super.initialize();
        intake.spit();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        intake.stopIntake();
    }
}