package edu.spu.se411.lab07_polymorphism.model;

import java.time.LocalDate;

import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;

public abstract class Booking {

	private String bookingId;
	private String customerName;
	private LocalDate travelDate;
	private String destinationCity;

	public Booking(String bookingId, String customerName, LocalDate travelDate, String destinationCity) {
		this.bookingId = bookingId;
		this.customerName = customerName;
		this.travelDate = travelDate;
		this.destinationCity = destinationCity;
	}

	// Each booking type computes its price differently (polymorphism)
	public abstract double computePrice() throws MissingInformationException;

	// Shared helpers reused by all subclasses
	protected void checkProvided(Object value, String fieldName) throws MissingInformationException {
		if (value == null) {
			throw new MissingInformationException(fieldName + " is not provided for booking " + bookingId);
		}
	}

	protected void checkRange(double value, double min, double max, String fieldName)
			throws InvalidArgumentException {
		if (value < min || value > max) {
			throw new InvalidArgumentException(
					fieldName + " must be between " + min + " and " + max + ", but was " + value);
		}
	}

	public String getBookingId() {
		return bookingId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public LocalDate getTravelDate() {
		return travelDate;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + bookingId + ", customer=" + customerName
				+ ", date=" + travelDate + ", destination=" + destinationCity + "]";
	}
}