package pkg.engine;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

/**
 * Controls mouse input.
 */
public class Input
{
  public static double mouse_x,mouse_y,mouse_xgui,mouse_ygui;
  public static boolean mbCheck,mbCheckPress,mbCheckRelease;
  
  static double mouse_x_read,mouse_y_read;
  static boolean mbCheck_read,mbCheckPress_read,mbCheckRelease_read;
  
  /**
   * Initializes some mouse events.
   */
  public static void CREATE()
  {
    Game.root.addEventFilter(MouseEvent.ANY,new EventHandler<MouseEvent>() 
    {
      @Override
      public void handle(MouseEvent event) 
      {
        mouse_x_read=event.getSceneX();
        mouse_y_read=event.getSceneY();
      }
    });
    
    Game.root.setOnMousePressed(new EventHandler<MouseEvent>() 
    {
      @Override
      public void handle(MouseEvent event) 
      {
        mbCheck_read=event.isPrimaryButtonDown();
        if (mbCheck_read)
        {mbCheckPress_read=true;}
      }
    });
    
    Game.root.setOnMouseReleased(new EventHandler<MouseEvent>() 
    {
      @Override
      public void handle(MouseEvent event) 
      {
        mbCheckRelease_read=true;
        mbCheck_read=false;   
      }
    });
    
  }    
  
  /**
   * Updates mouse. Don't use yourself.
   */
  public static void UPDATE()
  {
    if (Game.inFocus)
    {
      mouse_x=mouse_x_read/Game.appsurf.getScaleX()+GameWorld.cameraGet_x();
      mouse_y=mouse_y_read/Game.appsurf.getScaleY()+GameWorld.cameraGet_y();
      
      mouse_xgui=mouse_x_read;
      mouse_ygui=mouse_y_read;
      
      mbCheck=mbCheck_read;
      mbCheckPress=mbCheckPress_read;
      mbCheckRelease=mbCheckRelease_read;
    }
    else
    {mouseClear();}
    
    //Clearing values.
    mbCheckPress_read=  false;
    mbCheckRelease_read=false;
    //Clearing values.
  }
  
  /**
   * Clears mouse button states for current step.
   */
  public static void mouseClear()
  {
    mbCheck=       false;
    mbCheckPress=  false;
    mbCheckRelease=false;
  }
  
}
