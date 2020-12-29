package virtual_robot.controller.robots.classes;

import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorExImpl;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImpl;
import com.qualcomm.robotcore.hardware.configuration.MotorType;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import virtual_robot.controller.BotConfig;
import virtual_robot.controller.VirtualBot;
import virtual_robot.controller.VirtualRobotController;
import virtual_robot.util.AngleUtils;

/**
 * For internal use only. Represents a robot with four mechanum wheels, color sensor, four distance sensors,
 * a BNO055IMU, and a Servo-controlled arm on the back.
 *
 * MechanumBot is the controller class for the "mechanum_bot.fxml" markup file.
 *
 */
@BotConfig(name = "BBTC Ultimate Goal Bot", filename = "BBTCUltimateGoal", disabled = false)
public class BBTCUltimateGoalBot extends VirtualBot {
    // botOutline is instantiated by fxml loader
    @FXML Rectangle botOutline;

    MotorType motorType;
    private DcMotorExImpl[] motors = null;
    private ServoImpl servo = null;

    //Kept for reference... // backServoArm is instantiated during loading via a fx:id property.
    //Kept for reference... @FXML Rectangle backServoArm;

    private double wheelCircumference;
    private double interWheelWidth;
    private double interWheelLength;
    private double wlAverage;

    private double[][] tWR; //Transform from wheel motion to robot motion

    public BBTCUltimateGoalBot() {
        super();

        hardwareMap.setActive(true);

        motors = new DcMotorExImpl[]{
                (DcMotorExImpl)hardwareMap.get(DcMotorEx.class, "backLeft"),
                (DcMotorExImpl)hardwareMap.get(DcMotorEx.class, "frontLeft"),
                (DcMotorExImpl)hardwareMap.get(DcMotorEx.class, "frontRight"),
                (DcMotorExImpl)hardwareMap.get(DcMotorEx.class, "backRight")
        };
        servo = (ServoImpl)hardwareMap.servo.get("grabber");

        // Default wheels are 4" diameter.
        // c = pi*d
        // d = botWidth * (4" / 18")
        // botWidth = pixels (for the 18" bot)
        //wheelCircumference = Math.PI * botWidth * 4.0 / 18.0;

        // But, our wheel is 10cm diameter, not quite 4"
        wheelCircumference = Math.PI * botWidth * 10.0 / 2.54 / 18.0;

        interWheelWidth = botWidth * 8.0 / 9.0;
        interWheelLength = botWidth * 7.0 / 9.0;
        wlAverage = (interWheelLength + interWheelWidth) / 2.0;

        tWR = new double[][] {
                {-0.25, 0.25, -0.25, 0.25},
                {0.25, 0.25, 0.25, 0.25},
                {-0.25/ wlAverage, -0.25/ wlAverage, 0.25/ wlAverage, 0.25/ wlAverage},
                {-0.25, 0.25, 0.25, -0.25}
        };

        hardwareMap.setActive(false);
    }

    public void initialize() {
        //Kept for reference... backServoArm.getTransforms().add(new Rotate(0, 37.5, 67.5));

        // Set the dash array for the bot outline
        // (I couldn't figure out how to do it in fxml...)
        if (botOutline != null) {
            botOutline.getStrokeDashArray().addAll(5d, 5d);
        }
    }

    protected void createHardwareMap() {
        // Create the hardware map
        hardwareMap = new HardwareMap();

        // Add the locomotive motors
        motorType = MotorType.NeverestOrbital20;
        String[] motorNames = new String[] {"backLeft", "frontLeft", "frontRight", "backRight"};
        for (String name: motorNames) hardwareMap.put(name, new DcMotorExImpl(motorType));

        // Servo for grabber
        hardwareMap.put("grabber", new ServoImpl());

        // Another motor for lifter
        hardwareMap.put("lifter", new DcMotorExImpl(MotorType.Neverest60)); // Is this the right type?
    }

    public synchronized void updateStateAndSensors(double millis) {
        double[] deltaPos = new double[4];
        double[] w = new double[4];

        for (int i = 0; i < 4; i++) {
            deltaPos[i] = motors[i].update(millis);
            w[i] = deltaPos[i] * wheelCircumference / motorType.TICKS_PER_ROTATION;
            //if (i < 2) w[i] = -w[i];
        }

        double[] robotDeltaPos = new double[] {0,0,0,0};
        for (int i=0; i<4; i++){
            for (int j = 0; j<4; j++){
                robotDeltaPos[i] += tWR[i][j] * w[j];
            }
        }

        double dxR = robotDeltaPos[0];
        double dyR = robotDeltaPos[1];
        double headingChange = robotDeltaPos[2];
        double avgHeading = headingRadians + headingChange / 2.0;

        double sin = Math.sin(avgHeading);
        double cos = Math.cos(avgHeading);

        x += dxR * cos - dyR * sin;
        y += dxR * sin + dyR * cos;
        headingRadians += headingChange;

        if (headingRadians > Math.PI) headingRadians -= 2.0 * Math.PI;
        else if (headingRadians < -Math.PI) headingRadians += 2.0 * Math.PI;

        constrainToBoundaries();

        // TODO: Do something with lifter

        // TODO: Do something with grabber
    }

    public synchronized void updateDisplay() {
        super.updateDisplay();
        //Kept for reference... ((Rotate)backServoArm.getTransforms().get(0)).setAngle(-180.0 * servo.getInternalPosition());
    }

    public void powerDownAndReset() {
        for (int i=0; i<4; i++) motors[i].stopAndReset();
    }
}
