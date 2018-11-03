package database.Account;

import database.Entity;

public class AccountEntity extends Entity
{

    //member variables order matters
    //must be the same order as toTextFileString()'s
    //local variable
    private long   ID;
    private String SSN;
    private double BALANCE;
    private String TYPE;
    private String SUBTYPE;
    private String OVERDRAFT_PROTECT;
    private long   OVERDRAFT_ACCOUNT_ID;
    private String DATE_CREATED;
    private double INTEREST_RATE;
    private double MINIMUM_PAYMENT;
    private String DATE_NEXT_PAYMENT_DUE;

    public AccountEntity(){}

    public AccountEntity(String SSN, double BALANCE, String TYPE, String SUBTYPE,
                         String OVERDRAFT_PROTECT, long OVERDRAFT_ACCOUNT_ID,
                         String DATE_CREATED, double INTEREST_RATE,
                         double MINIMUM_PAYMENT, String DATE_NEXT_PAYMENT_DUE)
    {
        this.SSN = SSN;
        this.BALANCE = BALANCE;
        this.TYPE = TYPE;
        this.SUBTYPE = SUBTYPE;
        this.OVERDRAFT_PROTECT = OVERDRAFT_PROTECT;
        this.OVERDRAFT_ACCOUNT_ID = OVERDRAFT_ACCOUNT_ID;
        this.DATE_CREATED = DATE_CREATED;
        this.INTEREST_RATE = INTEREST_RATE;
        this.MINIMUM_PAYMENT = MINIMUM_PAYMENT;
        this.DATE_NEXT_PAYMENT_DUE = DATE_NEXT_PAYMENT_DUE;

        this.ID = toString().hashCode();

    }//end of (business logic) AccountEntity constructor

    //parsing constructor (when constructing objects from parsed text file)
    public AccountEntity(long ID, String SSN, double BALANCE, String TYPE, String SUBTYPE,
                         String OVERDRAFT_PROTECT, long OVERDRAFT_ACCOUNT_ID,
                         String DATE_CREATED, double INTEREST_RATE,
                         double MINIMUM_PAYMENT, String DATE_NEXT_PAYMENT_DUE)
    {
        this.ID = ID;
        this.SSN = SSN;
        this.BALANCE = BALANCE;
        this.TYPE = TYPE;
        this.SUBTYPE = SUBTYPE;
        this.OVERDRAFT_PROTECT = OVERDRAFT_PROTECT;
        this.OVERDRAFT_ACCOUNT_ID = OVERDRAFT_ACCOUNT_ID;
        this.DATE_CREATED = DATE_CREATED;
        this.INTEREST_RATE = INTEREST_RATE;
        this.MINIMUM_PAYMENT = MINIMUM_PAYMENT;
        this.DATE_NEXT_PAYMENT_DUE = DATE_NEXT_PAYMENT_DUE;

    }//end of parsing AccountEntity constructor

}
