package Accounts;

/**
 * Checking account class is the base class for all checking account types.
 * It is extended by Student, Gold, and Platinum accounts.
 */
public class Checking extends DebitAccount{

    private double transactionFee, minimumRequiredBalance;

    private Account associatedSavingsAccount;


    /**
     * Sets the savings account associated
     */
    public void setAssociatedSavingsAccount(Account savingsAccount) {
        associatedSavingsAccount =  savingsAccount;
    }

    /**
     * This method adds the amt to withdrawal plus the fee, and compares it to the account value
     * If the checking account value is less, it will charge the savings account.
     */
    void performOverDraftProtection(double amountToWithdrawal) {
        if(amountToWithdrawal + transactionFee < currentAccountValue) {
            super.withdrawal(amountToWithdrawal + transactionFee);
        } else {
            chargeSavingsAccount(amountToWithdrawal);
        }
    }

    /**
     * Charges the savings account associated with checking account
     */
    private void chargeSavingsAccount(double amountToWithdrawal){
        associatedSavingsAccount.currentAccountValue -= (amountToWithdrawal + transactionFee);
    }

    /**
     * Boolean value determining if a savings account exists.
     */
    private boolean hasAssociatedSavingsAccount() {
        return associatedSavingsAccount != null;
    }

    /**
     * Sets the minimum required balance for the account
     */
    public void setMinimumRequiredBalance(double newMinimumBalance) {
        this.minimumRequiredBalance = newMinimumBalance;
    }


    /**
     * These overridden methods allow for a transaction fee to be applied to debit or credit transactions.
     */
    @Override
    public void withdrawal(double amountToWithdrawal) {
        if(hasAssociatedSavingsAccount()) {
            performOverDraftProtection(amountToWithdrawal);
        }else {
            super.withdrawal(amountToWithdrawal + transactionFee);
        }
    }

    @Override
    public void desposit(double amountToDeposit) {
        super.desposit(amountToDeposit - transactionFee);
    }


}
