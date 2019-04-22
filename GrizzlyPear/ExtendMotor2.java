package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@TeleOp(name = "ExtendMotor2 (Blocks to Java)", group = "")
public class ExtendMotor2 extends LinearOpMode {

  private DcMotor extendMotor;
  private DcMotor intakeArm;
  DcMotor dumpMotor = null;
  public ElapsedTime runtime = new ElapsedTime();

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    

    extendMotor = hardwareMap.dcMotor.get("extendMotor");
    intakeArm = hardwareMap.dcMotor.get("intakeArm");
    dumpMotor = hardwareMap.dcMotor.get("dumpMotor");
    // Put initialization blocks here.
    extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    dumpMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    intakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    runtime.reset();
        waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      
      while (opModeIsActive()) {
        // Put loop blocks here.
        if (gamepad1.y) {
          extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          intakeArm.setTargetPosition(0);
          intakeArm.setPower(.8);
          intakeArm.setPower(0);       
          extendMotor.setTargetPosition(-418);
          extendMotor.setPower(.5);
          
        }
        /*if (Math.abs(gamepad2.left_stick_y) > 0) {
                extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
                extendMotor.setPower(gamepad1.left_stick_y);
        }*/
        else if (gamepad1.b) {
          intakeArm.setTargetPosition(-136);
          intakeArm.setPower(.8);
          
        }
        
        else if (gamepad1.x) {
          extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          extendMotor.setTargetPosition(0);
          extendMotor.setPower(1);
          telemetry.addData("In if",extendMotor.getCurrentPosition());
          telemetry.update();
        }
        else{
          extendMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
          extendMotor.setPower(gamepad1.left_stick_y);
        }
        while(gamepad1.a){
                dumpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                dumpMotor.setTargetPosition(885);
                dumpMotor.setPower(.65);  
                }
            dumpMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            dumpMotor.setTargetPosition(0);
            dumpMotor.setPower(.2);
      }
      telemetry.addData("Extend THANG", extendMotor.getCurrentPosition());
      telemetry.update();
    }
  }
}

