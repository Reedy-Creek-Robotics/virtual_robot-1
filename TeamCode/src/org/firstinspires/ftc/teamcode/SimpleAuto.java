package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@Autonomous(name = "SimpleAuto", group = "TwoWheelBot")
public class SimpleAuto extends LinearOpMode {

    @Override
    public void runOpMode() {
        DcMotor leftMotor = hardwareMap.dcMotor.get("left_motor");

        telemetry.addData("Status","Initialized");
        telemetry.update();

        waitForStart();

        leftMotor.setPower(0.25f);
        while (opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status","Running");
            telemetry.update();
        }

        leftMotor.setPower(0);
    }
}
