package database.Customer;

import java.util.ArrayList;
import java.nio.file.NoSuchFileException;
import java.lang.NoSuchFieldException;

import database.FieldNameTypeAndValue;
import database.EntityQuery;
import database.Entity;


/*
 *  Customer class serves as a database wrapper.
 *   It is a POJO with added functions for writing to
 * and reading from the text file Customer.txt.
 *
 *  It has two constructors. The first
 * is for internal/application logic creation
 * of Customers.
 *  The second is for parsing in Customer objects.
 * (from Customer.txt)
 *
 *    IMPORTANT: Order of member variable declaration
 *                                     and
 *               Order of fields per line in Customer.txt
 *                                     and
 *               Field names and getters. (String FNAME :: getFNAME())
 *                              
 *                                MUST MATCH. 
 * 
 * ================ FUNCTONS ================
 *
 *  parse(ArrayList<String> preparsedRecords) : ArrayList<Customer> customers
 * (uses getMemberFields() to parse/cast each String of fields to a Customer)
 *
 *  query() : CustomerQuery
 * (
 */


public class Customer extends Entity
{
  //member variables order matters
  //must be the same order as toTextFileString()'s
  //local variable
  private long   ID;
  private String FNAME;
  private String LNAME;
  private int    SSN;
  private int    PIN;
  private String USERNAME;
  private String PASSWORD;
  private String EMAIL;
  private String STREET_ADDRESS;
  private String CITY;
  private String STATE;
  private int    ZIP;

  public Customer(){}

  //business logic creation constructor
  public Customer(String FNAME, String LNAME, int SSN, int PIN, String USERNAME, String PASSWORD, String EMAIL, String STREET_ADDRESS, String CITY, String STATE, int ZIP)
  {
    this.FNAME = FNAME;
    this.LNAME = LNAME;
    this.SSN = SSN;
    this.PIN = PIN;
    this.USERNAME = USERNAME;
    this.PASSWORD = PASSWORD;
    this.EMAIL = EMAIL;
    this.STREET_ADDRESS = STREET_ADDRESS;
    this.CITY = CITY;
    this.STATE = STATE;
    this.ZIP = ZIP;

    this.ID = toString().hashCode();
    
  }//end of initial creation Customer constructor

  //parsing constructor (when constructing objects from parsed text file)
  public Customer(long ID, String FNAME, String LNAME, int SSN, int PIN, String USERNAME, String PASSWORD, String EMAIL, String STREET_ADDRESS, String CITY, String STATE, int ZIP)
  {
    this.ID = ID;
    this.FNAME = FNAME;
    this.LNAME = LNAME;
    this.SSN = SSN;
    this.PIN = PIN;
    this.USERNAME = USERNAME;
    this.PASSWORD = PASSWORD;
    this.EMAIL = EMAIL;
    this.STREET_ADDRESS = STREET_ADDRESS;
    this.CITY = CITY;
    this.STATE = STATE;
    this.ZIP = ZIP;
    
  }//end of parsing Customer constructor

      
  //takes an arraylist of each stringified (delimited) entity and casts each to a Customer
  public static ArrayList<Customer> parse(ArrayList<String> preparsedRecords) throws NoSuchFileException,NoSuchFieldException,InstantiationException,IllegalAccessException
  {
    //returned list of customers
    ArrayList<Customer> customersFromFile = new ArrayList<Customer>(); 

    ArrayList<String> preparsedFields = new ArrayList<String>();
    //fields after being parsed
    ArrayList<Object> parsedFields = new ArrayList<Object>();

      for(String customer : preparsedRecords)
      {  

        for(String field : customer.split(new Customer().getDelimiter()))
        {
          preparsedFields.add((String)field);
        }
        
        //because same order is maintained between
          //fields of Customer class, and text file  
          int i = 0; 
          //cast each field to its corresponding type
          for(FieldNameTypeAndValue cfield : new Customer().getMemberFields())
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

          Customer parsedCustomer = new Customer((long)parsedFields.get(0), //ID
                                               (String)parsedFields.get(1),//FNAME
                                               (String)parsedFields.get(2),//LNAME
                                               (int)parsedFields.get(3),//SSN
                                               (int)parsedFields.get(4),//PIN
                                               (String)parsedFields.get(5),//USERNAME
                                               (String)parsedFields.get(6),//PASSWORD
                                               (String)parsedFields.get(7),//EMAIL
                                               (String)parsedFields.get(8),//STREET_ADDRESS
                                               (String)parsedFields.get(9),//CITY
                                               (String)parsedFields.get(10),//STATE
                                               (int)parsedFields.get(11));//ZIP

          customersFromFile.add(parsedCustomer);
                          
          //clear for next line input
          parsedFields.clear();
          preparsedFields.clear();
        }//end of for


    return customersFromFile;
  }//end of parse()


  /*
   *  Customer inherits query() from Entity.
   * Needs implementation to call this instead of Entity.query().
   */
  public EntityQuery query()
  {
    return new CustomerQuery();
  }//end of query

  
  //these won't change, and we don't want them getting returned with other fields
  //from calls to getFields()
  public String getTextFileName(){ return "./db/Customer/Customer.txt"; }
  public String getBackupTextFileName(){ return "./db/Customer/OLD_Customer.txt"; }
  public String getDelimiter(){ return ", "; }

  public long   getID(){ return ID; }
  public String getFNAME(){ return FNAME; }
  public String getLNAME(){ return LNAME; }
  public int    getSSN(){ return SSN; }
  public int    getPIN(){ return PIN; }
  public String getUSERNAME(){ return USERNAME; }
  public String getPASSWORD(){ return PASSWORD; }
  public String getEMAIL(){ return EMAIL; }
  public String getSTREET_ADDRESS(){ return STREET_ADDRESS; }
  public String getCITY(){ return CITY; }
  public String getSTATE(){ return STATE; }
  public int    getZIP(){ return ZIP; }

  public void setFNAME(String name){ FNAME = name; }
  public void setLNAME(String name){ LNAME = name; }
  public void setPIN(int pin){ PIN = pin; }
  public void setUSERNAME(String uname){ USERNAME = uname; }
  public void setPASSWORD(String pass){ PASSWORD = pass; }
  public void setEMAIL(String email){ EMAIL = email; }
  public void setSTREET_ADDRESS(String street){ STREET_ADDRESS = street; }
  public void setCITY(String city){ CITY = city; }
  public void setSTATE(String state){ STATE = state; }
  public void setZIP(int zip){ ZIP = zip; }

}//end of Customer class
