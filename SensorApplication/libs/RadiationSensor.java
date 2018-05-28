package libs;

import java.util.Random;

public class RadiationSensor {
    private double radiationLevel;

    public RadiationSensor() {
    }

    public double getRadiationValue() {
        Random r = new Random();
        int value = r.nextInt(5);
        this.radiationLevel = (double)value;
        return this.radiationLevel;
    }

    public String getStatusInfo() {
        if (this.radiationLevel > 4.0D) {
            return "DANGER";
        } else {
            return this.radiationLevel > 3.0D ? "CRITICAL" : "OK";
        }
    }

    public String getName() {
        return "Radiation Sensor";
    }
}

