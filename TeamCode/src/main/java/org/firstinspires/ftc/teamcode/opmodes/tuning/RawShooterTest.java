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
import org.firstinspires.ftc.teamcode.parameters.Hardware;
import org.firstinspires.ftc.teamcode.systems.Shooter;

@TeleOp
public class RawShooterTest extends OpMode {

    private Shooter shooter;
    private TelemetryManager telemetryManager;
    private Telemetry multiTelemetry;

    @Config
    @Configurable
    public static class ShooterTest {
        public static double targetRPM = 0;
    }

    public void init() {
        this.shooter = new Shooter();
        this.shooter.loadHardware(hardwareMap);
        this.shooter.init();
        this.telemetryManager = PanelsTelemetry.INSTANCE.getTelemetry();
        this.multiTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    public void loop() {
        this.shooter.setState(Shooter.State.Target);
        this.shooter.setTargetRPM(ShooterTest.targetRPM);
        this.shooter.update(telemetryManager);
        this.telemetryManager.update(multiTelemetry);
    }
}
