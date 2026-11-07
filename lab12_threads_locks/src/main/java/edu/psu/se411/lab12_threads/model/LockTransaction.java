package edu.psu.se411.lab12_threads.model;

import edu.psu.se411.lab12_threads.exceptions.InsufficientBalanceException;

/**
 * Part b: transaction using explicit locks instead of "synchronized".
 *
 * The whole transfer (check + withdraw + deposit) must be atomic, so the
 * transaction locks the sender, then the receiver.
 *
 * Part c - what could go wrong: DEADLOCK. Thread 1 runs A -> B and locks A,
 * while thread 2 runs B -> A and locks B. Each now waits forever for the lock
 * the other one holds (circular wait), and the program never terminates.
 */
public class LockTransaction implements Runnable {
	private final String id;
	private final LockCryptoAccount sender;
	private final LockCryptoAccount receiver;
	private final double amount;

	public LockTransaction(String id, LockCryptoAccount sender, LockCryptoAccount receiver, double amount) {
		if (id == null || sender == null || receiver == null || amount < 0) throw new IllegalArgumentException();
		if (sender == receiver) throw new IllegalArgumentException("Cannot make a transaction where the sender and the receiver are the same account");
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
	}

	@Override
	public void run() {
		try {
			execute();
		} catch (InsufficientBalanceException e) {
			// transaction rejected, money stays where it is
		}
	}

	public void execute() throws InsufficientBalanceException {
		sender.getLock().lock();          // 1st lock: sender
		try {
			receiver.getLock().lock();    // 2nd lock: receiver  <-- may deadlock
			try {
				transfer();
			} finally {
				receiver.getLock().unlock();
			}
		} finally {
			sender.getLock().unlock();
		}
	}

	protected void transfer() throws InsufficientBalanceException {
		if (sender.getBalance() < amount) throw new InsufficientBalanceException(sender.getId());
		sender.retrieveFromBalance(amount);
		receiver.addToBalance(amount);
	}

	public LockCryptoAccount getSender() {
		return sender;
	}

	public LockCryptoAccount getReceiver() {
		return receiver;
	}

	public double getAmount() {
		return amount;
	}

	public String getId() {
		return id;
	}
}
