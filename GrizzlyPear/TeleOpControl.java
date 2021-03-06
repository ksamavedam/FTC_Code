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

@TeleOp(name = "TeleOpControl", group = "TeleOpModes")
public class TeleOpControl extends LinearOpMode {

    private GrizzlyPear robot;
    private MechanoMotion motion;
    private Minerals minerals;
    DcMotor extendMotor = null;
    DcMotor latchMotor = null;
    DcMotor intakeArm = null;
    DcMotor dumpMotor = null;
    CRServo intake = null;
    
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
        
        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        waitForStart();


        while (opModeIsActive()) {
            boolean flag = true;
            boolean dumping = false;
            telemetry.addData("Status", "Initialized");
            telemetry.addData("Slide Encoders", extendMotor.getCurrentPosition());
            telemetry.update();

            //MAANAV'S CONTROLS (GAMEPAD1)
            
            //Moving (fast)
            Motion movement = motion.rightwardsMotion(gamepad1.right_stick_x)
                    .add(motion.forwardMotion(-gamepad1.right_stick_y*.7))
                    .add(motion.rotationMotion(gamepad1.left_stick_x*.7));
                    
            //Moving (slow)
            if (gamepad1.dpad_left) {
                movement = movement.add(motion.forwardMotion(-0.3));
            }
            if (gamepad1.dpad_right) {
                movement = movement.add(motion.forwardMotion(0.3));
            }
            if (gamepad1.dpad_up) {
                movement = movement.add(motion.rightwardsMotion(-0.7));
            }
            if (gamepad1.dpad_down) {
                movement = movement.add(motion.rightwardsMotion(0.7));
            }
            
            motion.executeRate(movement);
            
            //Scoring Basket
            //Dumping with encoders
            /*if (gamepad1.b && dumping==false) {
                dumping = true;
                dumpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                dumpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                dumpMotor.setTargetPosition(900);
                dumpMotor.setPower(.5);
                while (opModeIsActive() && dumpMotor.isBusy() && !gamepad1.x) {
                    telemetry.addData("Dump Encoders", dumpMotor.getCurrentPosition());
                    telemetry.update();
                }
                sleep(1000);
                dumpMotor.setTargetPosition(0);
                dumpMotor.setPower(-.3);
                while (opModeIsActive() && dumpMotor.isBusy() && !gamepad1.x) {
                    telemetry.addData("Dump Encoders", dumpMotor.getCurrentPosition());
                    telemetry.update();
                }
                dumpMotor.setPower(0);
                dumping = false;
            }*/
            //Dumping without encoders
            //Down
            if(gamepad1.b){
                dumpMotor.setPower(.4);
            }    
            //Up
            else if(gamepad1.x){
                dumpMotor.setPower(-.4);
            }
            else {
                dumpMotor.setPower(0);
            }
            
            //HARBIN'S CONTROLS (GAMEPAD2)
            
            //Latching
            latchMotor.setPower(gamepad2.right_stick_y);
            
            //Linear Slides (Fast)
            if (Math.abs(gamepad2.left_stick_y) > 0) {
                extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
                extendMotor.setPower(-gamepad2.left_stick_y);
            }
            
            //Linear Slides (Slow)
            //Out
            else if (gamepad2.dpad_up) {
                extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
                extendMotor.setPower(.7);
            }
            //In
            else if (gamepad2.dpad_down) {
                extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
                extendMotor.setPower(-.7);
            }
            else {
                extendMotor.setPower(0);
            }
            
            //Slides to Position
            /*if (gamepad2.y) {
                extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extendMotor.setTargetPosition(-1050);
                if (extendMotor.getCurrentPosition() > -1050) {
                    extendMotor.setPower(.5);
                }
                else if (extendMotor.getCurrentPosition() < -1050) {
                    extendMotor.setPower(.5);
                }
                while (opModeIsActive() && extendMotor.isBusy() && !gamepad2.x) {
                    telemetry.addData("Slide Encoders", extendMotor.getCurrentPosition());
                    telemetry.update();
                }
                extendMotor.setPower(0);
            }*/
            
            // Intake Arm(in)
            if (gamepad2.b) {
                /*if (flag==true) {
                    flag=false;
                    flipIn();
                    flag=true;
                }*/
                intakeArm.setPower(-.8);
            }
            
            // Intake Arm(out)
            else if (gamepad2.a) {
                /*if (flag==true) {
                    flag=false;
                    //Bring slides to deposit position
                    double slideOffset = extendMotor.getCurrentPosition();
                    while (opModeIsActive() && extendMotor.getCurrentPosition()-slideOffset < -300) {
                        extendMotor.setPower(.5);
                    }
                    extendMotor.setPower(0);
                    //Flip in intake arm
                    flipOut();
                    flag=true;
                }*/
                intakeArm.setPower(.5);
            }
            else {
                intakeArm.setPower(0);
            }
            
            // Intake
            if (gamepad2.right_trigger > 0) {
                intake.setPower(.9);
            }
            
            // Outtake (these might be switched)
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
