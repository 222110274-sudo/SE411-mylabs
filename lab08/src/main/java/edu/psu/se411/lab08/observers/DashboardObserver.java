package edu.psu.se411.lab08.observers;

import edu.psu.se411.lab08.sensors.Sensor;

public class DashboardObserver extends SensorObserver {

	public DashboardObserver(String name) {
		super(name);
	}

	@Override
	protected void onReadingChanged(Sensor sensor) {
		System.out.printf("[%s] %s = %.2f %s%n",
				getName(), sensor.getName(), sensor.getReading(), sensor.getUnit());
	}
}