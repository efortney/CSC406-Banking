package Accounts;

/**
 * DebitAccount class is the parent class for
 * checking and savings accounts, providing functionality that both
 * account types will need access too.
 */
public class DebitAccount extends Account{

    // this allows accounts to maintain a minimum balance
    double minimumBalance;

    /**
     * Withdrawal debits an account with the double amount specified
     */
    void withdrawal(double amountToWithdrawal) {
        this.currentAccountValue -= amountToWithdrawal;
    }

    /**
     * Deposit credits the account with the double amount specified.
     */
    public void desposit(double amountToDeposit) {
        this.currentAccountValue += amountToDeposit;
    }

    /**
     * Creates a transfer by subtracting the amount to transfer from the current
     * account's value and adding the transfer amount to the requested account.
     */
    public void transfer(double transferAmount, Account transferAccount) {
        this.currentAccountValue -= transferAmount;
        transferAccount.currentAccountValue += transferAmount;
    }

    /**
     * TODO
     * This function will stop debits from the selected account
     */
    public void stopPayment() {

    }





}
