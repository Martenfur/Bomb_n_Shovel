
package pkg.engine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class Game extends Application
{
  static int gameSpeed=                30;
  private final double timeMul=1000000000.0;
  public static double currentTime=     0;
  public static boolean create=      true;
  public static Pane root,       //Root surface.
                     gui,        //For gui layer.
                     rotatesurf, //For rotating appsurf.
                     appsurf;    //For all the graphics.
  
  public static int scr_w=1000;
  public static int scr_h=800;
  
  public static boolean inFocus;
  
  @Override
  public void start(Stage primaryStage)
  {
    final long timeStart=System.nanoTime();
    long timePrev=timeStart;
    
    root       = new Pane();
    gui        = new Pane();
    rotatesurf = new Pane();
    appsurf    = new Pane();
    
    gui.setMinSize(scr_w,scr_h);
    gui.setMaxSize(scr_w,scr_h);
    rotatesurf.setMinSize(scr_w,scr_h);
    rotatesurf.setMaxSize(scr_w,scr_h);
    appsurf.setMinSize(scr_w,scr_h);
    appsurf.setMaxSize(scr_w,scr_h);
    
    Canvas canvas=new Canvas(scr_w,scr_h);    
    Scene scene=new Scene(root,scr_w,scr_h);
    
    primaryStage.setScene(scene);
    primaryStage.setTitle("Bomb n' Shovel.");
    
    root.getChildren().add(canvas);
    rotatesurf.getChildren().add(appsurf);
    root.getChildren().add(rotatesurf);
    root.getChildren().add(gui);
    
    primaryStage.show();
    
    new AnimationTimer()
    {      
      @Override
      public void handle(long timeCur)
      {
        if (Game.create)
        {
          GameWorld.CREATE();
          Game.create=false;  
        }
        
        long timePrev=System.nanoTime();    
        currentTime=(System.nanoTime()-timeStart)/timeMul;
        
        inFocus=root.getScene().getWindow().focusedProperty().get();
        
        GameWorld.UPDATE();
        
        //WAITING.
        while((System.nanoTime()-timePrev)/timeMul < 1/gameSpeed){}
        //WAITING.
      }
    }.start();
    
    //Closing additional thread;
    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
    {
      @Override
      public void handle(WindowEvent event) 
      {
        System.out.println("Sup^^");
        if (GameWorld.client!=null)
        {GameWorld.client.reader.running=false;}
      }
    });
    //Closing additional thread.
    
    
  }

  public static void main(String[] args)
  {launch(args);}
  
}

