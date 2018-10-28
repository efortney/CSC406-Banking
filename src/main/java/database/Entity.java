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

  
}//end of Entity class
