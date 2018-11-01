package database;

/*
 *  Used in <entity>.java when parsing <entity> objects
 * from a text file.
 *  FieldNameAndType is a wrapper for storing
 * and providing access to each of the calling class's 
 * member variables and respective type.
 */

public class FieldNameTypeAndValue
{
  String name;
  String type;
  Object value;

  public FieldNameTypeAndValue(String name, String type)
  {
    this.name = name;
    this.type = type;
  }

  public FieldNameTypeAndValue(String name, String type, Object value)
  {
    this.name = name;
    this.type = type;
    this.value = value;
  }

  public String toString(){ return name + " " + type + " " + value; }

  public String getName(){ return name; }
  public String getType(){ return type; }
  public Object getValue(){ return value; }

  public void setName(String name){ this.name = name; }
  public void setType(String type){ this.type = type; }
  public void setValue(Object value){ this.value = value; }
}//end of FieldNameAndType class
