package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


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
    Servo depot = null;
    HardwareMap hwMap = null;
    
    public  RobotHardware(String name, HardwareMap hw) {
        if (name == "GrizzlyPear"){
            wheelRadius = 2;
            width = 11.7;
            length = 11.7;
            encPerInchV = 31.008;
            encPerInchH = 37.037;
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
        //latchMotor = hwMap.get(DcMotor.class, "latchMotor");
        motor1 = hwMap.get(DcMotor.class, "motor1");
        motor2 = hwMap.get(DcMotor.class, "motor2");
        motor3 = hwMap.get(DcMotor.class, "motor3");
        motor4 = hwMap.get(DcMotor.class, "motor4");
        //intakeArm = hwMap.get(DcMotor.class, "intakeArm");
        //depot = hwMap.get(Servo.class, "depot");
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
