package edu.psu.se411.lab08;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab08.observers.DashboardObserver;
import edu.psu.se411.lab08.observers.LoggerObserver;
import edu.psu.se411.lab08.sensors.HumiditySensor;
import edu.psu.se411.lab08.sensors.TemperatureSensor;

public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		logger.info("Application is starting...");

		// ---------- Subjects ----------
		TemperatureSensor temp = new TemperatureSensor("Temperature");
		HumiditySensor humidity = new HumiditySensor("Humidity");

		// ---------- Observers ----------
		DashboardObserver dashboard = new DashboardObserver("Dashboard");
		LoggerObserver fileLogger = new LoggerObserver("Logger");

		// ---------- Register observers ----------
		temp.register(dashboard);
		temp.register(fileLogger);
		humidity.register(dashboard);
		humidity.register(fileLogger);

		// ---------- Simulate reading changes ----------
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			System.out.println("--- Reading " + (i + 1) + " ---");
			temp.setReading(20 + random.nextDouble() * 15);
			humidity.setReading(40 + random.nextDouble() * 20);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("Simulation interrupted", e);
				Thread.currentThread().interrupt();
			}
		}

		// ---------- Unregister ----------
		System.out.println("\n--- Logger unregistered from humidity ---");
		humidity.unregister(fileLogger);
		humidity.setReading(55);

		// ---------- Clone ----------
		System.out.println("\n--- Cloning the temperature sensor ---");
		TemperatureSensor tempCopy = temp.clone();
		tempCopy.setName("Temperature (copy)");
		System.out.println("Original observers: " + temp.getObserverCount());
		System.out.println("Clone observers:    " + tempCopy.getObserverCount());

		DashboardObserver copyDashboard = new DashboardObserver("Copy Dashboard");
		tempCopy.register(copyDashboard);

		System.out.println("Changing the clone (only Copy Dashboard should react):");
		tempCopy.setReading(18.5);

		System.out.println("Changing the original (only Dashboard and Logger should react):");
		temp.setReading(30.0);

		logger.info("Application is stopping...");
	}
}