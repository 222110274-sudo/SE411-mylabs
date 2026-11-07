package edu.psu.se411.lab12_threads.model;

import edu.psu.se411.lab12_threads.exceptions.InsufficientBalanceException;

/**
 * Part d: resource ordering. Accounts have a total order (by id), and every
 * transaction locks the two accounts in ASCENDING order, whatever the
 * direction of the transfer. A -> B and B -> A both lock A first, so a
 * circular wait is impossible and the deadlock disappears.
 */
public class OrderedLockTransaction extends LockTransaction {

	public OrderedLockTransaction(String id, LockCryptoAccount sender, LockCryptoAccount receiver, double amount) {
		super(id, sender, receiver, amount);
	}

	@Override
	public void execute() throws InsufficientBalanceException {
		LockCryptoAccount first = getSender().compareTo(getReceiver()) < 0 ? getSender() : getReceiver();
		LockCryptoAccount second = (first == getSender()) ? getReceiver() : getSender();

		first.getLock().lock();
		try {
			second.getLock().lock();
			try {
				transfer();
			} finally {
				second.getLock().unlock();
			}
		} finally {
			first.getLock().unlock();
		}
	}
}
