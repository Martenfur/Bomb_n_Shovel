package pkg.engine;

import pkg.*;

public class GameWorld
{
  
  public static void CREATE()
  {
    Mathe.randomize(); 
    InputCntrl.CREATE();
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
  
  public static void UPDATE()
  { 
    InputCntrl.UPDATE();
    ObjCntrl.UPDATE();
    DrawCntrl.UPDATE();
  }
  
  public static void cameraSetPosition(double x,double y)
  {
    Game.appsurf.setTranslateX(x);
    Game.appsurf.setTranslateY(y);
  }
  
  public static double cameraGet_x()
  {return -Game.appsurf.getTranslateX();}
  
  public static double cameraGet_y()
  {return -Game.appsurf.getTranslateY();}
}
