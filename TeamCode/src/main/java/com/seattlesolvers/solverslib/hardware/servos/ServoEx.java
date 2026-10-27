package com.seattlesolvers.solverslib.hardware.servos;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Extended Servo wrapper for SolversLib.
 */
public class ServoEx {
    private final Servo servo;
    private boolean inverted = false;
    private double minAngle = 0;
    private double maxAngle = 180;

    public ServoEx(HardwareMap hardwareMap, String deviceName) {
        this.servo = hardwareMap.get(Servo.class, deviceName);
    }

    public ServoEx(HardwareMap hardwareMap, String deviceName, double minAngle, double maxAngle) {
        this.servo = hardwareMap.get(Servo.class, deviceName);
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
        if (servo != null) {
            servo.setDirection(inverted ? Servo.Direction.REVERSE : Servo.Direction.FORWARD);
        }
    }

    public boolean isInverted() {
        return inverted;
    }

    public void set(double position) {
        if (servo != null) {
            servo.setPosition(position);
        }
    }

    public void setPosition(double position) {
        set(position);
    }

    public void turnToAngle(double angle) {
        if (maxAngle - minAngle != 0) {
            double pos = (angle - minAngle) / (maxAngle - minAngle);
            set(pos);
        }
    }

    public Servo getServo() {
        return servo;
    }
}
