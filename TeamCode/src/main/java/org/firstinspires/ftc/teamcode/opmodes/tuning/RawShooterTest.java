package org.firstinspires.ftc.teamcode.opmodes.tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.parameters.Hardware;
import org.firstinspires.ftc.teamcode.systems.Shooter;

@TeleOp
public class RawShooterTest extends OpMode {

    private Shooter shooter;
    private LionMotor intakeMotor;
    private LionMotor transferMotor;
    private TelemetryManager telemetryManager;
    private Telemetry multiTelemetry;
    private LionServo hood;

    @Config
    @Configurable
    public static class ShooterTest {
        public static double targetRPM = 0;
        public static double feedPower = 0;
        public static double hoodAngle = Hardware.Servos.ZeroPositions.hood;
    }

    public void init() {
        this.shooter = new Shooter();
        this.shooter.loadHardware(hardwareMap);
        this.shooter.init();
        this.telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();
        this.multiTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        this.intakeMotor = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.intakeMotor);
        this.transferMotor = LionMotor.withEncoder(hardwareMap, Hardware.Motors.Names.transferMotor);
        this.intakeMotor.setReversed(Hardware.Motors.Reversed.intakeMotor);
        this.transferMotor.setReversed(Hardware.Motors.Reversed.transferMotor);
        this.intakeMotor.setZPB(Hardware.Motors.ZPB.intakeMotor);
        this.transferMotor.setZPB(Hardware.Motors.ZPB.transferMotor);
        this.hood = LionServo.single(hardwareMap, Hardware.Servos.Names.shooterHood, Hardware.Servos.ZeroPositions.hood);
    }

    public void loop() {
        this.shooter.setState(Shooter.State.Target);
        this.shooter.setTargetRPM(ShooterTest.targetRPM);
        this.shooter.update(telemetryManager);
        this.intakeMotor.setPower(ShooterTest.feedPower);
        this.transferMotor.setPower(ShooterTest.feedPower);
        this.hood.setPosition(ShooterTest.hoodAngle);
        telemetryManager.addData("STATE", this.shooter.getState());
        telemetryManager.addData("TARGET", this.shooter.getTargetRPM());
        this.telemetryManager.update(multiTelemetry);
    }
}
