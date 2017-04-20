package pkg.engine;

import java.util.ArrayList;
import pkg.GameObject;

public class Obj
{
  public static ArrayList<GameObject> objects = new ArrayList<>();
  public static double objectsAm=0;
  
  public static enum oid
  {
    all,
    objTest,
    field,
    peasant
  }
  
  /*
  Performs events for all objects.
  */
  public static void UPDATE()
  {
    Draw.drawFlag=Draw.df.DEFAULT;
    for(int i=0; i<objectsAm; i+=1)
    {objects.get(i).STEP_BEGIN();}
    for(int i=0; i<objectsAm; i+=1)
    {objects.get(i).STEP();}
    for(int i=0; i<objectsAm; i+=1)
    {objects.get(i).STEP_END();}
    
    Draw.drawFlag=Draw.df.BEGIN;
    for(int i=0; i<objectsAm; i+=1)
    {objects.get(i).DRAW_BEGIN();}
    Draw.drawFlag=Draw.df.DEFAULT;
    for(int i=0; i<objectsAm; i+=1)
    {objects.get(i).DRAW();}
    Draw.drawFlag=Draw.df.END;
    for(int i=0; i<objectsAm; i+=1)
    {objects.get(i).DRAW_END();}
    Draw.drawFlag=Draw.df.GUI;
    for(int i=0; i<objectsAm; i+=1)
    {objects.get(i).DRAW_GUI();}
  }
  
  ////////////////////////////////////////////////
  public static void objDestroy(GameObject obj)
  {
    objects.remove(obj);
    objectsAm-=1;
  }
  ////////////////////////////////////////////////
  
  
  ////////////////////////////////////////////////
  public static int objCount(oid id)
  {
    int counter=0;
    for(int i=0; i<objectsAm; i+=1)
    {
      if (objIndexGet(objects.get(i))==id)
      {counter+=1;}
    }
    return counter;
  }
  ////////////////////////////////////////////////
  
  
  ////////////////////////////////////////////////
  public static oid objIndexGet(GameObject obj)
  {return obj.objIndex.get(obj.objIndex.size()-1);}
  ////////////////////////////////////////////////
  
  
  ////////////////////////////////////////////////
  public static boolean objIndexCmp(GameObject obj,oid id)
  {  
    int oidSize=obj.objIndex.size();
    for(int i=oidSize-1; i>=0; i-=1)
    {
      if (obj.objIndex.get(i)==id)
      {return true;}
    }
    
    return false;
  }
  ////////////////////////////////////////////////
}
