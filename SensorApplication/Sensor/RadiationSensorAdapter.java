package Sensor;

import libs.RadiationSensor;

/*
This is an Adapter class for radiation sensor and it implements sensor
The purpose of this class is to enable us to refer to Radiation sensor and use default sensor methods
 */
public class RadiationSensorAdapter implements Sensor {

    private RadiationSensor rs;

    public RadiationSensorAdapter(){
        rs= new RadiationSensor(); //initialize class variable
    }

    /*
    This method returns the reading on the sensor
     */
    @Override
    public double getEnvironmentReading() {
        return rs.getRadiationValue();
    }

    /*
    This method returns sensor name
     */
    @Override
    public String getSensorName() {
        return rs.getName();
    }

    /*
    This method return the status of the environment based on the reading
     */
    @Override
    public String getStatus() {
        return rs.getStatusInfo();
    }
}
