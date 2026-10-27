package com.seattlesolvers.solverslib.hardware.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Wrapper for FTC DcMotor in SolversLib.
 */
public class Motor {
    private final DcMotor dcMotor;

    public Motor(HardwareMap hardwareMap, String deviceName) {
        this.dcMotor = hardwareMap.get(DcMotor.class, deviceName);
    }

    public void set(double power) {
        if (dcMotor != null) {
            dcMotor.setPower(power);
        }
    }

    public void setInverted(boolean inverted) {
        if (dcMotor != null) {
            dcMotor.setDirection(inverted ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        }
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        if (dcMotor != null) {
            dcMotor.setZeroPowerBehavior(behavior);
        }
    }

    public double getCorrectedVelocity() {
        if (dcMotor instanceof DcMotorEx) {
            return ((DcMotorEx) dcMotor).getVelocity();
        } else if (dcMotor != null) {
            return dcMotor.getPower();
        }
        return 0.0;
    }

    public DcMotor getDcMotor() {
        return dcMotor;
    }
}
