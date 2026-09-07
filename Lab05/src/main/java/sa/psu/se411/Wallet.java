package sa.psu.se411;

import sa.psu.se411.exceptions.InsufficientFundsException;

public class Wallet {

    private double balance;

    public Wallet(double initialBalance) {
        this.balance = initialBalance;
    }


	public double getBalance() {
        return balance;
    }

    /**
     * Withdraws money from the wallet to the user's bank account.
     * Throws InsufficientFundsException if the amount exceeds the balance.
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(amount, balance);
        }
        balance -= amount;
        System.out.println("Withdrawal successful. New balance: " + balance);
    }

}