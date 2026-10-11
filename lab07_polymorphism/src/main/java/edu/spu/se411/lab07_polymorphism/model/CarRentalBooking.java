package edu.spu.se411.lab07_polymorphism.model;

import java.time.LocalDate;

import edu.spu.se411.lab07_polymorphism.config.Config;
import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;

public class CarRentalBooking extends Booking {

	private double dailyRate;
	private Integer numberOfDays; // null = not provided yet

	public CarRentalBooking(String bookingId, String customerName, LocalDate travelDate,
			String destinationCity, double dailyRate) {
		super(bookingId, customerName, travelDate, destinationCity);
		this.dailyRate = dailyRate;
	}

	public void setNumberOfDays(int numberOfDays) throws InvalidArgumentException {
		checkRange(numberOfDays, Config.MIN_RENTAL_DAYS, Config.MAX_RENTAL_DAYS, "Number of days");
		this.numberOfDays = numberOfDays;
	}

	@Override
	public double computePrice() throws MissingInformationException {
		checkProvided(numberOfDays, "Number of days");
		return dailyRate * numberOfDays;
	}
}