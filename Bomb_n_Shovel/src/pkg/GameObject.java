package pkg;

import pkg.engine.*;
import java.util.ArrayList;

/**
 * Parent class for every engine object.
 * Provides some basic interfaces for correct fps-mounting.
 */
public class GameObject
{
  public double x,y;
  public ArrayList<Obj.oid> objIndex=new ArrayList<>();
  
  /**
   * Main constructor. Add new object to main object array. 
   * @param x_arg x
   * @param y_arg y
   */
  public GameObject(double x_arg,double y_arg)
  {
    x=x_arg;
    y=y_arg;
    Obj.objects.add(this);
    Obj.objectsAm+=1;
    
    objIndex.add(Obj.oid.all);
  }
  
  //EVENTS
  public void STEP_BEGIN(){}
  public void STEP()      {}
  public void STEP_END()  {}
  
  public void DRAW_BEGIN(){}
  public void DRAW()      {}
  public void DRAW_END()  {}
  
  public void DRAW_GUI()  {}
  //EVENTS
}

