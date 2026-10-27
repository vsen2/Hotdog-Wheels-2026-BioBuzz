package com.seattlesolvers.solverslib.command;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Base LinearOpMode class for command-based OpModes in SolversLib.
 */
public abstract class CommandOpMode extends LinearOpMode {
    public abstract void initialize();

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        while (opModeIsActive()) {
            // Periodic execution loop
        }
    }
}
