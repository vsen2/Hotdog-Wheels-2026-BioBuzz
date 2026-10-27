package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import org.firstinspires.ftc.teamcode.Testing.RobotConstants;

public class ShooterSubsystem extends SubsystemBase {
    public Motor shooterMotor;
    public Servo hood, lifter;
    public double setpoint = RobotConstants.Teleop.FAR_SHOT;
    public double speedStep = 0.05; // Speed adjustment step per button press

    public ShooterSubsystem(String[] shooter, HardwareMap hardwareMap) {
        this.shooterMotor = new Motor(hardwareMap, shooter[0]);
        if (shooter.length > 1) {
            try {
                this.hood = hardwareMap.get(Servo.class, shooter[1]);
            } catch (Exception ignored) {}
        }
        if (shooter.length > 2) {
            try {
                this.lifter = hardwareMap.get(Servo.class, shooter[2]);
            } catch (Exception ignored) {}
        }

        if (shooter.length > 3) {
            shooterMotor.setInverted("T".equals(shooter[3]));
        }
        if (lifter != null && shooter.length > 5) {
            lifter.setDirection("T".equals(shooter[5]) ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);
        }
    }

    public double getSpeed() {
        if (shooterMotor != null && shooterMotor.getDcMotor() != null) {
            return shooterMotor.getDcMotor().getPower();
        }
        return 0.0;
    }

    public Motor getShooterMotor() {
        return shooterMotor;
    }

    public void setShooterMotor(double speed) {
        this.setpoint = Math.max(0.0, Math.min(1.0, speed));
        if (shooterMotor != null) {
            shooterMotor.set(this.setpoint);
        }
    }

    /**
     * Increase the target shooter speed by speedStep (default 0.05).
     */
    public void increaseSpeed() {
        increaseSpeed(speedStep);
    }

    /**
     * Increase target shooter speed by a specific amount.
     */
    public void increaseSpeed(double amount) {
        setpoint = Math.min(1.0, setpoint + amount);
        if (shooterMotor != null) {
            shooterMotor.set(setpoint);
        }
    }

    /**
     * Decrease the target shooter speed by speedStep (default 0.05).
     */
    public void decreaseSpeed() {
        decreaseSpeed(speedStep);
    }

    /**
     * Decrease target shooter speed by a specific amount.
     */
    public void decreaseSpeed(double amount) {
        setpoint = Math.max(0.0, setpoint - amount);
        if (shooterMotor != null) {
            shooterMotor.set(setpoint);
        }
    }

    public double getTargetSpeed() {
        return setpoint;
    }

    public void setHood(double position) {
        if (hood != null) {
            hood.setPosition(position);
        }
    }

    /**
     * Feeds the ball into the flywheel.
     */
    public void feedShoot() {
        if (lifter != null) {
            lifter.setPosition(RobotConstants.Teleop.FEED);
        }
    }

    /**
     * Resets the feeder servo back to stow position.
     */
    public void resetFeed() {
        if (lifter != null) {
            lifter.setPosition(RobotConstants.Teleop.STOW_FEEDER);
        }
    }

    public void liftArm() {
        if (lifter != null) {
            lifter.setPosition(RobotConstants.Teleop.INTAKE_FEEDER);
        }
    }
}
