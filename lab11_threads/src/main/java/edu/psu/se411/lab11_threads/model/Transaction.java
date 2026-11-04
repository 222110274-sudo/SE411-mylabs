package edu.psu.se411.lab11_threads.model;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab11_threads.exceptions.InsufficientBalanceException;

public class Transaction {

	private static final Logger logger = LoggerFactory.getLogger(Transaction.class);
	private static final AtomicInteger counter = new AtomicInteger(0);

	private String id;
	private CryptoAccount sender;
	private CryptoAccount receiver;
	private Double amount;
	
	public Transaction(String id, CryptoAccount sender, CryptoAccount receiver, Double amount) {
		super();
		if(id == null || sender == null || receiver == null || amount < 0) throw new IllegalArgumentException();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
	}

	// Constructor used in the lab: new Transaction(randomA, randomB, randomAmount)
	public Transaction(CryptoAccount sender, CryptoAccount receiver, Double amount) {
		this("tx_" + counter.incrementAndGet(), sender, receiver, amount);
	}
	
	public void execute() {
		transfer(this);
	}

	// PART 1: no "synchronized" -> double spending problem
	// PART 2: add "synchronized" -> only one transfer runs at a time, balances stay correct
	private static synchronized void transfer(Transaction t) {
		try {
			t.sender.retrieveFromBalance(t.amount);
			t.receiver.addToBalance(t.amount);
		} catch (InsufficientBalanceException e) {
			logger.warn("Transaction {} rejected: {}", t.id, e.getMessage());
		}
	}
	
	public CryptoAccount getSender() {
		return sender;
	}
	public void setSender(CryptoAccount sender) {
		this.sender = sender;
	}
	public CryptoAccount getReceiver() {
		return receiver;
	}
	public void setReceiver(CryptoAccount receiver) {
		this.receiver = receiver;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getId() {
		return id;
	}
}