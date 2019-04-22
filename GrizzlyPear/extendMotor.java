package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@TeleOp(name = "extendMotor (Blocks to Java)", group = "")
public class extendMotor extends LinearOpMode {

  private DcMotor extendMotor;
  private DcMotor intakeArm;
  public ElapsedTime runtime = new ElapsedTime();

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    

    extendMotor = hardwareMap.dcMotor.get("extendMotor");
    intakeArm = hardwareMap.dcMotor.get("intakeArm");

    // Put initialization blocks here.
    extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    intakeArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      
      while (opModeIsActive()) {
        // Put loop blocks here.
        if (gamepad1.a) {
          extendMotor.setTargetPosition(-487);
          extendMotor.setPower(.5);
        }
        if (gamepad1.b) {
          extendMotor.setTargetPosition(-2768);
          extendMotor.setPower(1);
        }
        if (gamepad1.x) {
          extendMotor.setTargetPosition(0);
          extendMotor.setPower(1);
          telemetry.update();
        }
      }
      telemetry.addData("Extend THANG", extendMotor.getCurrentPosition());
      telemetry.update();
    }
  }
}
