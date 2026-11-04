package edu.spu.se411.lab07_polymorphism.config;

public final class Config {

	// Flight
	public static final double EXTRA_LUGGAGE_RATE = 5.0;      // price per kg
	public static final double MIN_LUGGAGE_WEIGHT = 0;
	public static final double MAX_LUGGAGE_WEIGHT = 40;

	// Train
	public static final double TRAIN_STANDARD_RATE = 0.30;    // price per km
	public static final double TRAIN_FIRST_CLASS_RATE = 0.60; // price per km
	public static final int MIN_TRAIN_DISTANCE = 1;
	public static final int MAX_TRAIN_DISTANCE = 2000;

	// Car rental
	public static final int MIN_RENTAL_DAYS = 1;
	public static final int MAX_RENTAL_DAYS = 30;

	private Config() {
		// constants only, no objects
	}
}