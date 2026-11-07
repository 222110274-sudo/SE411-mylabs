package edu.psu.se411.lab12_threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab12_threads.model.CryptoAccount;
import edu.psu.se411.lab12_threads.model.Transaction;
import edu.psu.se411.lab12_threads.util.ExecutorUtils;
import edu.psu.se411.lab12_threads.util.SimulationConfig;

/**
 * Part a - program 1: FIXED thread pool + synchronized accounts.
 *
 * Build and run using Maven goal: clean package exec:java
 * Arguments: [nbAccounts] [nbTransactions] [poolSize]
 *   e.g. mvn clean package exec:java -Dexec.args="10 1000000"
 *
 * Note: lab 11 used newFixedThreadPool(nbTransactions). With one million
 * transactions that asks the JVM for one million OS threads, which crashes
 * or freezes the machine. The pool is therefore sized to the number of CPU
 * cores by default (override with the 3rd argument).
 */
public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		logger.info("Application is starting...");
		SimulationConfig cfg = SimulationConfig.fromArgs(args);
		ExecutorService executor = Executors.newFixedThreadPool(cfg.poolSize());
		run("FIXED thread pool (" + cfg.poolSize() + " threads)", cfg, executor);
		logger.info("Application is closing...");
	}

	/** Shared by App and AppCachedThreadPool so both run exactly the same workload. */
	static void run(String label, SimulationConfig cfg, ExecutorService executor) {
		// 1- Create accounts
		CryptoAccount[] accounts = new CryptoAccount[cfg.nbAccounts()];
		for (int i = 0; i < cfg.nbAccounts(); i++) {
			accounts[i] = new CryptoAccount("acc_" + i, SimulationConfig.INITIAL_BALANCE);
		}

		System.out.printf("%s | %d accounts | %d transactions%n", label, cfg.nbAccounts(), cfg.nbTransactions());
		System.out.printf("Initial money volume in system: %f%n", sumTotalMoneyInAccounts(accounts));

		long startTime = System.currentTimeMillis();

		// 2- choose sender and receiver accounts randomly, 3- execute transaction
		for (int i = 0; i < cfg.nbTransactions(); i++) {
			int[] pair = cfg.randomPair();
			executor.execute(new Transaction("id_" + i, accounts[pair[0]], accounts[pair[1]], Math.random() * 100));
		}

		// 4- wait for all transactions to complete
		try {
			ExecutorUtils.awaitTermination(executor);
		} catch (InterruptedException e) {
			logger.error("Interrupted while waiting for transactions", e);
			Thread.currentThread().interrupt();
			return;
		}
		long endTime = System.currentTimeMillis();

		// 5- check total money in system after all transactions
		System.out.printf("Final money volume in system:   %f%n", sumTotalMoneyInAccounts(accounts));
		System.out.println("Largest number of threads used: " + ExecutorUtils.largestPoolSize(executor));
		System.out.println("Total time: " + (endTime - startTime) + " milliseconds");
	}

	static double sumTotalMoneyInAccounts(CryptoAccount[] accounts) {
		double total = 0.0;
		for (CryptoAccount c : accounts) {
			total += c.getBalance();
		}
		return total;
	}
}
