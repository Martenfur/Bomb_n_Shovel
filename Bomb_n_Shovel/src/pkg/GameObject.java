package pkg;

import pkg.engine.*;
import java.util.ArrayList;

public class GameObject
{
  double x;
  double y;
  public ArrayList<Obj.oid> objIndex = new ArrayList<>();
  
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

