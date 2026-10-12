package edu.psu.se411.lab08.observers;

import edu.psu.se411.lab08.framework.Observer;
import edu.psu.se411.lab08.framework.Subject;
import edu.psu.se411.lab08.sensors.Sensor;

public abstract class SensorObserver implements Observer {

	private String name;

	public SensorObserver(String name) {
		this.name = name;
	}

	// Shared logic: check the subject type once, for every sensor observer
	@Override
	public void update(Subject subject) {
		if (subject instanceof Sensor) {
			onReadingChanged((Sensor) subject);
		}
	}

	// Each observer reacts differently (polymorphism)
	protected abstract void onReadingChanged(Sensor sensor);

	public String getName() {
		return name;
	}
}