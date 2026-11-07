# Lab 12 – Threads and Locks

All programs take optional arguments: `[nbAccounts] [nbTransactions] [poolSize]`
(defaults: 1000 accounts, 1,000,000 transactions, pool size = number of CPU cores).

| Part | Main class | What it shows |
|------|-----------|---------------|
| a | `App` | Fixed thread pool + `synchronized` accounts |
| a | `AppCachedThreadPool` | Same workload with a cached thread pool |
| b, c | `AppLocks` | `synchronized` replaced by `ReentrantLock` → deadlock (detected and reported) |
| d | `AppLockOrdering` | Locks taken in ascending account-id order → no deadlock |

Run, for example:

```
mvn clean package exec:java -Dexec.args="10 1000000"
mvn clean package exec:java -Dexec.mainClass=edu.psu.se411.lab12_threads.AppCachedThreadPool -Dexec.args="10 1000000"
mvn clean package exec:java -Dexec.mainClass=edu.psu.se411.lab12_threads.AppLocks -Dexec.args="10 1000000"
mvn clean package exec:java -Dexec.mainClass=edu.psu.se411.lab12_threads.AppLockOrdering -Dexec.args="10 1000000"
```

## a. Fixed vs cached thread pool

Measured with 1,000,000 transactions on a 2-core machine, 3 runs each
(the fixed pool has 2 threads = number of cores):

| Case | Fixed pool | Cached pool |
|------|-----------|-------------|
| a. 1000 accounts | 413 – 673 ms (2 threads) | 3501 – 3904 ms (up to 60 threads) |
| b. 10 accounts   | 506 – 546 ms (2 threads) | 3447 – 3634 ms (up to 62 threads) |

In every run the total money in the system stays the same (10,000,000 and
100,000), so `synchronized` keeps the accounts consistent with both pools.

### c. What do you observe?

- **The fixed pool is about 6–7 times faster in both cases.** Each transaction
  is a very small task. A fixed pool keeps a few threads busy and puts the
  tasks in a queue. A cached pool hands every task directly to an idle thread
  (`SynchronousQueue`), and if none is free at that moment it creates a new
  thread. It ends up with about 60 threads on only 2 cores, so the time goes
  into creating threads, context switching and the hand-off between threads,
  not into real work.
- **The number of accounts changes little here.** With 10 accounts, more
  transactions compete for the same account monitors, so there is more
  contention. But each critical section is so short that the thread-pool
  overhead dominates the time.
- **A cached pool is not bounded.** If tasks block or take longer (for example
  more contention on few accounts), it keeps creating threads. The lab 11
  version, `newFixedThreadPool(nbTransactions)`, has the same problem in a
  worse form: with 1,000,000 transactions it asks for 1,000,000 OS threads, and
  trying that crashed the test machine. That is why the fixed pool is sized to
  the number of cores.
- **Conclusion:** for many short CPU-bound tasks, use a fixed pool sized to the
  number of cores. A cached pool suits many short-lived tasks that mostly
  *wait* (I/O), not tasks that use the CPU.

## b. Replacing `synchronized` by locks

`LockCryptoAccount` uses a `ReentrantLock` instead of `synchronized` methods
(`lock()` / `unlock()` in `try/finally`). To make the whole transfer atomic
(check balance → withdraw → deposit), `LockTransaction` locks **the sender
first, then the receiver**, and releases them in reverse order.

## c. What could go wrong? → Deadlock

Two transactions running at the same time in opposite directions:

```
Thread 1:  A -> B   locks A ... waits for B
Thread 2:  B -> A   locks B ... waits for A
```

Each thread holds one lock and waits forever for the lock the other one holds.
All four Coffman conditions are met (mutual exclusion, hold-and-wait,
no preemption, circular wait), so the program never terminates.

`AppLocks` checks for this with `ThreadMXBean.findDeadlockedThreads()`. With 10
accounts and 1,000,000 transactions it deadlocked in **3 out of 3 runs**,
after about 1.6 s:

```
*** DEADLOCK DETECTED between 2 threads ***
  pool-1-thread-1 is waiting for a lock owned by pool-1-thread-2
  pool-1-thread-2 is waiting for a lock owned by pool-1-thread-1
```

With 1000 accounts it finished in 2 out of 2 runs. The deadlock is still
possible, just less likely, because two transactions on the same pair of
accounts in opposite directions rarely run at the same moment. This is what
makes deadlocks dangerous: the code can pass tests and still freeze in
production.

## d. Solution: resource ordering

Accounts get a total order (`LockCryptoAccount implements Comparable`, compared
by their unique `id`). `OrderedLockTransaction` always locks the **smaller**
account first, whatever the direction of the transfer:

```java
LockCryptoAccount first  = sender.compareTo(receiver) < 0 ? sender : receiver;
LockCryptoAccount second = (first == sender) ? receiver : sender;
first.getLock().lock();
try {
    second.getLock().lock();
    try { transfer(); } finally { second.getLock().unlock(); }
} finally { first.getLock().unlock(); }
```

Now `A -> B` and `B -> A` both try to lock `A` first, so a circular wait
cannot happen. `AppLockOrdering` finishes every time with the money preserved:

| Case | Result |
|------|--------|
| 10 accounts, 1,000,000 transactions | finished in 527 ms, total 100,000 (unchanged) |
| 1000 accounts, 1,000,000 transactions | finished in 612 ms, total 10,000,000 (unchanged) |

*Timings depend on the machine; rerun to get your own numbers.*
