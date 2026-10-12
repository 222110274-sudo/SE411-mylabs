package edu.psu.se411.lab08.sensors;

public class TemperatureSensor extends Sensor {

	public TemperatureSensor(String name) {
		super(name);
	}

	@Override
	public String getUnit() {
		return "C";
	}

	@Override
	public TemperatureSensor clone() {
		return (TemperatureSensor) super.clone();
	}
}