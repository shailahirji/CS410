package Sensor;/*
This is an Adapter class for Temperature sensor and it implements sensor
The purpose of this class is to enable us to refer to Temperature sensor and use default sensor methods
 */

import libs.TemperatureSensor;

public class TemperatureSensorAdapter implements Sensor{

    private TemperatureSensor ts;

    public TemperatureSensorAdapter(){
     ts= new TemperatureSensor();//initialize class variable
    }

    /*
   This method returns the reading on the sensor
    */
    @Override
    public double getEnvironmentReading() {
        return ts.senseTemperature();
    }

    /*
    This method returns sensor name
     */
    @Override
    public String getSensorName() {
        return ts.getSensorType();
    }

    /*
    This method return the status of the environment based on the reading
     */
    @Override
    public String getStatus() {
        return ts.getTempReport();
    }
}
