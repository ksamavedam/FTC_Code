/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.Gamepad;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the FourWheeledRobot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "TeleOpNoReset", group = "TeleOpModes")
public class TeleOpNoReset extends LinearOpMode {

        private GrizzlyPear robot;
    private MechanoMotion motion;
    private Minerals minerals;
    public ElapsedTime runtime = new ElapsedTime();
    DcMotor extendMotor = null;
    DcMotor latchMotor = null;
    DcMotor intakeArm = null;
    DcMotor dumpMotor = null;
    CRServo intake = null;
    boolean slideDone = true;
    boolean armDone = false;
    boolean transfer = false;
    boolean dumping = true;
    boolean joystickzero = true;
    boolean intakeArmZero = true;
    double power;
    double count;
    
    @Override
    public void runOpMode() {
        robot = new GrizzlyPear(hardwareMap);
        motion = new MechanoMotion(robot);
        minerals = new Minerals(telemetry, gamepad1, gamepad2, robot.getDunkMotor());
        extendMotor = hardwareMap.get(DcMotor.class, "extendMotor");
        latchMotor = hardwareMap.get(DcMotor.class, "latchMotor");
        intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
        dumpMotor = hardwareMap.get(DcMotor.class, "dumpMotor");
        intake = hardwareMap.get(CRServo.class, "intake");
        
        //extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //intakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        //dumpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dumpMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        runtime.reset();
        waitForStart();
        

        while (opModeIsActive()) {
            //MAANAV'S CONTROLS (GAMEPAD1)
            
            //Moving (Speed)
            Motion movement = motion.rightwardsMotion(gamepad1.right_stick_x)
                    .add(motion.forwardMotion(-gamepad1.right_stick_y))
                    .add(motion.rotationMotion(gamepad1.left_stick_x));
                    
            //Moving (Control)
            if (gamepad1.dpad_left) {
                movement = movement.add(motion.forwardMotion(0.5));
            }
            if (gamepad1.dpad_right) {
                movement = movement.add(motion.forwardMotion(-0.5));
            }
            if (gamepad1.dpad_up) {
                movement = movement.add(motion.rightwardsMotion(1));
            }
            if (gamepad1.dpad_down) {
                movement = movement.add(motion.rightwardsMotion(-1));
            }
            if (gamepad1.right_trigger > 0) {
                movement = movement.add(motion.rotationMotion(.4));
            }
            if (gamepad1.left_trigger > 0) {
                movement = movement.add(motion.rotationMotion(-.4));
            }
            
            motion.executeRate(movement);
            
            //Scoring Basket
            /*if(gamepad1.x){
                if(dumpMotor.getCurrentPosition()>-400){
                    dumpMotor.setTargetPosition(-400);
                    dumpMotor.setPower(.9);
                }
                else if(dumpMotor.getCurrentPosition()>-700){
                    dumpMotor.setTargetPosition(-700);
                    dumpMotor.setPower(.5);
                }
                else if(dumpMotor.getCurrentPosition()>-910){
                    dumpMotor.setTargetPosition(-910);
                    dumpMotor.setPower(.3);
                }
                else{
                    sleep(2000);
                    dumpMotor.setTargetPosition(0);
                    dumpMotor.setPower(.2);
                }
             }*/
             
            // Auto score -- currently not in use 
            if (gamepad1.x) {
                count = 0;
                power = -.3;
                while (dumpMotor.getCurrentPosition() > -1000) {
                    dumpMotor.setPower(power);
                    count++;
                    if (count/50 == (int)count/50) {
                        power -= .1;
                        if (power < -1) {
                            power = -1;
                        }
                    }
                    telemetry.addData("Encoders", dumpMotor.getCurrentPosition());
                    telemetry.update();
                }
                runtime.reset();
                while (runtime.time() < .2) {
                    dumpMotor.setPower(-.1);
                }
                dumpMotor.setPower(0);
                sleep(750);
                while (dumpMotor.getCurrentPosition() < -750) {
                    dumpMotor.setPower(.2);
                }
                dumpMotor.setPower(0);
            }
            //dumpMotor.setPower(0);
            //Dumping without encoders
            //Down
            if(gamepad1.left_bumper){
                dumpMotor.setPower(.8);
                dumping = false;
            }    
            //Up
            else if(gamepad1.right_bumper){
                dumpMotor.setPower(-.8);
                dumping = false;
            }
            else if (!dumping && !gamepad1.left_bumper && !gamepad1.right_bumper) {
                dumpMotor.setPower(0);
                dumping = true;
            }
            /*else {
                dumpMotor.setPower(0);
            }*/
            
            //HARBIN'S CONTROLS (GAMEPAD2)
            
            //Latching
            latchMotor.setPower(gamepad2.right_stick_y);
            
            //Linear Slides (Fast)
            if (Math.abs(gamepad2.left_stick_y) > 0) {
                joystickzero = false;
                extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
                extendMotor.setPower(gamepad2.left_stick_y);
            }
            
            else if (gamepad2.y) {
                extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extendMotor.setTargetPosition(-976);
                extendMotor.setPower(.9);
                transfer = true;
                /*if (runtime.time() > 1) {
                    intakeArm.setPower(0);
                    armDone = true;
                    slideDone= true;
                }*/
                if(extendMotor.getCurrentPosition()<=-976 && extendMotor.getCurrentPosition()>=-990){
                    if(intakeArm.getCurrentPosition()<-10){
                        intakeArm.setPower(-.7);
                        sleep(500);
                    }
                    else{
                        intakeArm.setPower(0);        
                    }
                }
                
                // Old auto transfer
                /*if (expectedChange > 0 && extendMotor.getCurrentPosition() >= -1030) {
                    telemetry.addData("position 1", extendMotor.getCurrentPosition());
                    telemetry.update();
                    extendMotor.setPower(0);
                    intakeArm.setPower(.8);
                    slideDone = true;
                }
                else if (expectedChange < 0 && extendMotor.getCurrentPosition() <= -1030) {
                    telemetry.addData("position 2", extendMotor.getCurrentPosition());
                    telemetry.update();
                    extendMotor.setPower(0);
                    slideDone = true;
                    intakeArm.setPower(.8);
                } else {
                    telemetry.addData("got here","how about that");
                    telemetry.addData("expectedChange", expectedChange);
                    telemetry.addData("Current position", extendMotor.getCurrentPosition());
                    telemetry.update();
                }
                if (armDone && slideDone) {
                    transfer = false;
                    armDone = false;
                    slideDone = false;
                }*/
            }
            else if(gamepad2.left_stick_y == 0 && !joystickzero) {
                extendMotor.setPower(0);
                joystickzero = true;
            }
            
            // Intake Arm(out)
            if (gamepad2.b) {
                intakeArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
                intakeArm.setPower(-1);
                intakeArmZero = false;
            }
            
            // Intake Arm(in)
            else if (gamepad2.a) {
                intakeArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
                intakeArm.setPower(.75);
                intakeArmZero = false;
            }
            else if (!intakeArmZero && !gamepad2.a && !gamepad2.b){
                intakeArmZero = true;
                intakeArm.setPower(0);
            }
            
            // Outtake
            if (gamepad2.right_trigger > 0) {
                intake.setPower(.8);
            }
            
            // Intake
            else if (gamepad2.left_trigger > 0) {
                intake.setPower(-.9);
            }
            else {
                intake.setPower(0);
            }
        }

    }
    public void flipIn() {
        double offset = intakeArm.getCurrentPosition();
        while (opModeIsActive() && intakeArm.getCurrentPosition()-offset < 80 && !gamepad2.b) {
            //intakeArm.setPower(-.7);
            telemetry.addData("Condition", intakeArm.getCurrentPosition()-offset);
            telemetry.addData("InEncoders", intakeArm.getCurrentPosition());
            telemetry.addData("offset", offset);
            telemetry.update();
        }
        intakeArm.setPower(0);
    }
    public void flipOut() {
        double offset = intakeArm.getCurrentPosition();
        while (opModeIsActive() && intakeArm.getCurrentPosition()-offset > -50 && !gamepad2.b) {
            //intakeArm.setPower(.7);
            telemetry.addData("Condition", intakeArm.getCurrentPosition()-offset);
            telemetry.addData("OutEncoders", intakeArm.getCurrentPosition());
            telemetry.addData("offset", offset);
            telemetry.update();
        }
        intakeArm.setPower(0);
    }
}
