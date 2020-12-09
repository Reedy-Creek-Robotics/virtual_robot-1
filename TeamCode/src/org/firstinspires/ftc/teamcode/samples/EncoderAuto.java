package org.firstinspires.ftc.teamcode.samples;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Auto", group = "RCMMS")
public class EncoderAuto extends LinearOpMode {

    DcMotor frontLeft,backLeft,frontRight,backRight;
    ElapsedTime t;
    public static double TICKS_PER_CM = 17.1;// 17.112 tics/cm traveled
    //Ticks per revolution = 537.6
    //wheel size is 100mm and circumference ~31.415 cm

    public void runOpMode() {
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");

        waitForStart();
        t = new ElapsedTime();
        turnRight(90);

    }
    public void moveForward(double distance) {
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        move();
    }
    public void moveBackward(double distance) {
        backLeft.setTargetPosition((int) (-distance  * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (-distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM));
        move();
    }
    public void strafeRight(double distance) {
        backLeft.setTargetPosition((int) (-distance * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        move();
    }
    public void strafeLeft(double distance) {
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM)); //ticks
        frontLeft.setTargetPosition((int) (-distance * TICKS_PER_CM));
        frontRight.setTargetPosition((int) (-distance * TICKS_PER_CM));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM));
        move();
    }
    public void turnRight(double distance) {
        backLeft.setTargetPosition((int) (distance * TICKS_PER_CM * 62/90)); //ticks
        frontLeft.setTargetPosition((int) (distance * TICKS_PER_CM * 62/90));
        frontRight.setTargetPosition((int) (distance * TICKS_PER_CM * 62/90));
        backRight.setTargetPosition((int) (distance * TICKS_PER_CM * 62/90));
        move();
    }
    public void turnLeft(double distance) {
        backLeft.setTargetPosition((int) (-distance * TICKS_PER_CM * 62/90)); //ticks
        frontLeft.setTargetPosition((int) (-distance * TICKS_PER_CM * 62/90));
        frontRight.setTargetPosition((int) (-distance * TICKS_PER_CM * 62/90));
        backRight.setTargetPosition((int) (-distance * TICKS_PER_CM * 62/90));
        move();
    }
    public void move(){
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(1);
        backLeft.setPower(1);
        frontRight.setPower(1);
        backRight.setPower(1);

        while (opModeIsActive())
        {
            telemetry.addData("Time", t.seconds());
            telemetry.addData("encoder-bck-left", backLeft.getCurrentPosition() + "  busy=" + backLeft.isBusy());
            telemetry.addData("encoder-bck-right", backRight.getCurrentPosition() + "  busy=" + backRight.isBusy());
            telemetry.addData("encoder-fwd-left", frontLeft.getCurrentPosition() + "  busy=" +frontLeft.isBusy());
            telemetry.addData("encoder-fwd-right", frontRight.getCurrentPosition() + "  busy=" + frontRight.isBusy());
            telemetry.update();
            if((Math.abs(backRight.getCurrentPosition()-backRight.getTargetPosition())<10) && (Math.abs(backLeft.getCurrentPosition()-backLeft.getTargetPosition())<10)&& (Math.abs(frontLeft.getCurrentPosition()-frontLeft.getTargetPosition())<10) && (Math.abs(frontRight.getCurrentPosition()-frontRight.getTargetPosition())<10)){
                break;
            }
        }

        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        resetStartTime();
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
}
