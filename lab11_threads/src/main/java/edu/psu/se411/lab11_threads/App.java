package edu.psu.se411.lab11_threads;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab11_threads.model.CryptoAccount;
import edu.psu.se411.lab11_threads.model.Transaction;

/**
 * Build and run using Maven goal: clean package exec:java
 */
public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	public static Integer nbAccounts = 10;
	public static Double initialBalance = 1000.0;
	public static Integer nbTransactions = 10000;

	public static void main(String[] args) {
		logger.info("Application is starting...");
		
		// 1- Create accounts
		List<CryptoAccount> accounts = new ArrayList<>();
		for (Integer i = 0; i < nbAccounts; i++) {
			accounts.add(new CryptoAccount("acc_" + i.toString(), initialBalance));
		}
		System.out.printf("Total before: %.2f riyals%n", computeTotal(accounts));

		// 2- Create one thread per transaction
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < nbTransactions; i++) {
			Thread thread = new Thread(() -> {
				CryptoAccount randomA = accounts.get((int) (Math.random() * 10));
				CryptoAccount randomB = accounts.get((int) (Math.random() * 10));
				Double randomAmount = (double) (Math.random() * 10 + 1);
				Transaction t = new Transaction(randomA, randomB, randomAmount);
				t.execute();
			});
			threads.add(thread);
		}

		// 3- Start all threads
		for (Thread thread : threads) {
			thread.start();
		}

		// 4- Wait for all threads to finish
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				logger.error("Main thread interrupted while waiting", e);
				Thread.currentThread().interrupt();
			}
		}

		// 5- Check the total
		double expected = nbAccounts * initialBalance;
		double total = computeTotal(accounts);
		System.out.printf("Total after:  %.2f riyals (expected %.2f)%n", total, expected);

		if (Math.abs(total - expected) > 0.01) {
			System.out.println("INCONSISTENT: money was created or lost (double spending problem)");
			logger.error("Inconsistent total: {} instead of {}", total, expected);
		} else {
			System.out.println("CONSISTENT: total is correct");
			logger.info("Consistent total: {}", total);
		}

		logger.info("Application is closing...");
	}

	// Total amount of money in the system
	public static double computeTotal(List<CryptoAccount> accounts) {
		double total = 0;
		for (CryptoAccount account : accounts) {
			total += account.getBalance();
		}
		return total;
	}

}