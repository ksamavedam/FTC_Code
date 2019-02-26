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
    double dampen=0.4;
    CRServo intakeArm = null;
    Servo arm = null;
    //private Minerals minerals;
    //DcMotor extendMotor = null;
    //DcMotor latchMotor = null;
   // Servo intakeArm = null;
//    Servo intake = null;
    
    @Override
    public void runOpMode() {
        intakeArm = hardwareMap.get(CRServo.class, "intake");
        arm = hardwareMap.get(Servo.class, "arm");
        robot = new GrizzlyPear(hardwareMap);
        motion = new MechanoMotion(robot);
       // minerals = new Minerals(telemetry, gamepad1, gamepad2, robot.getDunkMotor());
        //extendMotor = hardwareMap.get(DcMotor.class, "extendMotor");
       // latchMotor = hardwareMap.get(DcMotor.class, "latchMotor");
       // intakeArm = hardwareMap.get(Servo.class, "intakeArm");
        //intake = hardwareMap.get(Servo.class, "intake");
        waitForStart();
 
        boolean flag = false;
 
        while (opModeIsActive()) {
            telemetry.addData("Status", "Initialized");
            telemetry.update();
 
            Motion movement = motion.rightwardsMotion(dampen*gamepad1.right_stick_x)
                    .add(motion.forwardMotion(dampen*gamepad1.right_stick_y))
                    .add(motion.rotationMotion(dampen*gamepad1.left_stick_x));
            if (gamepad1.a) {
                intakeArm.setPower(0.70);
                telemetry.addData("Status", "Gamepad A");
                telemetry.update();
                
            }
            if (gamepad1.b) {
                intakeArm.setPower(-0.70);
            }
            if (gamepad1.x){
                intakeArm.setPower(0);
            }
            
           if(gamepad1.dpad_up){
               arm.setPosition(0.50);
           }
           if(gamepad1.dpad_down){
               arm.setPosition(-0.90);
           }
          
    

            motion.executeRate(movement);
 
        }
    }
}
