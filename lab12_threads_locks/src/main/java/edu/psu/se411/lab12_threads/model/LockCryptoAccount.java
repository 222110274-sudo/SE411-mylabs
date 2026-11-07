package edu.psu.se411.lab12_threads.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.psu.se411.lab12_threads.exceptions.InsufficientBalanceException;

/**
 * Part b: same account as CryptoAccount, but "synchronized" is replaced by an
 * explicit ReentrantLock. The lock is exposed so a transaction can hold the
 * locks of both accounts while it moves money.
 *
 * Comparable gives a total order on accounts (by id), used in part d.
 */
public class LockCryptoAccount implements Comparable<LockCryptoAccount> {
	private final String id;
	private double balance;
	private final Lock lock = new ReentrantLock();

	public LockCryptoAccount(String id, double balance) {
		if (balance < 0) throw new IllegalArgumentException("Account balance cannot be negative");
		this.id = id;
		this.balance = balance;
	}

	public void addToBalance(double amount) {
		if (amount < 0) throw new IllegalArgumentException("Amount added to balance cannot be negative");
		lock.lock();
		try {
			balance += amount;
		} finally {
			lock.unlock();
		}
	}

	public void retrieveFromBalance(double amount) throws InsufficientBalanceException {
		lock.lock();
		try {
			if (amount > balance) throw new InsufficientBalanceException(id);
			balance -= amount;
		} finally {
			lock.unlock();
		}
	}

	public double getBalance() {
		lock.lock();
		try {
			return balance;
		} finally {
			lock.unlock();
		}
	}

	public Lock getLock() {
		return lock;
	}

	public String getId() {
		return id;
	}

	/** Total order on accounts: ids are unique, so String order is a total order. */
	@Override
	public int compareTo(LockCryptoAccount other) {
		return id.compareTo(other.id);
	}
}
