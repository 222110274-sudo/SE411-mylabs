package edu.spu.se411.lab07_polymorphism;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;
import edu.spu.se411.lab07_polymorphism.model.Booking;
import edu.spu.se411.lab07_polymorphism.model.CarRentalBooking;
import edu.spu.se411.lab07_polymorphism.model.FlightBooking;
import edu.spu.se411.lab07_polymorphism.model.SeatClass;
import edu.spu.se411.lab07_polymorphism.model.TrainBooking;

public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	// Polymorphism: works for any booking type
	public static double computeTotalPrice(Booking booking) throws MissingInformationException {
		return booking.computePrice();
	}

	public static void main(String[] args) {

		logger.info("Application is starting...");

		LocalDate date = LocalDate.of(2026, 11, 15);
		List<Booking> bookings = new ArrayList<>();

		// ---------- Valid bookings ----------
		FlightBooking flight = new FlightBooking("F001", "Sultan Ahmed", date, "Jeddah", 450);
		TrainBooking train = new TrainBooking("T001", "Sara Ali", date, "Dammam", SeatClass.FIRST_CLASS);
		CarRentalBooking car = new CarRentalBooking("C001", "Omar Saleh", date, "Abha", 150);

		try {
			flight.setLuggageWeight(20);
			train.setDistance(400);
			car.setNumberOfDays(5);
		} catch (InvalidArgumentException e) {
			logger.error("Invalid booking data", e);
		}

		bookings.add(flight);
		bookings.add(train);
		bookings.add(car);

		// ---------- Missing information ----------
		bookings.add(new FlightBooking("F002", "Khalid Nasser", date, "Madinah", 380)); // no luggage weight
		bookings.add(new CarRentalBooking("C002", "Lama Fahad", date, "AlUla", 200));   // no days

		// ---------- Invalid values ----------
		FlightBooking heavyFlight = new FlightBooking("F003", "Noura Saad", date, "Tabuk", 500);
		try {
			heavyFlight.setLuggageWeight(55); // above 40 kg
		} catch (InvalidArgumentException e) {
			logger.error("Invalid luggage weight for booking {}", heavyFlight.getBookingId(), e);
			System.out.println("Error: " + e.getMessage());
		}

		TrainBooking longTrain = new TrainBooking("T002", "Faisal Turki", date, "Qassim", SeatClass.STANDARD);
		try {
			longTrain.setDistance(2500); // above 2000 km
		} catch (InvalidArgumentException e) {
			logger.error("Invalid distance for booking {}", longTrain.getBookingId(), e);
			System.out.println("Error: " + e.getMessage());
		}

		// ---------- Compute prices polymorphically ----------
		for (Booking b : bookings) {
			try {
				double total = computeTotalPrice(b);
				System.out.printf("%s -> total price: %.2f%n", b, total);
			} catch (MissingInformationException e) {
				logger.error("Cannot compute price for booking {}", b.getBookingId(), e);
				System.out.println("Error: " + e.getMessage());
			}
		}

		logger.info("Application is stopping...");
	}
}