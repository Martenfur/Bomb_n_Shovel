package pkg;

import javafx.scene.canvas.GraphicsContext;

public class ObjTest extends GameObject
{
  int x=0;
  int y=0;
  
  
  
  ObjTest(int x_arg,int y_arg)
  {
    super();
    objIndex.add(ObjCntrl.oid.objTest);
    
    x=x_arg;
    y=y_arg;
  }
  
  @Override
  public void DRAW(GraphicsContext gc)
  {
    gc.strokeOval(x,y,16,16);
  }
}

