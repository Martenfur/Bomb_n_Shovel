package pkg.engine;

import java.util.ArrayList;
import pkg.GameObject;

/**
 * Object controller. All the magic happens here.
 */
public class Obj
{
  public static ArrayList<GameObject> objects = new ArrayList<>();
  public static double objectsAm=0;
  
  /**
   * This list determines object types. 
   * One object can have many.
   */
  public static enum oid
  {
    all,
    objTest,
    terrain,
    peasant,
    paper
  }
  
  /**
   * Performs events for all game objects.
   */
  public static void UPDATE()
  {
    Draw.drawFlag=Draw.df.GUI;
    for(int i=0; i<objectsAm; i+=1)
    {objects.get(i).DRAW_GUI();}
    
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
  }
  
  ////////////////////////////////////////////////
  /**
   * Removes object with given index from object list.
   * @param obj 
   */
  public static void objDestroy(GameObject obj)
  {
    objects.remove(obj);
    objectsAm-=1;
  }
  ////////////////////////////////////////////////
  
  
  ////////////////////////////////////////////////
  /**
   * Counts objects of given type.
   * @param id
   * @return 
   */
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
  /**
   * Retrieves top object index.
   * @param obj
   * @return 
   */
  public static oid objIndexGet(GameObject obj)
  {return obj.objIndex.get(obj.objIndex.size()-1);}
  ////////////////////////////////////////////////
  
  
  ////////////////////////////////////////////////
  /**
   * Compares object with object index.
   * @param obj
   * @param id
   * @return 
   */
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
