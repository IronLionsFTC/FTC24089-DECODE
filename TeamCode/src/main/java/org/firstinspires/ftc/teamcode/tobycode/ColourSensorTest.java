package org.firstinspires.ftc.teamcode.tobycode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class ColourSensorTest extends LinearOpMode {
    private ColorSensor clr;
    public Servo indicatorLight = null;
    private boolean isPurple = false;

    @Override
    public void runOpMode() {
        clr = hardwareMap.get(ColorSensor.class, "colourSensor");
        indicatorLight = hardwareMap.get(Servo.class, "indicatorLight");
        waitForStart();

        while (opModeIsActive()) {
            float colourBlue = clr.blue();
            float colourGreen = clr.green();
            float colourRed = clr.red();

            if(colourRed>=400)
                if(colourGreen<=700)
                    if(colourBlue>=600)
                        isPurple = true;

            if(isPurple)
                indicatorLight.setPosition(0.7);
            else
                indicatorLight.setPosition(0.5);
                isPurple    = false;



            telemetry.addData("blue", colourBlue);
            telemetry.addData("green", colourGreen);
            telemetry.addData("red", colourRed);





            telemetry.update();


        }
    }
}
