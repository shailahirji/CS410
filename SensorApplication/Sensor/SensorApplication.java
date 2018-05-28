package Sensor;/*
Author:Shaila Hirji
Instructor:Dr Sara Farag
Assignment 2, CS 410 Software Engineering
Bellevue College, Spring 2018

 */
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class SensorApplication extends JFrame {
	
	public SensorApplication() {
		setTitle("Sensor Tracker");
		setLayout(new GridLayout(3,1));

		Sensor ts= new TemperatureSensorAdapter();
		Sensor ps= new PressureSensorAdapter();
		Sensor rs= new RadiationSensorAdapter();

		JPanel temperaturePnl= new RenderSensor(ts);
		temperaturePnl.setBorder(new TitledBorder("Temperature"));

		JPanel pressurePnl= new RenderSensor(ps);
		pressurePnl.setBorder(new TitledBorder("Pressure"));

		JPanel radiationPnl= new RenderSensor(rs);
		radiationPnl.setBorder(new TitledBorder("Radiation"));

		add(temperaturePnl);
		add(pressurePnl);
		add(radiationPnl);

		setPreferredSize(new Dimension(600,600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}


	public static void main(String[] args) {
		SensorApplication app = new SensorApplication();

	}

}
