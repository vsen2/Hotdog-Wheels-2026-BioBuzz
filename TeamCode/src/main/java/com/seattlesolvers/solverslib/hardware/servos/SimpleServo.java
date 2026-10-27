package com.seattlesolvers.solverslib.hardware.servos;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * SimpleServo class extending ServoEx.
 */
public class SimpleServo extends ServoEx {
    public SimpleServo(HardwareMap hardwareMap, String deviceName, double minAngle, double maxAngle) {
        super(hardwareMap, deviceName, minAngle, maxAngle);
    }
}
