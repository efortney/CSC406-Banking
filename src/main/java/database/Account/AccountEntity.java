package database.Account;

import database.Customer.Customer;
import database.Customer.CustomerQuery;
import database.Entity;
import database.EntityQuery;
import database.FieldNameTypeAndValue;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

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


    //takes an arraylist of each stringified (delimited) entity and casts each to an Account
    public static ArrayList<AccountEntity> parse(ArrayList<String> preparsedRecords) throws NoSuchFileException,NoSuchFieldException,InstantiationException,IllegalAccessException
    {
        //returned list of accounts
        ArrayList<AccountEntity> accountsFromFile = new ArrayList<>();

        ArrayList<String> preparsedFields = new ArrayList<String>();
        //fields after being parsed
        ArrayList<Object> parsedFields = new ArrayList<Object>();

        for(String account : preparsedRecords)
        {

            for(String field : account.split(new AccountEntity().getDelimiter()))
            {
                preparsedFields.add(field);
            }

            //because same order is maintained between
            //fields of AccountEntity class, and text file
            int i = 0;
            //cast each field to its corresponding type
            for(FieldNameTypeAndValue cfield : new AccountEntity().getMemberFields())
            {
                if(cfield.getType().equals("long"))
                {
                    parsedFields.add(Long.parseLong(preparsedFields.get(i)));
                }
                else if(cfield.getType().equals("int"))
                {
                    parsedFields.add(Integer.parseInt(preparsedFields.get(i)));
                }
                else{  parsedFields.add(preparsedFields.get(i)); }//its a String
                i += 1;
            }//end for loop


            AccountEntity parsedAccount = new AccountEntity((long)parsedFields.get(0), //ID
                                                            (String)parsedFields.get(1),//SSN
                                                            (double)parsedFields.get(2),//BALANCE
                                                            (String)parsedFields.get(3),//TYPE
                                                            (String)parsedFields.get(4),//SUBTYPE
                                                            (String)parsedFields.get(5),//OVERDRAFT_PROTECT
                                                            (long)parsedFields.get(6),//OVERDRAFT_ACCOUNT_ID
                                                            (String)parsedFields.get(7),//DATE_CREATED
                                                            (double)parsedFields.get(8),//INTEREST_RATE
                                                            (double)parsedFields.get(9),//MINIMUM_PAYMENT
                                                            (String)parsedFields.get(10));//DATE_NEXT_PAYMENT_DUE

            accountsFromFile.add(parsedAccount);

            //clear for next line input
            parsedFields.clear();
            preparsedFields.clear();
        }//end of for


        return accountsFromFile;
    }//end of parse()


    //these won't change, and we don't want them getting returned with other fields
    //from calls to getFields()
    public String getTextFileName(){ return "./db/Account/Account.txt"; }
    public String getBackupTextFileName(){ return "./db/Account/OLD_Account.txt"; }
    public String getDelimiter(){ return ", "; }

    public long   getID(){ return ID; }
    public String getSSN(){ return SSN; }
    public double getBALANCE(){ return BALANCE; }
    public String getTYPE(){ return TYPE; }
    public String getSUBTYPE(){ return SUBTYPE; }
    public String getOVERDRAFT_PROTECT(){ return OVERDRAFT_PROTECT; }
    public long   getOVERDRAFT_ACCOUNT_ID(){ return OVERDRAFT_ACCOUNT_ID; }
    public String getDATE_CREATED(){ return DATE_CREATED; }
    public double getINTEREST_RATE(){ return INTEREST_RATE; }
    public double getMINIMUM_PAYMENT(){ return MINIMUM_PAYMENT; }
    public String getDATE_NEXT_PAYMENT_DUE(){ return DATE_NEXT_PAYMENT_DUE; }

    public void setBALANCE(double amount){ BALANCE = amount; }
    public void setOVERDRAFT_PROTECT(String status){ OVERDRAFT_PROTECT = status; }
    public void setOVERDRAFT_ACCOUNT_ID(long id){ OVERDRAFT_ACCOUNT_ID = id; }
    public void setINTEREST_RATE(double rate){ INTEREST_RATE = rate; }
    public void setMINIMUM_PAYMENT(double amount){ MINIMUM_PAYMENT = amount; }
    public void setDATE_NEXT_PAYMENT_DUE(String date){ DATE_NEXT_PAYMENT_DUE = date; }
}
