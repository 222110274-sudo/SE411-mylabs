package edu.psu.se411.lab12_threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab12_threads.util.SimulationConfig;

/**
 * Part a - program 2: CACHED thread pool + synchronized accounts.
 * Same workload as App, only the thread pool changes.
 *
 * Run: mvn clean package exec:java -Dexec.mainClass=edu.psu.se411.lab12_threads.AppCachedThreadPool -Dexec.args="10 1000000"
 */
public class AppCachedThreadPool {

	static Logger logger = LoggerFactory.getLogger(AppCachedThreadPool.class);

	public static void main(String[] args) {
		logger.info("Application is starting...");
		SimulationConfig cfg = SimulationConfig.fromArgs(args);
		ExecutorService executor = Executors.newCachedThreadPool();
		App.run("CACHED thread pool", cfg, executor);
		logger.info("Application is closing...");
	}
}
