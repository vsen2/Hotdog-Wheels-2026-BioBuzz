package com.seattlesolvers.solverslib.controller;

/**
 * PID Controller implementation in SolversLib.
 */
public class PIDController {
    private double kP, kI, kD;
    private double setpoint = 0;
    private double errorSum = 0;
    private double lastError = 0;

    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double calculate(double pv) {
        return calculate(pv, setpoint);
    }

    public double calculate(double pv, double setpoint) {
        double error = setpoint - pv;
        errorSum += error;
        double errorDeriv = error - lastError;
        lastError = error;
        return kP * error + kI * errorSum + kD * errorDeriv;
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    public void reset() {
        errorSum = 0;
        lastError = 0;
    }
}
