
package pkg.engine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;

import javafx.animation.AnimationTimer;

public class Game extends Application
{
  static int gameSpeed=30;
  private final double timeMul=1000000000.0;
  public static GraphicsContext gc;
  public static double currentTime=0;
  public static boolean create=true;
  public static Pane root;
  
  public static int scr_w=640;
  public static int scr_h=480;
  
  @Override
  public void start(Stage primaryStage)
  {
    
    final long timeStart=System.nanoTime();
    long timePrev=timeStart;
    
    
    
    root = new Pane();
    Canvas canvas = new Canvas(scr_w,scr_h);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    primaryStage.setTitle("Love foxes or die.");
    Scene scene = new Scene(root,scr_w,scr_h);
    primaryStage.setScene(scene);
    root.getChildren().add(canvas);
    
    //gc.rotate(45);
    
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
        
        GameWorld.UPDATE(gc);
        
        //WAITING.
        while((System.nanoTime()-timePrev)/timeMul < 1/gameSpeed){}
        //WAITING.
        
        //timePrev=System.nanoTime();
        
      }
    }.start();
    
    
  }

  public static void main(String[] args)
  {
    launch(args);
  }
  
}

