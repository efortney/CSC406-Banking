package controller;

import java.io.IOException;

import database.Customer.Customer;
import database.Customer.CustomerQuery;

public class CustomerController
{
  //login a customer, if successful returns a non-null customer
  public static Customer authorizeCustomer(String username, String password)
  {
    Customer customerToAuth = new CustomerQuery().getByUsername(username).getFirst();
    
    if(customerToAuth != null)
    {
      if(customerToAuth.getPASSWORD().equals(password)){ return customerToAuth; }
    }

  return null;
  }

}//end of CustomerController class
