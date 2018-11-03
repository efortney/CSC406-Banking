
import database.Account.AccountEntity;
import database.Account.AccountQuery;

import database.Customer.Customer;
import database.Customer.CustomerQuery;

import java.util.List;

public class AccountCRUD {

    public static void main(String[] args)
    {


        Customer c = new CustomerQuery().getBySSN("423453245").getFirst();
        System.out.println(c);

        System.out.println();
        
        AccountEntity a1 = new AccountEntity("423453245",
                                             3000.00,
                                             "Savings",
                                             "Gold",
                                             "OFF",
                                             0,
                                             "05/04/2016",
                                             0.1,
                                             0,
                                       "null");

        AccountEntity a2 = new AccountEntity("423453245",
                9000.00,
                "Checking",
                "Platinum",
                "OFF",
                0,
                "05/04/2016",
                0.65,
                0,
                "null");

        AccountEntity a3 = new AccountEntity("423453245",
                -23000.00,
                "Loan",
                "Longterm",
                "OFF",
                0,
                "05/04/2016",
                0.65,
                500.0,
                "11/30/2018");

        a1.add();
        a2.add();
        a3.add();

        System.out.println("Let's set up a back up account: ");

        //get the savings account to use as backup account
        AccountEntity backupAccount = new AccountQuery().getBySSN(c.getSSN())
                                                        .getByType("Savings")
                                                        .getFirst();
        //get the account we wish to backup
        AccountEntity accountOverdraftProtect = new AccountQuery().getBySSN(c.getSSN())
                                                                  .getByType("Checking")
                                                                  .getFirst();

        //set up a backup account for the accountOverdraftProtect
        accountOverdraftProtect.setOVERDRAFT_ACCOUNT_ID(backupAccount.getID());
        accountOverdraftProtect.setOVERDRAFT_PROTECT("ON");

        //update account
        accountOverdraftProtect.update();



        //now to get the backup account from accountOverdraftProtect
        AccountEntity backupAccountTest = new AccountQuery().getByID(accountOverdraftProtect.getOVERDRAFT_ACCOUNT_ID())
                                                            .getFirst();

        System.out.println("overdraft account id: " + accountOverdraftProtect.getOVERDRAFT_ACCOUNT_ID() +
                           " for account: \n" + accountOverdraftProtect + "ID: " + accountOverdraftProtect.getID() +
                           "\n\nbackup Account: \n" + backupAccountTest +"ID: " + backupAccountTest.getID());

        List<AccountEntity> accountsForCustomer = new AccountQuery().getBySSN(c.getSSN()).execute();

        System.out.println("========== All accounts for customer: " + c.getFNAME() + " with ssn: " + c.getSSN());
        System.out.println(accountsForCustomer);


        List<AccountEntity> loansForCustomer = new AccountQuery().getBySSN(c.getSSN())
                                                                    .getByType("Loan")
                                                                    .execute();
        System.out.println("========== All loan accounts for customer: " + c.getFNAME() + " with ssn: " + c.getSSN());
        System.out.println(loansForCustomer);

    }
}
