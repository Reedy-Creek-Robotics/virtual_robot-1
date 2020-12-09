package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "TwoWobbleShoot", group = "RCMMS")
public class TwoWobbleShoot extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {


        telemetry.addData("Status","Initialized");
        telemetry.update();

        waitForStart();


        while (opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status","Running");
            telemetry.update();
            
            moveToRingStack();
            recognizeRingHeight();
            goToAppropriateTargetZone();
            returnToCenter();
            getSecondWobbleGoal();
            returnToStartPosition();
            repeat1345();
            shoot();
            parkOverLine();
            
        }

    }

    private void parkOverLine() {
    }

    private void shoot() {
    }

    private void repeat1345() {
    }

    private void returnToStartPosition() {
    }

    private void getSecondWobbleGoal() {
    }

    private void returnToCenter() {
    }

    private void goToAppropriateTargetZone() {
    }

    private void recognizeRingHeight() {
    }

    private void moveToRingStack() {
    }
}