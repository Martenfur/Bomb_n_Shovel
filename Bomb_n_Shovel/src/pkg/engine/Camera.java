package pkg.engine;

import pkg.GameObject;

/**
 * Main camera. 
 */
public class Camera
{
  public static GameObject viewer;
  public static double view_w=0,view_h=0;
  
  /**
   * Updates smooth camera.
   */
  public static void UPDATE()
  {
    view_w=Game.scr_w/getScale_x();
    view_h=Game.scr_h/getScale_y();
    
    if (viewer!=null)
    {
      double cx=get_x()+view_w/2,
             cy=get_y()+view_h/2,
             vx=viewer.x,
             vy=viewer.y;
      
      double l=Mathe.pointDistance(cx,cy,vx,vy),
             d=Mathe.pointDirection(cx,cy,vx,vy);
      
      cx+=Mathe.lcos(l/4.0,d);
      cy+=Mathe.lsin(l/4.0,d);
      
      setPosition(cx-view_w/2,cy-view_h/2);
    }
  }
  
  /**
   * Sets x and y of the camera.
   * @param x
   * @param y 
   */
  public static void setPosition(double x,double y)
  {
    Game.appsurf.setTranslateX(Math.round(-x*Game.appsurf.getScaleX()+(Game.root.getWidth()*Game.appsurf.getScaleX()-Game.appsurf.getWidth())/2));
    Game.appsurf.setTranslateY(Math.round(-y*Game.appsurf.getScaleY()+(Game.root.getHeight()*Game.appsurf.getScaleY()-Game.appsurf.getHeight())/2));
  }
  
  /**
   * @return x of upper left camera's corner.
   */
  public static double get_x()
  {return -Game.appsurf.getTranslateX()/Game.appsurf.getScaleX()-(Game.root.getWidth()/Game.appsurf.getScaleX()-Game.appsurf.getWidth())/2;}
  
  
  /**
   * @return y of upper left camera's corner.
   */
  public static double get_y()
  {return -Game.appsurf.getTranslateY()/Game.appsurf.getScaleY()-(Game.root.getHeight()/Game.appsurf.getScaleY()-Game.appsurf.getHeight())/2;}
  
  /**
   * Sets scale.
   * @param xscale
   * @param yscale 
   */
  public static void setScale(double xscale,double yscale)
  {
    
    Game.appsurf.setTranslateX(Game.appsurf.getTranslateX()/Game.appsurf.getScaleX());
    Game.appsurf.setTranslateY(Game.appsurf.getTranslateY()/Game.appsurf.getScaleY());
    
    Game.appsurf.setScaleX(xscale);
    Game.appsurf.setScaleY(yscale);
    
    Game.appsurf.setTranslateX(Game.appsurf.getTranslateX()*xscale);
    Game.appsurf.setTranslateY(Game.appsurf.getTranslateY()*yscale);
  }
  
  /**
   * @return xscale
   */
  public static double getScale_x()
  {return Game.appsurf.getScaleX();}
  
  /**
   * @return yscale
   */
  public static double getScale_y()
  {return Game.appsurf.getScaleY();}
  
  /**
   * Sets rotation in degrees. 
   * !!!THIS WILL MESS UP MOUSE COORDINATES, SO BE CAREFUL!!!
   * @param rot 
   */
  public static void setRotate(double rot)
  {Game.rotatesurf.setRotate(rot);}
  
  /**
   * @return Camera angle in degrees.
   */
  public static double getRotate()
  {return Game.rotatesurf.getRotate();}
}
