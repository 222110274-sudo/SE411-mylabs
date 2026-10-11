package edu.spu.se411.lab07_polymorphism.model;

import java.time.LocalDate;

import edu.spu.se411.lab07_polymorphism.config.Config;
import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;

public class FlightBooking extends Booking {

	private double basePrice;
	private Double luggageWeight; // null = not provided yet

	public FlightBooking(String bookingId, String customerName, LocalDate travelDate,
			String destinationCity, double basePrice) {
		super(bookingId, customerName, travelDate, destinationCity);
		this.basePrice = basePrice;
	}

	public void setLuggageWeight(double luggageWeight) throws InvalidArgumentException {
		checkRange(luggageWeight, Config.MIN_LUGGAGE_WEIGHT, Config.MAX_LUGGAGE_WEIGHT, "Luggage weight");
		this.luggageWeight = luggageWeight;
	}

	@Override
	public double computePrice() throws MissingInformationException {
		checkProvided(luggageWeight, "Luggage weight");
		return basePrice + luggageWeight * Config.EXTRA_LUGGAGE_RATE;
	}
}