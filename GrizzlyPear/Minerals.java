package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Minerals {

    private final Telemetry telemetry;
    private final Gamepad gamepad1;
    private final Gamepad gamepad2;
    private final WrappedDCMotor dunkMotor;

    public Minerals(Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2, WrappedDCMotor dunkMotor) {
        this.telemetry = telemetry;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.dunkMotor = dunkMotor;
    }

    public void dunk() {
        while (dunkMotor.getEncoderCount() < 150 && !gamepad2.x) {
            telemetry.addData("Status", "Dunking");
            telemetry.addData("Angle", dunkMotor.getEncoderCount());
            telemetry.update();
            dunkMotor.setRawPower(0.7);
        }
        dunkMotor.setRawPower(-0.03);
        while (dunkMotor.getEncoderCount() > 90) { }
        while (dunkMotor.getEncoderCount() > 40 && !gamepad2.x) {
            telemetry.addData("Status", "Returning");
            telemetry.addData("Angle", dunkMotor.getEncoderCount());
            telemetry.update();
            dunkMotor.setRawPower(0.15);
        }
        dunkMotor.setRawPower(0);
    }
}
