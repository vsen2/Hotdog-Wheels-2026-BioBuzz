package org.firstinspires.ftc.teamcode.Testing;


public class RobotConstants {
    public static class Hardware {
        public static String FRONT_LEFT_MOTOR = "frontLeft";
        public static String FRONT_RIGHT_MOTOR = "frontRight";
        public static String BACK_LEFT_MOTOR = "backLeft";
        public static String BACK_RIGHT_MOTOR = "backRight";
        public static String INTAKE_MOTOR = "intake";
        public static String SHOOTER_MOTOR_1 = "shooterTop";
        public static String SHOOTER_MOTOR_2 = "shooterBottom";
        public static String HOOD_SERVO = "hood";
        public static String TURRET_SERVO_LEFT = "turretLeft";
        public static String TURRET_SERVO_RIGHT = "turretRight";
        public static String INTAKE_SERVO_LEFT = "intakeLeft";
        public static String INTAKE_SERVO_RIGHT = "intakeRight";
        public static String RAMP_SERVO = "ramp";

        // SENSORS
        public static String OTOS_SENSOR = "otos";
        public static String LIME_LIGHT = "limelight";
        public static String[] COLOUR_SENSORS = {"LeftColourSensor"};
    }

    public static class Teleop {
        public static boolean DEFAULT_FIELD_CENTRIC = true;
        public static double INTAKE_SPEED = 1.0;
        public static double SPIT_SPEED = -1.0;
        public static double FAR_SHOT = 0.8;
        public static double[] WHITE_RGB = {255, 255, 255};
        public static double WHITE_THRESHOLD = 50.0;
        public static double INTAKE_MIN = 100.0;
        public static double FEED = 0.5;
        public static double STOW_FEEDER = 0.0;
        public static double INTAKE_FEEDER = 0.2;
    }

    public static class Tuning {
        public static double[] CHASSIS_TURN_PID_COEFFICIENTS = {0.05, 0.0, 0.005};
        public static double TA_TO_SPEED = 1.0;
    }
}