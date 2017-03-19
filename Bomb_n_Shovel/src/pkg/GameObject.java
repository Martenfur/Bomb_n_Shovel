package pkg;

import pkg.engine.*;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class GameObject
{
  double x;
  double y;
  public ArrayList<ObjCntrl.oid> objIndex = new ArrayList<>();
  
  public GameObject(double x_arg,double y_arg)
  {
    x=x_arg;
    y=y_arg;
    ObjCntrl.objects.add(this);
    ObjCntrl.objectsAm+=1;
    
    objIndex.add(ObjCntrl.oid.all);
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

