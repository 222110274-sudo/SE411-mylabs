package edu.psu.se411.lab12_threads.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ExecutorUtils {

	private ExecutorUtils() {
	}

	/** Waits until every submitted task is finished. */
	public static void awaitTermination(ExecutorService executor) throws InterruptedException {
		executor.shutdown();
		while (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
			// keep waiting
		}
	}

	/**
	 * Waits until every task is finished, but checks for deadlocked threads
	 * every second. Returns false if a deadlock was found.
	 */
	public static boolean awaitTerminationOrDeadlock(ExecutorService executor) throws InterruptedException {
		executor.shutdown();
		ThreadMXBean mx = ManagementFactory.getThreadMXBean();
		while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
			long[] ids = mx.findDeadlockedThreads();
			if (ids != null) {
				System.out.println("\n*** DEADLOCK DETECTED between " + ids.length + " threads ***");
				for (ThreadInfo info : mx.getThreadInfo(ids)) {
					System.out.println("  " + info.getThreadName() + " is waiting for a lock owned by " + info.getLockOwnerName());
				}
				return false;
			}
		}
		return true;
	}

	/** Largest number of threads the pool ever had at the same time. */
	public static int largestPoolSize(ExecutorService executor) {
		return executor instanceof ThreadPoolExecutor tpe ? tpe.getLargestPoolSize() : -1;
	}
}
