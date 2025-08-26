package core;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class TobyTeleOp extends LinearOpMode {
    private DcMotor lf;
    private DcMotor rf;
    private  DcMotor lb;
    private DcMotor rb;


    @Override
    public void runOpMode() {
        lf = hardwareMap.dcMotor.get("leftFront");
        rf = hardwareMap.dcMotor.get("rightFront");
        lb = hardwareMap.dcMotor.get("leftRear");
        rb = hardwareMap.dcMotor.get("rightRear");
        DcMotor sm = hardwareMap.dcMotor.get("outtake1");
        DcMotor sm2 = hardwareMap.dcMotor.get("outtake2");

        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        sm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        sm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        double y = 0;
        double x = 0;
        double z = 0;
        double slide = 0.5;
        double slideback = -0.5;
        double stop = 0;



        while (opModeIsActive()) {
            y = gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            z = gamepad1.right_stick_x;


            lf.setPower (-z+x-y);
            rf.setPower(-z+x+y);
            lb.setPower(-z-x-y);
            rb.setPower(-z-x+y);

            if (gamepad1.crossWasPressed()) {
                sm.setPower(slide);
                sm2.setPower(slide);
                getRuntime();


            }
            if (gamepad1.circleWasPressed()) {
                sm.setPower(slideback);
                sm2.setPower(slideback);
            }
            if (gamepad1.triangleWasPressed()) {
                sm.setPower(stop);
                sm2.setPower(stop);
            }




            telemetry.addData("Status", "Running");
            telemetry.update();



        }
    }


}
