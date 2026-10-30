package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConstants;

/**
 * Subsystem responsible for controlling the robot's single shooter mechanism,
 * including the flywheel motor, hood angle servo, and feeder/lifter servo.
 */
public class ShooterSubsystem extends SubsystemBase {
    private final Motor shooterMotor;
    private Servo hood;
    private Servo lifter;

    private double setpoint = RobotConstants.Teleop.FAR_SHOT; // Target speed (0.0 to 1.0)
    private final double speedStep = 0.05;                    // Increment for speed adjustments
    private Telemetry telemetry;

    /**
     * Standard constructor for a single shooter using HardwareMap and Telemetry.
     */
    public ShooterSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize Flywheel Motor
        this.shooterMotor = new Motor(hardwareMap, RobotConstants.Hardware.SHOOTER_MOTOR_1);

        // Initialize Hood Servo
        try {
            this.hood = hardwareMap.get(Servo.class, RobotConstants.Hardware.HOOD_SERVO);
        } catch (Exception ignored) {}

        // Initialize Feeder/Lifter Servo
        try {
            this.lifter = hardwareMap.get(Servo.class, RobotConstants.Hardware.RAMP_SERVO);
        } catch (Exception ignored) {}
    }

    public ShooterSubsystem(HardwareMap hardwareMap) {
        this(hardwareMap, null);
    }

    public ShooterSubsystem(String[] shooter, HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.shooterMotor = new Motor(hardwareMap, shooter[0]);
        if (shooter.length > 1) {
            try { this.hood = hardwareMap.get(Servo.class, shooter[1]); } catch (Exception ignored) {}
        }
        if (shooter.length > 2) {
            try { this.lifter = hardwareMap.get(Servo.class, shooter[2]); } catch (Exception ignored) {}
        }
        if (shooter.length > 3) {
            shooterMotor.setInverted("T".equals(shooter[3]));
        }
    }

    public ShooterSubsystem(String[] shooter, HardwareMap hardwareMap) {
        this(shooter, hardwareMap, null);
    }

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public void periodic() {
        super.periodic();
        updateTelemetry();
    }

    /**
     * Set target flywheel motor speed (clamped between 0.0 and 1.0).
     */
    public void setShooterMotor(double speed) {
        this.setpoint = Math.max(0.0, Math.min(1.0, speed));
        if (shooterMotor != null) {
            shooterMotor.set(this.setpoint);
        }
    }

    /**
     * Increase target shooter speed by a step amount.
     */
    public void increaseSpeed(double amount) {
        setShooterMotor(setpoint + amount);
    }

    public void increaseSpeed() {
        increaseSpeed(speedStep);
    }

    /**
     * Decrease target shooter speed by a step amount.
     */
    public void decreaseSpeed(double amount) {
        setShooterMotor(setpoint - amount);
    }

    public void decreaseSpeed() {
        decreaseSpeed(speedStep);
    }

    public double getTargetSpeed() {
        return setpoint;
    }

    public double getSpeed() {
        return shooterMotor != null ? shooterMotor.get() : 0.0;
    }

    /**
     * Adjust the hood servo position.
     */
    public void setHood(double position) {
        if (hood != null) {
            hood.setPosition(position);
        }
    }

    /**
     * Feeds game piece into the flywheel.
     */
    public void feedShoot() {
        if (lifter != null) {
            lifter.setPosition(RobotConstants.Teleop.FEED);
        }
    }

    /**
     * Resets feeder servo back to stow position.
     */
    public void resetFeed() {
        if (lifter != null) {
            lifter.setPosition(RobotConstants.Teleop.STOW_FEEDER);
        }
    }

    /**
     * Position feeder servo at intake feeder height.
     */
    public void liftArm() {
        if (lifter != null) {
            lifter.setPosition(RobotConstants.Teleop.INTAKE_FEEDER);
        }
    }

    public Motor getShooterMotor() {
        return shooterMotor;
    }

    private void updateTelemetry() {
        if (telemetry != null) {
            telemetry.addData("Shooter Target Speed", "%.2f (%.0f%%)", getTargetSpeed(), getTargetSpeed() * 100);
            telemetry.addData("Shooter Motor Power", "%.2f", getSpeed());
        }
    }
}
