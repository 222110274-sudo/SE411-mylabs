package sa.psu.se411;

import sa.psu.se411.exceptions.InvalidAgeException;
import sa.psu.se411.exceptions.InsufficientFundsException;

public class MainApp {

    public static void validateAge(int age) throws InvalidAgeException {
        if (age < Config.MIN_AGE) {
            throw new InvalidAgeException(age);
        }
        System.out.println("Age valid message.");
    }

    public static void main(String[] args) {
        try {
            validateAge(15);
        } catch (InvalidAgeException e) {
            e.printStackTrace();
        }

        Wallet wallet = new Wallet(100.0); // initial balance of 100
        try {
            wallet.withdraw(50.0);   // should succeed, balance becomes 50
            wallet.withdraw(200.0);  // should fail, exceeds remaining balance
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
        }
    }

}