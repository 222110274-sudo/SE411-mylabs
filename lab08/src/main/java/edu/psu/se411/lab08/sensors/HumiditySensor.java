package edu.psu.se411.lab08.sensors;

public class HumiditySensor extends Sensor {

	public HumiditySensor(String name) {
		super(name);
	}

	@Override
	public String getUnit() {
		return "%";
	}

	@Override
	public HumiditySensor clone() {
		return (HumiditySensor) super.clone();
	}
}