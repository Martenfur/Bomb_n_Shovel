package pkg;


import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class GameWorld
{
  
  public static void CREATE()
  {
    ObjTest o1 = new ObjTest(32,32);
    ObjTest o2 = new ObjTest(64,64);
    ObjTest o3 = new ObjTest(96,96);
    ObjCntrl.objDestroy(o1);
    System.out.println(ObjCntrl.objIndexCmp(o1,ObjCntrl.oid.all));
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
    //DrawCntrl.UPDATE();
  }
}
