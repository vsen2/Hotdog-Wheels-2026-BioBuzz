package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import org.firstinspires.ftc.teamcode.RobotConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final Motor intakeMotorFront;
    private final Motor intakeMotorBack;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        intakeMotorFront = new Motor(hardwareMap, RobotConstants.Hardware.INTAKE_MOTOR_FRONT);
        intakeMotorBack  = new Motor(hardwareMap, RobotConstants.Hardware.INTAKE_MOTOR_BACK);
    }

    public void intake() {
        intakeMotorFront.set(RobotConstants.Teleop.INTAKE_SPEED);
        intakeMotorBack.set(RobotConstants.Teleop.INTAKE_SPEED);
    }

    public void spit() {
        intakeMotorFront.set(RobotConstants.Teleop.SPIT_SPEED);
        intakeMotorBack.set(RobotConstants.Teleop.SPIT_SPEED);
    }

    public void stopIntake() {
        intakeMotorFront.set(0);
        intakeMotorBack.set(0);
    }

    public Motor getIntakeMotorFront() {
        return intakeMotorFront;
    }

    public Motor getIntakeMotorBack() {
        return intakeMotorBack;
    }
}
