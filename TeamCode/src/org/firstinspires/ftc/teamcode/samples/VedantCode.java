package org.firstinspires.ftc.teamcode.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "VedantCode", group = "MechanumBot")
public class VedantCode extends LinearOpMode {



    @Override
    public void runOpMode() {
        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        telemetry.addData("Status", "Initalized");
        telemetry.update();
        waitForStart();
        setPower(0.25);
        sleep(5000);
        setReversePower(0.25);
        sleep(5000);
        setPower(0.0);
        setReversePower(0.0);



    }

     public void setPower(double power) {
        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        FrontRightMotor.setPower(power);
        BackRightMotor.setPower(power);
        FrontLeftMotor.setPower(-power);
        BackLeftMotor.setPower(-power);
    }


    public void setReversePower(double reversePower) {
        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        FrontRightMotor.setPower(-reversePower);
        BackRightMotor.setPower(-reversePower);
        FrontLeftMotor.setPower(reversePower);
        BackLeftMotor.setPower(reversePower);
    }
    }







