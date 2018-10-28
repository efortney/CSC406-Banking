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
import java.util.List;



public class Entity
{
  //methods for polymorphism (these are called on subclasses of Entity)
  public long   getID(){ return 0; }
  public String getTextFileName(){ return ""; }
  public String getBackupTextFileName(){ return ""; }
  public String getDelimiter(){ return ", "; }


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
  public ArrayList<FieldNameTypeAndValue> getMemberFields() throws NoSuchFieldException,InstantiationException,IllegalAccessException
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
       //loop through this classes methods to find getter for this field
       for(Method method : this.getClass().getDeclaredMethods())
       { 
         //split on space and grab the 3rd element (method name)
         String methodHead = method.toString().split(" ")[2];
         if(methodHead.contains("get") && methodHead.contains(field.getName()))
         {
           try{
             field.setValue(method.invoke(this));
           }catch(InvocationTargetException e){ e.printStackTrace(); }
         }//end of if
       }//end of for
         
     }//end of for

    return fields;
  }//end of getMemberFields


  /*
   *   For generating record in the text file. (order matters!)
   *    Order must match member variable declaration.
   */
  public String toTextFileString()
  {
    try
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
    }catch(NoSuchFieldException | InstantiationException | IllegalAccessException e){ e.printStackTrace(); }
    return "";
  }//end of toTextFileString


  
  //line by line, reads, parses, casts and calls (parsing) constructor for each Entity
  public static ArrayList<String> readFromTextFile(String textFileName, String delimiter) throws NoSuchFieldException,InstantiationException,IllegalAccessException
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


  
}//end of Entity class
