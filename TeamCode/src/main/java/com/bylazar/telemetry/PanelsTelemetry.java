package com.bylazar.telemetry;

public class PanelsTelemetry {
    public static final PanelsTelemetry INSTANCE = new PanelsTelemetry();
    private final TelemetryManager telemetryManager = new TelemetryManager();

    public TelemetryManager getTelemetry() {
        return telemetryManager;
    }
}
