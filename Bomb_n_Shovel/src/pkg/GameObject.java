package pkg;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class GameObject
{
  public ArrayList<ObjCntrl.oid> objIndex = new ArrayList<ObjCntrl.oid>();
  
  GameObject()
  {
    ObjCntrl.objects.add(this);
    ObjCntrl.objectsAm+=1;
    
    objIndex.add(ObjCntrl.oid.all);
  }
  //EVENTS
  public void STEP_BEGIN() {}
  public void STEP()       {}
  public void STEP_END()   {}
  
  public void DRAW_BEGIN(GraphicsContext gc) {}
  public void DRAW(GraphicsContext gc)       {}
  public void DRAW_END(GraphicsContext gc)   {}
  //EVENTS
}

