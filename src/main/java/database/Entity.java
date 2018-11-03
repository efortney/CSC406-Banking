package database;

import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.NoSuchFieldException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;

/*  
 *   Entity class is the base class for all entity types.
 *
 *================ INTERNAL FUNCTIONS ====================
 *
 * getMemberFields() : ArrayList<FieldNameTypeAndValue> fields
 *      (for parsing type conversion)
 *   
 *  toString() : Stringified Entity                  
 *      (for ID(hash) and printing)
 * 
 *  toTextFileString() : comma-delimited stringified Entity
 *      (for writing to text file)
 *
 *  writeToTextFile() : void
 *      (uses toTextFileString() to write to text file)
 *
 *  (static) readFromTextFile() : ArrayList<String> records
 *      (uses FieldNameTypeAndValue and getFields() to parse <entity>.txt line by line)
 *
 *  deleteFromTextFile(long id) : void
 *      (write backup, write to new <entity>.txt)
 *
 *================ EXTERNAL API FUNCTIONS ================
 *
 *  Entity.add() : boolean success
 *      (uses writeToTextFile() after checking if entity already exists)
 *
 *  Entity.update() : boolean success
 *      (uses deleteFromTextFile() to delete old entity and writes new updated
 *           entity via writeToTextFile() to <entity>.txt)
 *  
 *  Entity.delete() : boolean success
 *      (uses deleteFromTextFile() after it confirms entity exists)
 *
 *
 */
public class Entity
{
  //methods for polymorphism (these are called on subclasses of Entity)
  public long   getID(){ return 0; }
  public String getSSN(){return "null";}
  public String getTextFileName(){ return ""; }
  public String getBackupTextFileName(){ return ""; }
  public String getDelimiter(){ return ", "; }
  public EntityQuery query(){ return new EntityQuery(); }


  //used for generating hash (ID) and printing
  public String toString()
  {   
    StringBuilder asString = new StringBuilder();

    try{
      //get all fields
      ArrayList<FieldNameTypeAndValue> fields = this.getMemberFields();

      for(FieldNameTypeAndValue field : fields)
      {
        //skip ID field
        if(field.getName().equals("ID")) continue ;

        asString.append(field.getName() + ": " + field.getValue() + " \n");
      }
    }catch(Exception e){ e.printStackTrace(); }
 
    return asString.toString();
  }//end of toString


  //get all member variables (name, type and value) for Calling (subclass Entity) class 
  public ArrayList<FieldNameTypeAndValue> getMemberFields()
  {
    //FieldNameAndType is object with each field and its corresponding type
    //obtained through reflection/introspection
    ArrayList<FieldNameTypeAndValue> fields = new ArrayList<FieldNameTypeAndValue>();
    
    //java.lang.reflect.Field  :  reflective/introspective call
    for(Field field : this.getClass().getDeclaredFields())
    {
      String type;

      //field.getType() ==> "int" (or) "long" (or) "double" (primitive)
      if(field.getType().toString().split(" ").length == 1)
      {
        fields.add(new FieldNameTypeAndValue(field.getName(),field.getType().toString()));
      }
      
      else //field.getType() ==> "class java.lang.String" ==> "java.lang.String" ==> "String"
      {
        //field.getType() ==>  ["class","java.lang.String"] ==> "java.lang.String"
        type = field.getType().toString().split(" ")[1];
        //"java.lang.String" ==> ["java","lang","String"] ==> "String"
        fields.add(new FieldNameTypeAndValue(field.getName(),type.split("\\.")[2]));
      }
    }//end of for

    //add in values to FieldNameTypeAndValue
    for(FieldNameTypeAndValue field : fields)
    {
      try
      {
        //get the get method for this field
        Method getter = this.getClass().getDeclaredMethod("get" + field.getName());
        //set value by invoking getter for this field
        field.setValue(getter.invoke(this));
      }catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e){ e.printStackTrace(); }
         
     }//end of for

