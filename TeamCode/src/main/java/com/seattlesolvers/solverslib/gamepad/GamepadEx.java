package com.seattlesolvers.solverslib.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * GamepadEx wrapper in SolversLib.
 */
public class GamepadEx {
    private final Gamepad gamepad;

    public GamepadEx(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    public double getLeftX() {
        return gamepad != null ? gamepad.left_stick_x : 0.0;
    }

    public double getLeftY() {
        return gamepad != null ? gamepad.left_stick_y : 0.0;
    }

    public double getRightX() {
        return gamepad != null ? gamepad.right_stick_x : 0.0;
    }

    public double getRightY() {
        return gamepad != null ? gamepad.right_stick_y : 0.0;
    }

    public Gamepad getGamepad() {
        return gamepad;
    }
}
