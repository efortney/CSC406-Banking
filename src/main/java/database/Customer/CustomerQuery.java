package database.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 *   CustomerQuery  
 *         Supports following calls:
 *
 *     getAll()
 *     getByID(long)
 *     getBySSN(int)
 *     getByUsername(String)
 *
 *     calls may be chained 
 *           but must be terminated by: 
 *                        
 *                 execute() (returns List<Customer>)
 *                    or 
 *                 getFirst() (returns first Customer or null)
 *                                   
 */
public class CustomerQuery
{
  //initial query of all entities from Customer.txt
  private List<Customer> initialSet;

  //intermediate resultSet (for chaining)
  private List<Customer> tempSet;  

  //final returned results
  private List<Customer> resultSet;

  //true if one or more calls to a getBy function
  //(if chaining has begun)
  private boolean firstCallOccurred;

  public CustomerQuery()
  {
    initialSet = Customer.readFromTextFile();
    tempSet = new ArrayList<Customer>();
    resultSet = new ArrayList<Customer>();

    firstCallOccurred = false;
  }

  // Use as chain terminator 
  // instead of execute() if only
  // expecting one result.
  public Customer getFirst()
  {
    Customer singleResult;
    //no result
    if(resultSet.size() < 1) singleResult = null;
    else
    {
      singleResult = resultSet.get(0);
    }
    return singleResult;
  }
  
  // Use as chain terminator.
  // instead of getFirst if
  // expecting more than one result.
  public List<Customer> execute()
  {
    return resultSet;
  }

  //get all customers, still needs to call execute()
  //only usage:   customerQuery.getAll().execute();
  public CustomerQuery getAll()
  {
    resultSet = initialSet;
    return this;
  }

  //filter on ID
  public CustomerQuery getByID(long id)
  {
    //if a getBy function was already called
    if(firstCallOccurred)
    {
      //filter resultSet to tempSet
      tempSet = resultSet.stream().filter(c -> c.getID() == id)
                         .collect(Collectors.toList());
      //replace resultSet with tempSet
      resultSet = tempSet;
    }
    else
    {
      //filter on getID() matching id
      resultSet = initialSet.stream().filter(c -> c.getID() == id)
                          .collect(Collectors.toList());
      firstCallOccurred = true;
    }

    return this;
  }

  //filter on SSN
  public CustomerQuery getBySSN(int ssn)
  {
    if(firstCallOccurred)
    {
      tempSet = resultSet.stream().filter(c -> c.getSSN() == ssn)
                         .collect(Collectors.toList());
      resultSet = tempSet;
    }
    else
    {
      //filter on getSSN() matching ssn
      resultSet = initialSet.stream().filter(c -> c.getSSN() == ssn)
                          .collect(Collectors.toList());
      firstCallOccurred = true;
    }

    return this;
  }
  //filter on Username
  public CustomerQuery getByUsername(String username)
  {
    if(firstCallOccurred)
    {
      tempSet = resultSet.stream().filter(c -> c.getUsername().equals(username))
                         .collect(Collectors.toList());
      resultSet = tempSet;
    }
    else
    {
      //filter on getLastName() matching lastName
      resultSet = initialSet.stream().filter(c -> c.getUsername().equals(username))
                          .collect(Collectors.toList());
      firstCallOccurred = true;
    }

    return this;
  }//end of getByUsername()

}//end of class CustomerQuery