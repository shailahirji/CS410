package Sensor;/*
This is an Adapter class for Pressure sensor and it implements sensor
The purpose of this class is to enable us to refer to Pressure sensor and use default sensor methods
 */

import libs.PressureSensor;

public class PressureSensorAdapter implements Sensor{


    private PressureSensor ps;

    public PressureSensorAdapter(){
        ps= new PressureSensor();//initialize class variable
    }

    /*
  This method returns the reading on the sensor
   */
    @Override
    public double getEnvironmentReading() {
        return ps.readValue();
    }

    /*
   This method returns sensor name
    */
    @Override
    public String getSensorName() {
        return ps.getSensorName();
    }

    /*
        This method return the status of the environment based on the reading
         */
    @Override
    public String getStatus() {
        return ps.getReport();
    }
}
