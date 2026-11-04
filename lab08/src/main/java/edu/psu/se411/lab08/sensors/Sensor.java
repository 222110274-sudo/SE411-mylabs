package edu.psu.se411.lab08.sensors;

import java.util.ArrayList;
import java.util.List;

import edu.psu.se411.lab08.framework.Observer;
import edu.psu.se411.lab08.framework.Subject;

public abstract class Sensor implements Subject, Cloneable {

	private String name;
	private double reading;
	private List<Observer> observers = new ArrayList<>();

	public Sensor(String name) {
		this.name = name;
	}

	// Each sensor type has its own unit (polymorphism)
	public abstract String getUnit();

	@Override
	public void register(Observer o) {
		if (o != null && !observers.contains(o)) {
			observers.add(o);
		}
	}

	@Override
	public void unregister(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update(this);
		}
	}

	public void setReading(double reading) {
		if (this.reading != reading) {
			this.reading = reading;
			notifyObservers(); // only notify when the value really changes
		}
	}

	public double getReading() {
		return reading;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getObserverCount() {
		return observers.size();
	}

	@Override
	public Sensor clone() {
		try {
			Sensor copy = (Sensor) super.clone();
			copy.observers = new ArrayList<>(); // the clone gets its own (empty) observer list
			return copy;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError("Sensor implements Cloneable, so this cannot happen", e);
		}
	}
}