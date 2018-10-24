package database.Customer;

import java.lang.reflect.Field;
import java.util.ArrayList;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import database.FieldNameAndType;


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
 *  getFields() : ArrayList<FieldNameAndType> fields
 *      (for parsing type conversion)
 * 
 *  toString() : Stringified Customer                  
 *      (for ID(hash) and printing)
 * 
 *  toTextFileString() : comma-delimited stringified Customer
 *      (for writing to text file)
 *
 *  (static) readFromTextFile() : ArrayList<Customer> customers
 *      (uses FieldNameAndType and getFields() to parse Customer.txt line by line)
 *
 *  writeToTextFile() : void
 *      (uses toTextFileString() to write to text file)
 *
 *  (static) deleteFromTextFile(long id) : void
 *      (internal call, write backup, write to new Customer.txt)
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

  //these won't change, and we don't want them getting returned with other fields
  //from calls to getFields()
  private static String getTextFileName(){ return "./db/Customer/Customer.txt"; }
  private static String getBackupTextFileName(){ return "./db/Customer/OLD_Customer.txt"; }

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
 
  //get all member variables (name and type) for Customer class 
  public static ArrayList<FieldNameAndType> getFields()
  {
    //FieldNameAndType is object with each field and its corresponding type
    //obtained through reflection/introspection
    ArrayList<FieldNameAndType> fields = new ArrayList<FieldNameAndType>();
    
    //java.lang.reflect.Field  :  reflective/introspective call
    for(Field field : Customer.class.getDeclaredFields())
    {
      String type;

      //field.getType() ==> "int" (or) "long"  (primitive)
      if(field.getType().toString().split(" ").length == 1)
      {
        fields.add(new FieldNameAndType(field.getName(),field.getType().toString()));
      }
      
      else //field.getType() ==> "class java.lang.String" ==> "java.lang.String" ==> "String"
      {
        //field.getType() ==>  ["class","java.lang.String"] ==> "java.lang.String"
        type = field.getType().toString().split(" ")[1];
        //"java.lang.String" ==> ["java","lang","String"] ==> "String"
        fields.add(new FieldNameAndType(field.getName(),type.split("\\.")[2]));
      }
    }//end of for
      
    return fields;
  }//end of getFields


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


  //line by line, reads, parses, casts and calls (parsing) constructor for each Customer
  public static ArrayList<Customer> readFromTextFile()
  {
    //returned list of customers
    ArrayList<Customer> customersFromFile = new ArrayList<Customer>(); 
    //fields straight from text file
    ArrayList<String> preparsedFields = new ArrayList<String>();
    //fields after being parsed
    ArrayList<Object> parsedFields = new ArrayList<Object>();

    //open and begin reading in (line by line) customers text file
    try (BufferedReader br = new BufferedReader(new FileReader(getTextFileName())))
    {

      String currentLine;
     
      while ((currentLine = br.readLine()) != null)
      {
        //ignore empty lines
        if(currentLine.length() == 0) continue;

        //split current line into separate fields
        for(String field : currentLine.split(", "))
        {
          preparsedFields.add(field);
        }
        
        //because same order is maintained between
        //fields of Customer class, and text file  
        int i = 0; 
        //cast each field to its corresponding type
        for(FieldNameAndType field : getFields())
        {
          if(field.getType().equals("long"))
          {
            parsedFields.add(Long.parseLong(preparsedFields.get(i)));
          }
          else if(field.getType().equals("int"))
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
        preparsedFields.clear();
        //clear for next line input
        parsedFields.clear();

      }//end while loop

    } catch (IOException e) { e.printStackTrace();}

    return customersFromFile;
  }//end of readFromTextFile


  //writes serialized Customer to text file
  public void writeToTextFile() throws IOException
  { 
    //                                                                     append!
    PrintWriter writer = new PrintWriter(new FileWriter(getTextFileName(), true));
    writer.println(toTextFileString());
    writer.close();  
  }


  //backup current Customer.txt => OLD_Customer.txt
  //write all except line with matching customerID to Customer.txt
  private static void deleteFromTextFile(long customerID) throws IOException
  {
    //lines to be written to new Customer.txt
    ArrayList<String> linesToKeep = new ArrayList<String>();

    //to write to backup file before replacing Customer.txt
    PrintWriter beforeDelete = new PrintWriter(new FileWriter(getBackupTextFileName()));

    //open and begin reading in (line by line) customers text file
    try (BufferedReader br = new BufferedReader(new FileReader(getTextFileName())))
    {

      String currentLine;
      //parsed ID
      long recordID;
     
      while ((currentLine = br.readLine()) != null)
      {
        //ignore empty lines
        if(currentLine.length() == 0) continue;

        //write to backup
        beforeDelete.println(currentLine);

        //grab the id of the record (first field)
        recordID = Long.parseLong(currentLine.split(", ")[0]);

        //skip over delete entity
        if(recordID == customerID) continue; 
        else 
        {
          linesToKeep.add(currentLine);
        }

      }//end while loop

    } catch (IOException e) { e.printStackTrace();}

    //IMPORTANT: must not be called until after backup is written to.
    PrintWriter afterDelete = new PrintWriter(new FileWriter(getTextFileName()));

    //write new Customer.txt
    for(String line : linesToKeep){ afterDelete.println(line); }
    
    //close open files
    beforeDelete.close();
    afterDelete.close();

  }//end of deleteFromTextFile


}//end of Customer class
