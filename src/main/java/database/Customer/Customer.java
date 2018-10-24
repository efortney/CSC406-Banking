package database.Customer;

import java.lang.reflect.Field;
import java.util.ArrayList;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


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
 *                              
 *                                MUST MATCH. 
 * 
 * ================ INTERNAL FUNCTONS ================
 *   
 *  toString() : Stringified Customer                  
 *      (for ID(hash) and printing)
 * 
 *  toTextFileString() : comma-delimited stringified Customer
 *      (for writing to text file)
 *
 *
 */
public class Customer
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

  public long   getID(){ return ID; }
  public String getFirstName(){ return FNAME; }
  public String getLastName(){ return LNAME; }
  public int    getSSN(){ return SSN; }
  public int    getPIN(){ return PIN; }
  public String getUsername(){ return USERNAME; }
  public String getPassword(){ return PASSWORD; }
  public String getEmail(){ return EMAIL; }
  public String getStreetAddress(){ return STREET_ADDRESS; }
  public String getCity(){ return CITY; }
  public String getState(){ return STATE; }
  public int    getZip(){ return ZIP; }

  public void setFirstName(String name){ FNAME = name; }
  public void setLastName(String name){ LNAME = name; }
  public void setPIN(int pin){ PIN = pin; }
  public void setUserName(String uname){ USERNAME = uname; }
  public void setPassword(String pass){ PASSWORD = pass; }
  public void setEmail(String email){ EMAIL = email; }
  public void setStreetAddress(String street){ STREET_ADDRESS = street; }
  public void setCity(String city){ CITY = city; }
  public void setState(String state){ STATE = state; }
  public void setZip(int zip){ ZIP = zip; }
 

  //used for generating hash (ID) and printing
  public String toString()
  {
    String asString = "First Name: " + getFirstName() + " \n" +
                      "Last Name: " + getLastName() + " \n" +
                      "Social Security #: " + getSSN() + " \n" +
                      "PIN: " + getPIN() + " \n" +
                      "Username: " + getUsername() + " \n" +
                      "Password: " + getPassword() + " \n" +
                      "Email: " + getEmail() + " \n" +
                      "Street Address: " + getStreetAddress() + " \n" +
                      "City: " + getCity() + " \n" + 
                      "State: " + getState() + " \n" +
                      "ZipCode: " + getZip() + " \n" +
                      "ID: " + getID();
    return asString;
  }//end of toString


  /*
   *   For generating record in the text file. (order matters!)
   *    Order must match member variable declaration.
   */
  public String toTextFileString()
  {
    String delimiter = ", ";

    String textFileString = getID() + delimiter +
                            getFirstName() + delimiter +
                            getLastName() + delimiter +
                            getSSN() + delimiter +
                            getPIN() + delimiter +
                            getUsername() + delimiter +
                            getPassword() + delimiter +
                            getEmail() + delimiter +
                            getStreetAddress() + delimiter +
                            getCity() + delimiter +
                            getState() + delimiter +
                            getZip();
    
    return textFileString;
  }//end of toTextFileString

}//end of Customer class
