package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;


public class RobotHardware {
    double width;
    double length;
    double wheelRadius;
    double encPerInchV;
    double encPerInchH;
    double encPerInchR;
    double encPerInchD;
    DcMotor latchMotor = null;
    DcMotor motor1 = null;
    DcMotor motor2 = null;
    DcMotor motor3 = null;
    DcMotor motor4 = null;
    DcMotor intakeArm = null;
    DcMotor extendMotor = null;
    DcMotor dumpMotor = null;
    // The IMU sensor object
    BNO055IMU imu;
    Servo depot = null;
    CRServo intake = null;
    HardwareMap hwMap = null;

    public  RobotHardware(String name, HardwareMap hw) {
        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();


        if (name == "GrizzlyPear"){
            wheelRadius = 2;
            width = 11.7;
            length = 11.7;
            encPerInchV = 25.5 ; // 31.008;
            encPerInchH =  37.037*1.25;
            encPerInchR = 15.152;
            encPerInchD = 48.781;
        }
        else if (name == "Robot2"){
            //0 = TBD
            wheelRadius = 3;
            width = 15.5;
            length = 17.5;
            encPerInchV = 26.144;
            encPerInchH = 0;
            encPerInchR = 0;
            encPerInchD = 0;
        }
        hwMap = hw;
        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        latchMotor = hwMap.get(DcMotor.class, "latchMotor");
        motor1 = hwMap.get(DcMotor.class, "motor1");
        motor2 = hwMap.get(DcMotor.class, "motor2");
        motor3 = hwMap.get(DcMotor.class, "motor3");
        motor4 = hwMap.get(DcMotor.class, "motor4");
        intakeArm = hwMap.get(DcMotor.class, "intakeArm");
        extendMotor = hwMap.get(DcMotor.class, "extendMotor");
        depot = hwMap.get(Servo.class, "depot");
        intake = hwMap.get(CRServo.class, "intake");
        dumpMotor = hwMap.get(DcMotor.class, "dumpMotor");
        motor2.setDirection(DcMotor.Direction.REVERSE);
        motor3.setDirection(DcMotor.Direction.REVERSE);
        
    }
    public double widthReturn(){
        return width;
    }
    public double lengthReturn(){
        return length;
    }
    public double wheelReturn(){
        return wheelRadius;
    }
}
