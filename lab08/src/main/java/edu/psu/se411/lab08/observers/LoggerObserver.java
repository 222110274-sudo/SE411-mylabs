package edu.psu.se411.lab08.observers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab08.sensors.Sensor;

public class LoggerObserver extends SensorObserver {

	private static final Logger logger = LoggerFactory.getLogger(LoggerObserver.class);

	public LoggerObserver(String name) {
		super(name);
	}

	@Override
	protected void onReadingChanged(Sensor sensor) {
		System.out.printf("[%s] logged %s = %.2f %s%n",
				getName(), sensor.getName(), sensor.getReading(), sensor.getUnit());
		logger.info("{} reading changed to {} {}",
				sensor.getName(), String.format("%.2f", sensor.getReading()), sensor.getUnit());
	}
}