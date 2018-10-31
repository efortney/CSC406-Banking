

/*
 *    Examples for Customer CRUD operations.
 *
 */

import database.Customer.Customer;
import database.Customer.CustomerQuery;

import java.util.List;

public class CRUDexamples
{      
  public static void main(String[] args)
  {

    //Add a new test customer
      Customer newCustomer = new Customer("TEST",//LNAME
                                          "TEST",//LNAME
                                          0,//SSN
                                          "TEST",//USERNAME
                                          "TEST",//PASSWORD
                                          "TEST@TEST",//EMAIL
                                          "TEST",//STREET_ADDRESS
                                          "TEST",//CITY
                                          "TEST",//STATE
                                          0);//ZIP

      newCustomer.add();

      //won't add duplicate customer
      newCustomer.add();

      if(!newCustomer.add()) System.out.println("new customer already added.");

      newCustomer.setEMAIL("PROD@PROD");

      newCustomer.update();

      List<Customer> customers = new CustomerQuery().getByID(newCustomer.getID()).execute();

      for(Customer c : customers)
      {
        System.out.println(c);
      }
    
      newCustomer.delete();

      System.out.println("Deleted new Customer.");

      System.out.println("\n===============All Customers============== \n");

      List<Customer> all = new CustomerQuery().getAll().execute();

      for(Customer c : all)
      {
          System.out.println(c);
      }
  }

}//end of Main class
