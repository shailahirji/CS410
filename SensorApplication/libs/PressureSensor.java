package libs;

import java.util.Random;

public class PressureSensor {
    private double pressure;

    public PressureSensor() {
    }

    public double readValue() {
        Random r = new Random();
        int value = r.nextInt(10);
        this.pressure = (double)value;
        return this.pressure;
    }

    public String getReport() {
        if (this.pressure > 6.58D) {
            return "DANGER";
        } else {
            return this.pressure > 5.0D ? "CRITICAL" : "OK";
        }
    }

    public String getSensorName() {
        return "Pressure Sensor";
    }
}

