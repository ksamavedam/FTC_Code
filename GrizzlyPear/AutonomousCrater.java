package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import java.util.List;
import java.lang.*;

@Autonomous(name="AutonomousCrater", group="Linear Opmode")

public class AutonomousCrater extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor motor1 = null;
    DcMotor motor2 = null;
    DcMotor motor3 = null;
    DcMotor motor4 = null;
    DcMotor latchMotor = null;
    DcMotor minShoulder = null;
    DcMotor minInOut = null;
    Servo depot = null;
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "AQapiaj/////AAABmR9MFwGXtUXlokDNoVqBfPgVJQUtQjEGM5ThSHmsuy4picaSUk8W+xn1vM+EV1DbJfrr58EOVEJdMfLFvG4An8oN8YDvHB44IGFPAmQBdHv3RkhbYEgWU/guwcEjwIXtcRTRt/J0PmTZG2xnyDxFfAk+AOUVtLE/Ze481z/We0oTolHGpStuPrUhGKGQcY7noVFb2q2LU/3DRoUKg7R1P7y93lluKthHr2BXZSFuHqN4CmEzXdeJKQ+vZrJxorguqiIdyiNvJ1fjXAtCATQeLAOwGQWp7HlRb/T9UUf/KXLcYxKKQV3NPtvE3iPuRkDceF3IvbPbX3i32+MKXZqKFHhpZwomR9PpqEzaxgG8pJ1I";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    @Override
    public void runOpMode() throws InterruptedException{
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor3 = hardwareMap.get(DcMotor.class, "motor3");
        motor4 = hardwareMap.get(DcMotor.class, "motor4");
        latchMotor = hardwareMap.get(DcMotor.class, "latchMotor");
        minShoulder = hardwareMap.get(DcMotor.class, "minShoulder");
        minInOut = hardwareMap.get(DcMotor.class, "minInOut");
        depot = hardwareMap.get(Servo.class, "depot");

        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor4.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        latchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        latchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        minShoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        minShoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        minInOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        minInOut.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setDirection(DcMotor.Direction.REVERSE);
        motor3.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            lowerPear();
            int encoders;
            encoders = diagonalEncoder(34);
            move(45,encoders,1, 0);
            encoders = verticalEncoder(12);
            move(0,encoders,0,0);
            encoders = rotationEncoder(128);
            rotate(encoders, 1);
            encoders = verticalEncoder(67);
            move(0,encoders,0,0);
            /*Read the sampling mineral
            double position;
            position = sampling();
            Lower Robot
            lowerPear();
            Run over gold mineral
            int encoders;
            if (position == 1){
                encoders = diagonalEncoder(39);
                move(30,encoders, 1);
                encoders = rotationEncoder(135);
                rotate(encoders);
                encoders = verticalEncoder(25);
                move(0,encoders,0);
                encoders = rotationEncoder(-170);
            }
            else if (position == 0){
                encoders = horizontalEncoder(54);
                move(0,encoders, 0);
                encoders = rotationEncoder(-44);
            }
            else{
                encoders = diagonalEncoder(39);
                move(120,encoders, 1);
                encoders = rotationEncoder(30);
                rotate(encoders);
                encoders = verticalEncoder(27);
                move(0,encoders,0);
                encoders = rotationEncoder(-90);
            }*/
            //Claim the depot
            claimDepot();
            //Move to crater
            encoders = rotationEncoder(-90);
            rotate(encoders, 0);
            encoders = verticalEncoder(82);
            move(0,encoders, 0, 0);
            //Extend the arm into the crater
            extendFull();
            // Show the elapsed game time
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
    public void lowerPear(){
            latchMotor.setTargetPosition(-37000);
            latchMotor.setPower(-1);
            while (opModeIsActive() && latchMotor.isBusy()){
            }
            latchMotor.setPower(0);
        }
    /*public int sampling(){
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        int position = -1;
        if (opModeIsActive()) {
            //Activate Tensor Flow Object Detection.
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                      telemetry.addData("# Object Detected", updatedRecognitions.size());
                      if (updatedRecognitions.size() == 3) {
                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;
                        for (Recognition recognition : updatedRecognitions) {
                          if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                          } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                          } else {
                            silverMineral2X = (int) recognition.getLeft();
                          }
                        }
                        if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                          if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Left");
                            position = -1;
                          } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Right");
                            position = 1;
                          } else {
                            telemetry.addData("Gold Mineral Position", "Center");
                            position = 0;
                          }
                        }
                      }
                      telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    return position;
    }
    private void initVuforia() {
        //Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "webcam");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }*/
    public int verticalEncoder(int distance) {
        int encoders;
        encoders = (int) Math.round(distance*31.008);
        return encoders;
    }
    public int horizontalEncoder(int distance) {
        int encoders;
        encoders = (int) Math.round(distance*37.037);
        return encoders;
    }
    public int rotationEncoder(int degrees) {
        int encoders;
        encoders = (int) Math.round(degrees*15.152);
        return encoders;
    }
    public int diagonalEncoder(int distance) {
        int encoders;
        encoders = (int) Math.round(distance*48.781);
        return encoders;
    }
    public void move(double direction, int encoders, int diagonal, int side) {
        double Power1;
        double Power2;
        double Power3;
        double Power4;
        double wheelRadius = 2;
        int lessEncoder = 1;
        if (diagonal == 1 || diagonal == 2){
            lessEncoder = (int) Math.round(.45*encoders);
        }
        direction = Math.toRadians(direction);
        Power1 = .75*(Math.cos(direction)+Math.sin(direction))/wheelRadius;
        Power2 = .75*(Math.cos(direction)-Math.sin(direction))/wheelRadius;
        Power3 = .75*(Math.cos(direction)+Math.sin(direction))/wheelRadius;
        Power4 = .75*(Math.cos(direction)-Math.sin(direction))/wheelRadius;
        // Send calculated power to wheels
        motor1.setPower(Power1);
        motor2.setPower(Power2);
        motor3.setPower(Power3);
        motor4.setPower(Power4);
        if (diagonal == 1){
            motor1.setTargetPosition(lessEncoder);
            motor2.setTargetPosition(encoders);
            motor3.setTargetPosition(lessEncoder);
            motor4.setTargetPosition(encoders);
        }
        else if (diagonal ==2){
            motor1.setTargetPosition(encoders);
            motor2.setTargetPosition(lessEncoder);
            motor3.setTargetPosition(encoders);
            motor4.setTargetPosition(lessEncoder);
        }
        else if (diagonal == 0){
            if (side == 0) {
                motor1.setTargetPosition(encoders);
                motor2.setTargetPosition(encoders);
                motor3.setTargetPosition(encoders);
                motor4.setTargetPosition(encoders);
            }
            else {
                motor1.setTargetPosition(encoders);
                motor2.setTargetPosition(-encoders);
                motor3.setTargetPosition(encoders);
                motor4.setTargetPosition(-encoders);
            }
        }
        while (opModeIsActive() && motor1.isBusy()){
        }
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        motor4.setPower(0);
    }
    public void rotate(int encoders, int way) {
        double radius = 8.25;
        double radiusAngle = 45;
        double wheelRadius = 2;
        double Power1;
        double Power2;
        double Power3;
        double Power4;
        Power1 = radius*.75*(Math.cos(Math.toRadians(180+radiusAngle))+Math.sin(Math.toRadians(180+radiusAngle)))/wheelRadius;
        Power2 = radius*.75*(Math.cos(Math.toRadians(90+radiusAngle))+Math.sin(Math.toRadians(90+radiusAngle)))/wheelRadius;
        Power3 = radius*.75*(Math.cos(Math.toRadians(90-radiusAngle))+Math.sin(Math.toRadians(90-radiusAngle)))/wheelRadius;
        Power4 = radius*.75*(Math.cos(Math.toRadians(270+radiusAngle))+Math.sin(Math.toRadians(270+radiusAngle)))/wheelRadius;
        motor1.setPower(Power1);
        motor2.setPower(Power2);
        motor3.setPower(Power3);
        motor4.setPower(Power4);
        if (way == 1) {
            motor1.setTargetPosition(encoders);
            motor2.setTargetPosition(-encoders);
            motor3.setTargetPosition(-encoders);
            motor4.setTargetPosition(encoders);
        }
        else if (way == 0) {
            motor1.setTargetPosition(-encoders);
            motor2.setTargetPosition(encoders);
            motor3.setTargetPosition(encoders);
            motor4.setTargetPosition(-encoders);
        }
        while (opModeIsActive() && motor1.isBusy()){
        }
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        motor4.setPower(0);
    }
    public void claimDepot(){
        depot.setPosition(0);
        sleep(30);
        depot.setPosition(1);
        sleep(30);
    }
    public void extendFull(){
        minShoulder.setPower(-2/3);
        //while (opModeIsActive() && runtime.time() < 30){
        //}
        //minShoulder.setPower(0);
        /*minElbow.setTargetPosition(1);
        minElbow.setPower(.8);
        while (opModeIsActive() && minElbow.isBusy()){
        }
        minElbow.setPower(0);*/
    }
}
