package libs;

import java.util.Random;

public class TemperatureSensor {
    private double temperature;

    public TemperatureSensor() {
    }

    public double senseTemperature() {
        Random r = new Random();
        int value = r.nextInt(400);
        this.temperature = (double)value;
        return this.temperature;
    }

    public String getTempReport() {
        if (this.temperature > 300.0D) {
            return "DANGER";
        } else {
            return this.temperature > 235.0D ? "CRITICAL" : "OK";
        }
    }

    public String getSensorType() {
        return "Temperature Sensor";
    }
}

