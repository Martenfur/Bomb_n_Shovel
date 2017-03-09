
package pkg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Line;

import javafx.animation.AnimationTimer;

public class Game extends Application
{
  static int gameSpeed=30;
  private double timeMul=1000000000.0;
  public static GraphicsContext gc;
  public static double currentTime=0;
  public static boolean create=true;
  
  public static int scr_w=640;
  public static int scr_h=480;
  
  @Override
  public void start(Stage primaryStage)
  {
    
    final long timeStart=System.nanoTime();
    long timePrev=timeStart;
    
    
    
    StackPane root = new StackPane();
    Canvas canvas = new Canvas(scr_w,scr_h);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    primaryStage.setTitle("Love foxes or die.");
    Scene scene = new Scene(root,scr_w,scr_h);
    primaryStage.setScene(scene);
    root.getChildren().add(canvas);
    
    //Line line = new Line(0,0,100,32);
    //gc.rotate(45);
    //root.getChildren().add(line);
    
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

