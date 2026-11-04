package edu.spu.se411.lab07_polymorphism.model;

import java.time.LocalDate;

import edu.spu.se411.lab07_polymorphism.config.Config;
import edu.spu.se411.lab07_polymorphism.exceptions.InvalidArgumentException;
import edu.spu.se411.lab07_polymorphism.exceptions.MissingInformationException;

public class TrainBooking extends Booking {

	private SeatClass seatClass;
	private Integer distance; // null = not provided yet

	public TrainBooking(String bookingId, String customerName, LocalDate travelDate,
			String destinationCity, SeatClass seatClass) {
		super(bookingId, customerName, travelDate, destinationCity);
		this.seatClass = seatClass;
	}

	public void setDistance(int distance) throws InvalidArgumentException {
		checkRange(distance, Config.MIN_TRAIN_DISTANCE, Config.MAX_TRAIN_DISTANCE, "Distance");
		this.distance = distance;
	}

	@Override
	public double computePrice() throws MissingInformationException {
		checkProvided(distance, "Distance");
		double rate = (seatClass == SeatClass.FIRST_CLASS)
				? Config.TRAIN_FIRST_CLASS_RATE
				: Config.TRAIN_STANDARD_RATE;
		return distance * rate;
	}
}