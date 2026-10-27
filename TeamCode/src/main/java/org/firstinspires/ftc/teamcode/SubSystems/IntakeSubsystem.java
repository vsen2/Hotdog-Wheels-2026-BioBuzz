package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.Testing.RobotConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final Motor intakeMotor;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        intakeMotor = new Motor(hardwareMap, RobotConstants.Hardware.INTAKE_MOTOR);
    }

    public void intake() {
        intakeMotor.set(RobotConstants.Teleop.INTAKE_SPEED);
    }

    public Motor getIntakeMotor() {
        return intakeMotor;
    }

    public void spit() {
        intakeMotor.set(RobotConstants.Teleop.SPIT_SPEED);
    }

    public void stopIntake() {
        intakeMotor.set(0);
    }
}
