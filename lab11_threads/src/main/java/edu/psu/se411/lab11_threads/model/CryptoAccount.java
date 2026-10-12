package edu.psu.se411.lab11_threads.model;

import edu.psu.se411.lab11_threads.exceptions.InsufficientBalanceException;

public class CryptoAccount {
	private String id;
	private Double balance;
	
	public CryptoAccount(String id, Double balance) {
		this.id = id;
		this.balance = balance;
	}
	
	public void addToBalance(Double amount) {
		Double current = balance;      // 1- read
		Thread.yield();                // gives other threads a chance to run here (makes the race visible)
		balance = current + amount;    // 2- write
	}
	
	public void retrieveFromBalance(Double amount) throws InsufficientBalanceException {
		Double current = balance;      // 1- read
		if (current < amount) {
			throw new InsufficientBalanceException("Account " + id + " has " + current
					+ " but needs " + amount);
		}
		Thread.yield();                // gives other threads a chance to run here (makes the race visible)
		balance = current - amount;    // 2- write
	}
	
	public String getId() {
		return id;
	}
	public Double getBalance() {
		return balance;
	}
	
}