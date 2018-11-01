package Accounts;

/**
 * Basic Account class, parent class of all account types.
 *
 * Contains code that checking, savings, and loan accounts
 */
public class Account {

    double currentAccountValue, currentAccountInterestRate, totalAccruedInterest;

    String accountName;

    /**
     * Returns the current value of the account as a double.
     */
    public double getCurrentAccountValue() {
        return this.currentAccountValue;
    }

    /**
     * Applies interest rate to the account. This function takes the total accrued interest and adds
     * the current value of the account times the current account interest rate.
     */
    public double applyInterest() {
        return this.totalAccruedInterest += (this.currentAccountValue * this.currentAccountInterestRate);
    }

    /**
     * Updates the currentInterestRate to the newAccountInterest
     */
    public void updateAccountInterest(double newAccountInterestRate) {
        this.currentAccountInterestRate = newAccountInterestRate;
    }

    /**
     * Sets the name of the account
     */
    public void setAccountName(String name) {
        this.accountName = name;
    }


} // end of account class
