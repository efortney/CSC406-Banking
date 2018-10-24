package database;

/*
 *  Used in <entity>.java when parsing <entity> objects
 * from a text file.
 *  FieldNameAndType is a wrapper for storing
 * and providing access to each of the calling class's 
 * member variables and respective type.
 */

public class FieldNameAndType
{
  String name;
  String type;

  public FieldNameAndType(String name, String type)
  {
    this.name = name;
    this.type = type;
  }

  public String toString(){ return name + " " + type; }

  public String getName(){ return name; }
  public String getType(){ return type; }
}//end of FieldNameAndType class
