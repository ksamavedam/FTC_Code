package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;



@TeleOp
public class TeleOpDP extends LinearOpMode {



  private DcMotor intakeArm;



  /**

   * This function is executed when this Op Mode is selected from the Driver Station.

   */

  @Override

  public void runOpMode() {

    intakeArm = hardwareMap.dcMotor.get("intakeArm");



    // Put initialization blocks here.

    waitForStart();

    if (opModeIsActive()) {

      // Put run blocks here.
      intakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      intakeArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      
      
      intakeArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      

      while (opModeIsActive()) {


        //intakeArm.setPower(-gamepad1.right_stick_y);

      /*
        if (gamepad1.x && intakeArm.getCurrentPosition()<370) {
          intakeArm.setPower(.7);
          telemetry.addData("Power: ",1);
        }
        else {
          intakeArm.setPower(0);
        }
      */
      
      if (gamepad1.y && intakeArm.getCurrentPosition()<-10) {
          intakeArm.setPower(-1);
          telemetry.addData("Power: ",1);
        }
        else {
          intakeArm.setPower(0);
        }  

        telemetry.addData("Arm Position: ", intakeArm.getCurrentPosition());
        //telemetry.addData("Power: ", -this.gamepad1.right_stick_y);

        telemetry.update();

      }

    }

  }

}
