package edu.psu.se411.lab12_threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab12_threads.model.LockCryptoAccount;
import edu.psu.se411.lab12_threads.model.LockTransaction;
import edu.psu.se411.lab12_threads.model.OrderedLockTransaction;
import edu.psu.se411.lab12_threads.util.ExecutorUtils;
import edu.psu.se411.lab12_threads.util.SimulationConfig;

/**
 * Parts b and c: "synchronized" replaced by locks (LockTransaction locks the
 * sender then the receiver). This program can DEADLOCK; it detects the
 * deadlock, prints the threads involved and exits.
 *
 * Run: mvn clean package exec:java -Dexec.mainClass=edu.psu.se411.lab12_threads.AppLocks -Dexec.args="10 1000000"
 */
public class AppLocks {

	static Logger logger = LoggerFactory.getLogger(AppLocks.class);

	public static void main(String[] args) {
		logger.info("Application is starting...");
		SimulationConfig cfg = SimulationConfig.fromArgs(args);
		boolean finished = run("LOCKS, no ordering", cfg, false);
		if (!finished) {
			logger.error("Deadlock detected: transactions lock accounts in opposite orders");
			logger.info("Application is closing...");
			System.exit(1); // deadlocked threads can never be stopped, so force the JVM to exit
		}
		logger.info("Application is closing...");
	}

	/**
	 * Shared by AppLocks and AppLockOrdering.
	 * A fixed pool is used: with a cached pool, every task that blocks behind a
	 * deadlocked account would make the pool create yet another thread.
	 */
	static boolean run(String label, SimulationConfig cfg, boolean ordered) {
		LockCryptoAccount[] accounts = new LockCryptoAccount[cfg.nbAccounts()];
		for (int i = 0; i < cfg.nbAccounts(); i++) {
			accounts[i] = new LockCryptoAccount("acc_" + i, SimulationConfig.INITIAL_BALANCE);
		}

		ExecutorService executor = Executors.newFixedThreadPool(cfg.poolSize());
		System.out.printf("%s | %d accounts | %d transactions | %d threads%n",
				label, cfg.nbAccounts(), cfg.nbTransactions(), cfg.poolSize());
		System.out.printf("Initial money volume in system: %f%n", sum(accounts));

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < cfg.nbTransactions(); i++) {
			int[] pair = cfg.randomPair();
			LockCryptoAccount sender = accounts[pair[0]];
			LockCryptoAccount receiver = accounts[pair[1]];
			double amount = Math.random() * 100;
			executor.execute(ordered
					? new OrderedLockTransaction("id_" + i, sender, receiver, amount)
					: new LockTransaction("id_" + i, sender, receiver, amount));
		}

		boolean finished;
		try {
			finished = ExecutorUtils.awaitTerminationOrDeadlock(executor);
		} catch (InterruptedException e) {
			logger.error("Interrupted while waiting for transactions", e);
			Thread.currentThread().interrupt();
			return false;
		}
		long endTime = System.currentTimeMillis();

		if (finished) {
			System.out.printf("Final money volume in system:   %f%n", sum(accounts));
			System.out.println("Total time: " + (endTime - startTime) + " milliseconds");
		} else {
			System.out.println("Program stuck after " + (endTime - startTime) + " milliseconds: the transactions can never finish.");
		}
		return finished;
	}

	static double sum(LockCryptoAccount[] accounts) {
		double total = 0.0;
		for (LockCryptoAccount a : accounts) {
			total += a.getBalance();
		}
		return total;
	}
}