    return fields;
  }//end of getMemberFields


  /*
   *   For generating record in the text file. (order matters!)
   *    Order must match member variable declaration.
   */
  public String toTextFileString()
  {
    String delimiter = this.getDelimiter();

    StringBuilder textFileString = new StringBuilder();

    int index = 0;
    int last = this.getMemberFields().size() - 1;

    //generates stringified (delimited) entity (one line per record)
    for(FieldNameTypeAndValue field : this.getMemberFields())
    {
      if(index == last) textFileString.append(field.getValue());
      else
        {
          textFileString.append(field.getValue() + delimiter);
        }
        index += 1;
    }
    
    return textFileString.toString();
  }//end of toTextFileString


  
  //line by line, reads from <entity>.txt, splitting on delimiter and returning an ArrayList of (String) records
  public static ArrayList<String> readFromTextFile(String textFileName, String delimiter)
  {
 
    //fields straight from text file
    ArrayList<String> preparsedEntities = new ArrayList<String>();

    //open and begin reading in (line by line) <entity> text file
    try (BufferedReader br = new BufferedReader(new FileReader(textFileName)))
    {

      String currentLine;
      StringBuilder strB = new StringBuilder();
     
      while ((currentLine = br.readLine()) != null)
      {
        //ignore empty lines
        if(currentLine.length() == 0) continue;

        int count = 0;
        int last = currentLine.split(delimiter).length - 1;
        //split current line into separate fields
        for(String field : currentLine.split(delimiter))
        {
          if(count == last)
          {
            strB.append(field.replace(",",""));
            break;
          }
          strB.append(field + delimiter);
          count += 1;
        }
        preparsedEntities.add(strB.toString());
        //System.out.println(strB.toString());
        strB.setLength(0);

      }//end while loop

    } catch (IOException e) { e.printStackTrace();}

    return preparsedEntities;
  }//end of readFromTextFile


  //writes serialized Customer to text file
  public void writeToTextFile()
  { 
    try
    {
      //                                                                         append!
      PrintWriter writer = new PrintWriter(new FileWriter(this.getTextFileName(), true));
      writer.println(this.toTextFileString());
      writer.close();
    }catch(IOException e){ e.printStackTrace(); }
  }//end of writeToTextFile()


  
    //backup current <entity>.txt => OLD_<entity>.txt
  //write all except line with matching entityID to <entity>.txt
  public void deleteFromTextFile()
  {
    //open and begin reading in (line by line) text file
    try (BufferedReader br = new BufferedReader(new FileReader(this.getTextFileName())))
    {

      long entityID = this.getID();
    
      //lines to be written to new <entity>.txt
      ArrayList<String> linesToKeep = new ArrayList<String>();

      //to write to backup file before replacing <entity>.txt
      PrintWriter beforeDelete = new PrintWriter(new FileWriter(this.getBackupTextFileName()));

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
        if(recordID == entityID) continue; 
        else 
        {
          linesToKeep.add(currentLine);
        }

      }//end while loop

      //IMPORTANT: must not be called until after backup is written to.
      PrintWriter afterDelete = new PrintWriter(new FileWriter(getTextFileName()));

      //write new <entity>.txt
      for(String line : linesToKeep){ afterDelete.println(line); }
    
      //close open files
      beforeDelete.close();
      afterDelete.close();

    } catch (IOException e) { e.printStackTrace();}

  }//end of deleteFromTextFile

  
  
  //add entity (visible api call)
  public boolean add()
  {
    boolean success = false;

    String ssn=this.getSSN();
    //long id = this.getID();

    //check if entity already exists in <entity>.txt
    //Entity entityToAdd = this.query().getByID(id).getFirst();
    Entity entityToAdd = this.query().getBySSN(ssn).getFirst();
    //not a duplicate in  db
    if(entityToAdd == null)
    {
      //write to <entity>.txt
      this.writeToTextFile();
      success = true;
    }

    return success;
  }//end of add()


  
    //update entity (visible api call)
  public boolean update()
  {
    boolean success = false;

    long id = this.getID();
    
    //check if newEntity already exists in <entity>.txt
    Entity entityToUpdate = this.query().getByID(id).getFirst();

    if(entityToUpdate != null)
    {
      //delete the old (same id)
      this.deleteFromTextFile();
      //write the updated entity to text file
      this.writeToTextFile();
      success = true;
    }
    
    return success;
  }//end of update()


  
  //delete entity (visible api call)
  public boolean delete()
  {
    boolean success = false;

      long id = this.getID();
    
      //check if entity exists in <entity>.txt
      Entity entityToDelete = this.query().getByID(id).getFirst();

      if(entityToDelete != null)
      {
        this.deleteFromTextFile();
        success = true;
      }
    
    return success;
  }//end of deleteByID
  
  
}//end of Entity class
