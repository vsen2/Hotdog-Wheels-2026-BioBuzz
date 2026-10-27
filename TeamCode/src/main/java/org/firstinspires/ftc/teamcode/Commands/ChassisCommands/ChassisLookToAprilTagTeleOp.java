package org.firstinspires.ftc.teamcode.Commands.ChassisCommands;

import com.bylazar.telemetry.TelemetryManager;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Testing.RobotConstants;
import org.firstinspires.ftc.teamcode.Subsystems.ChassisSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.LimelightSubsystem;

/**
 * Default tele-op chassis command using PID control and Limelight vision
 * to automatically point the robot towards an AprilTag while driving field-oriented.
 */
public class ChassisLookToAprilTagTeleOp extends CommandBase {
    ChassisSubsystem chassis;
    LimelightSubsystem limelight;
    PIDController hPID;
    TelemetryManager telemetryManager;
    GamepadEx gamepadEx;

    public ChassisLookToAprilTagTeleOp(ChassisSubsystem chassisSubsystem,
                                       LimelightSubsystem limelight, TelemetryManager telemetry,
                                       GamepadEx gamepadEx){
        chassis = chassisSubsystem;
        this.telemetryManager = telemetry;
        this.limelight = limelight;
        this.gamepadEx = gamepadEx;
        this.hPID = new PIDController(
                RobotConstants.Tuning.CHASSIS_PID_COEFFICIENTS_POINT[0],
                RobotConstants.Tuning.CHASSIS_PID_COEFFICIENTS_POINT[1],
                RobotConstants.Tuning.CHASSIS_PID_COEFFICIENTS_POINT[2]
        );
        addRequirements(chassisSubsystem);
    }

    @Override
    public void initialize(){
        super.initialize();
    }

    @Override
    public void execute(){
        super.execute();
        if(limelight.getLimelightResult().isValid()){
            double autoPointSpeed= RobotConstants.clamp(hPID.calculate(limelight.getLimelightResult().getTx()), -1, 1);
            chassis.fieldOriented(-gamepadEx.getLeftX(), -gamepadEx.getLeftY(), autoPointSpeed);
        }else{
            chassis.fieldOriented(-gamepadEx.getLeftX(), -gamepadEx.getLeftY(), -gamepadEx.getRightX());
        }
    }

    @Override
    public void end(boolean interrupted){
        super.end(interrupted);
        hPID.reset();
    }

}
