package Sensor;/*
This class enables us to project a visual of the readings from each sensor onto JPanel
 */
import javax.swing.*;
import java.awt.*;

public class RenderSensor extends JPanel {

    private Sensor sensor;
    private double reading; //used to store the reading on sensor

    public RenderSensor(Sensor sensor){
        this.sensor=sensor;
        reading=sensor.getEnvironmentReading();
    }

    /*
    This method draws the sensor visual on JPanel as well as mentions the sensor readings and the type of sensor
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(visualColor());
        g.fillRect(10, 60, 100, 40);
        g.setColor(Color.BLACK);
        g.drawString(sensor.getSensorName()+"("+sensor.getStatus()+")--->"+reading,120,80);

    }

    /*
    This method returns a color for the visual based on the sensor's status
     */
    private Color visualColor(){
        if(sensor.getStatus().equals("DANGER")){
            return Color.RED;
        }else if (sensor.getStatus().equals("CRITICAL")){
            return Color.yellow;

        }else{
            return Color.green;
        }


    }


}
