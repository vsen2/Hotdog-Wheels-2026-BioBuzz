package com.seattlesolvers.solverslib.command;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base class for commands in SolversLib.
 */
public abstract class CommandBase {
    protected final Set<SubsystemBase> requirements = new HashSet<>();

    public void addRequirements(SubsystemBase... subsystems) {
        Collections.addAll(requirements, subsystems);
    }

    public Set<SubsystemBase> getRequirements() {
        return requirements;
    }

    public void initialize() {
    }

    public void execute() {
    }

    public void end(boolean interrupted) {
    }

    public boolean isFinished() {
        return false;
    }
}
