package Sensor;

/*
This is a general interface used by all Adapter class of each sensor type
 */
public interface Sensor  {

     double getEnvironmentReading();
     String getSensorName();
     String getStatus();

}
