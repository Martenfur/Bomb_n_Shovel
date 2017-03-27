package pkg.engine;

import pkg.*;

public class GameWorld
{
  
  public static void CREATE()
  {
    Mathe.randomize(); 
    InputCntrl.CREATE();
    DrawCntrl.CREATE();
    
    Field o1 = new Field(0,0);
    
    /*
    for(ObjIter it = new ObjIter(ObjCntrl.oid.objTest); it.end(); it.inc())
    {
      System.out.println(it.get());
    }
    */
  }
  
  public static void UPDATE()
  { 
    InputCntrl.UPDATE();
    ObjCntrl.UPDATE();
    DrawCntrl.UPDATE();
  }
  
  public static void cameraSetPosition(double x,double y)
  {
    Game.appsurf.setTranslateX(Math.round(-x*Game.appsurf.getScaleX()+(Game.root.getWidth()*Game.appsurf.getScaleX()-Game.appsurf.getWidth())/2));
    Game.appsurf.setTranslateY(Math.round(-y*Game.appsurf.getScaleY()+(Game.root.getHeight()*Game.appsurf.getScaleY()-Game.appsurf.getHeight())/2));
  }
  
  public static double cameraGet_x()
  {return -Game.appsurf.getTranslateX()/Game.appsurf.getScaleX()-(Game.root.getWidth()/Game.appsurf.getScaleX()-Game.appsurf.getWidth())/2;}
  
  public static double cameraGet_y()
  {return -Game.appsurf.getTranslateY()/Game.appsurf.getScaleY()-(Game.root.getHeight()/Game.appsurf.getScaleY()-Game.appsurf.getHeight())/2;}
  
  
  public static void cameraSetScale(double xscale,double yscale)
  {
    
    Game.appsurf.setTranslateX(Game.appsurf.getTranslateX()/Game.appsurf.getScaleX());
    Game.appsurf.setTranslateY(Game.appsurf.getTranslateY()/Game.appsurf.getScaleY());
    
    Game.appsurf.setScaleX(xscale);
    Game.appsurf.setScaleY(yscale);
    
    Game.appsurf.setTranslateX(Game.appsurf.getTranslateX()*xscale);
    Game.appsurf.setTranslateY(Game.appsurf.getTranslateY()*yscale);
  }
  
  public static double cameraGetScale_x()
  {return Game.appsurf.getScaleX();}
  
  public static double cameraGetScale_y()
  {return Game.appsurf.getScaleY();}
  
  public static void cameraSetRotate(double rot)
  {Game.rotatesurf.setRotate(rot);}
  
  public static double cameraGetRotate()
  {return Game.rotatesurf.getRotate();}
  
}
