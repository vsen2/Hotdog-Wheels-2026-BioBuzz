package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.SimpleServoExtKt;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

import org.firstinspires.ftc.teamcode.RobotConstants;

public class ShooterSubsystem extends SubsystemBase {
    public Motor shooterMotor;
    public ServoEx hood, lifter;
    public RevColorSensorV3 colourSensor;
    public double setpoint = RobotConstants.Teleop.FAR_SHOT;
    public ShooterSubsystem(String[] shooter, HardwareMap hardwareMap){
        this.shooterMotor = new Motor(hardwareMap, shooter[0]);
        this.hood = new ServoEx(hardwareMap, shooter[1]);
        this.lifter = new ServoEx(hardwareMap, shooter[2], 0, 1);
        this.colourSensor = hardwareMap.get(RevColorSensorV3.class, shooter[4]);
        shooterMotor.setInverted(shooter[3] == "T");
        lifter.setInverted(shooter[5] != "T");
    }
    public double getSpeed(){
        return shooterMotor.getCorrectedVelocity();
    }

    public Motor getShooterMotor(){
        return shooterMotor;
    }

    public void setShooterMotor(double speed){
        shooterMotor.set(speed);
    }

    public void setHood(double angle){
        hood.set(angle);
    }
    public double[] rgb(){
        return new double[]{colourSensor.red(), colourSensor.green(), colourSensor.blue()};
    }
    public char getColour(){
        double[]rgb = new double[]{colourSensor.red(), colourSensor.green(), colourSensor.blue()};
        double[] min = RobotConstants.Teleop.WHITE_RGB;
        if(Math.sqrt(Math.pow(min[0]-rgb[0], 2)+Math.pow(min[1]-rgb[1], 2)+Math.pow(min[2]-rgb[2], 2))<RobotConstants.Teleop.WHITE_THRESHOLD){
            return 'n';
        }
        else if(rgb[0]<RobotConstants.Teleop.INTAKE_MIN){
            return 'g';
        }else{
            return 'r';
        }
    }
    public boolean isGreen() {
        int red = colourSensor.red(); //when teh camera scans for RGB, it returns a number value. Purple is red+blue, and green is green (duh). If the green number is more than the others, it should be green.
        int blue = colourSensor.blue();
        int green = colourSensor.green();

        return green > red && green > blue; //If it's more green then red/blue(which makes purple), it's green
    }

    public void feedShoot(){
        lifter.set(RobotConstants.Teleop.FEED);
    }
    public void resetFeed()
    {
        lifter.set(RobotConstants.Teleop.STOW_FEEDER);
    }
    public void liftArm(){
        lifter.set(RobotConstants.Teleop.INTAKE_FEEDER);
    }
}