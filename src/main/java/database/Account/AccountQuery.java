package database.Account;


import database.Entity;
import database.EntityQuery;

import java.nio.file.NoSuchFileException;
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
 *     getByType(String)
 *     getBySubType(String)
 *
 *     calls may be chained
 *           but must be terminated by:
 *
 *                 execute() (returns List<AccountEntity>)
 *                    or
 *                 getFirst() (returns first AccountEntity or null)
 *
 */
public class AccountQuery extends EntityQuery
{
    //initial query of all entities from Customer.txt
    private List<AccountEntity> initialSet;

    //intermediate resultSet (for chaining)
    private List<AccountEntity> tempSet;

    //final returned results
    private List<AccountEntity> resultSet;

    //true if one or more calls to a getBy function
    //(if chaining has begun)
    private boolean firstCallOccurred;

    public AccountQuery()
    {

        try
        {
            initialSet = AccountEntity.parse(Entity.readFromTextFile(new AccountEntity().getTextFileName(), new AccountEntity().getDelimiter()));
            tempSet = new ArrayList<>();
            resultSet = new ArrayList<>();

            firstCallOccurred = false;
        }catch(NoSuchFieldException | NoSuchFileException | InstantiationException | IllegalAccessException e){ e.printStackTrace(); }
    }

    // Use as chain terminator
    // instead of execute() if only
    // expecting one result.
    public AccountEntity getFirst()
    {
        AccountEntity singleResult;
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
    public List<AccountEntity> execute()
    {
        return resultSet;
    }

    //get all accounts, still needs to call execute()
    //only usage:   customerQuery.getAll().execute();
    public AccountQuery getAll()
    {
        resultSet = initialSet;
        return this;
    }

    //filter on ID
    public AccountQuery getByID(long id)
    {
        //if a getBy function was already called
        if(firstCallOccurred)
        {
            //filter resultSet to tempSet
            tempSet = resultSet.stream().filter(a -> a.getID() == id)
                    .collect(Collectors.toList());
            //replace resultSet with tempSet
            resultSet = tempSet;
        }
        else
        {
            //filter on getID() matching id
            resultSet = initialSet.stream().filter(a -> a.getID() == id)
                    .collect(Collectors.toList());
            firstCallOccurred = true;
        }

        return this;
    }

    //filter on SSN
    public AccountQuery getBySSN(String ssn)
    {
        if(firstCallOccurred)
        {
            tempSet = resultSet.stream().filter(a -> a.getSSN().equals(ssn))
                    .collect(Collectors.toList());
            resultSet = tempSet;
        }
        else
        {
            //filter on getSSN() matching ssn
            resultSet = initialSet.stream().filter(a -> a.getSSN().equals(ssn))
                    .collect(Collectors.toList());
            firstCallOccurred = true;
        }
        return this;
    }

    //filter on type
    public AccountQuery getByType(String type)
    {
        if(firstCallOccurred)
        {
            tempSet = resultSet.stream().filter(a -> a.getTYPE().toUpperCase().equals(type.toUpperCase()))
                    .collect(Collectors.toList());
            resultSet = tempSet;
        }
        else
        {
            //filter on gettype() matching type
            resultSet = initialSet.stream().filter(a -> a.getTYPE().toUpperCase().equals(type.toUpperCase()))
                    .collect(Collectors.toList());
            firstCallOccurred = true;
        }
        return this;
    }

    //filter on subtype
    public AccountQuery getBySubType(String stype)
    {
        if(firstCallOccurred)
        {
            tempSet = resultSet.stream().filter(a -> a.getSUBTYPE().toUpperCase().equals(stype.toUpperCase()))
                    .collect(Collectors.toList());
            resultSet = tempSet;
        }
        else
        {
            //filter on getsubtype() matching subtype
            resultSet = initialSet.stream().filter(a -> a.getSUBTYPE().toUpperCase().equals(stype.toUpperCase()))
                    .collect(Collectors.toList());
            firstCallOccurred = true;
        }
        return this;
    }


}//end of class AccountQuery
