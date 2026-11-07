package edu.psu.se411.lab12_threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab12_threads.util.SimulationConfig;

/**
 * Part d: locks + resource ordering. OrderedLockTransaction always locks the
 * account with the smaller id first, so the deadlock of AppLocks cannot happen.
 *
 * Run: mvn clean package exec:java -Dexec.mainClass=edu.psu.se411.lab12_threads.AppLockOrdering -Dexec.args="10 1000000"
 */
public class AppLockOrdering {

	static Logger logger = LoggerFactory.getLogger(AppLockOrdering.class);

	public static void main(String[] args) {
		logger.info("Application is starting...");
		SimulationConfig cfg = SimulationConfig.fromArgs(args);
		boolean finished = AppLocks.run("LOCKS with resource ordering", cfg, true);
		if (!finished) {
			logger.error("Unexpected deadlock with resource ordering");
		}
		logger.info("Application is closing...");
	}
}
