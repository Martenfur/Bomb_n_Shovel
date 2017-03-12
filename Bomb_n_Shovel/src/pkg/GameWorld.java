package pkg;


import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class GameWorld
{
  
  public static void CREATE()
  {
    DrawCntrl.CREATE();
    
    ObjTest o1 = new ObjTest(32.0,32.0);
    GameObject o2 = new GameObject(0.0,0.0);
    ObjTest o3 = new ObjTest(96.0,96.0);
    ObjCntrl.objDestroy(o1);
    //System.out.println(o1);
    
    for(ObjIter it = new ObjIter(ObjCntrl.oid.objTest); it.end(); it.inc())
    {
      System.out.println(it.get());
    }
  }
  
  public static void UPDATE(GraphicsContext gc)
  {
    gc.setFill(Color.rgb(128,128,128));
    gc.setStroke(Color.rgb(0,0,0,0.5));
    gc.setLineWidth(5);
    gc.fillRect(0,0,Game.scr_w,Game.scr_h);
    //gc.strokeOval(32,32,60+Math.sin(Game.currentTime)*30,30);
    //System.out.println(Game.currentTime);
    ObjCntrl.UPDATE(gc);
    DrawCntrl.UPDATE();
  }
}
