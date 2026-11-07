package edu.psu.se411.lab12_threads.util;

/**
 * Reads the simulation parameters from the program arguments:
 *   args[0] = number of accounts      (default 1000)
 *   args[1] = number of transactions  (default 1000000)
 *   args[2] = fixed pool size         (default = number of CPU cores)
 */
public record SimulationConfig(int nbAccounts, int nbTransactions, int poolSize) {

	public static final double INITIAL_BALANCE = 10000.0;

	public static SimulationConfig fromArgs(String[] args) {
		int accounts = args.length > 0 ? Integer.parseInt(args[0]) : 1000;
		int transactions = args.length > 1 ? Integer.parseInt(args[1]) : 1_000_000;
		int pool = args.length > 2 ? Integer.parseInt(args[2]) : Runtime.getRuntime().availableProcessors();
		if (accounts < 2) throw new IllegalArgumentException("At least 2 accounts are needed");
		return new SimulationConfig(accounts, transactions, pool);
	}

	/** Picks two different random account indexes: [sender, receiver]. */
	public int[] randomPair() {
		int sender = (int) (Math.random() * nbAccounts);
		int receiver = (int) (Math.random() * nbAccounts);
		if (sender == receiver) {
			receiver = (receiver + 1) % nbAccounts;
		}
		return new int[] { sender, receiver };
	}
}
