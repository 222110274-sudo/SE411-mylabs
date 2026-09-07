package sa.psu.se411.exceptions;

public class InsufficientFundsException extends Exception {

    private double shortfall;

    public InsufficientFundsException(double requestedAmount, double availableBalance) {
        super("Insufficient funds: requested " + requestedAmount
                + " but only " + availableBalance + " available.");
        this.shortfall = requestedAmount - availableBalance;
    }

    public double getShortfall() {
        return shortfall;
    }

}