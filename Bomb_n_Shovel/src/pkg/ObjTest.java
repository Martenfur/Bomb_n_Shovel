package pkg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;

public class ObjTest extends GameObject
{
  Line line = new Line();
  Line line1 = new Line();
  Circle rect = new Circle();
  Rectangle bkg = new Rectangle();
  
  ObjTest(double x_arg,double y_arg)
  {
    super(x_arg,y_arg);
    objIndex.add(ObjCntrl.oid.objTest);
  }
  
  @Override
  public void DRAW(GraphicsContext gc)
  {
    x=Math.cos(Game.currentTime)*32;
    y=Math.sin(Game.currentTime)*32;
    DrawCntrl.setColor(Color.rgb(208,137,255));
    DrawCntrl.drawRectangle(bkg,0,0,Game.scr_w,Game.scr_h,false);
    
    DrawCntrl.setColor(Color.rgb(32,32,64));
    DrawCntrl.setAlpha(0.5);
    DrawCntrl.drawCircle(rect,0,0,300,false);
    DrawCntrl.setColor(Color.rgb(32,64,32));
    DrawCntrl.setAlpha(1);
    DrawCntrl.drawLine(line,0,0,x,y);
    DrawCntrl.drawLine(line1,0,0,100,400);
    
    
    Polyline poly = new Polyline();
    DrawCntrl.drawPolyBegin(poly,true);
    DrawCntrl.polyAddPoint(0,100);
    DrawCntrl.polyAddPoint(32,32);
    DrawCntrl.polyAddPoint(48,80);
    DrawCntrl.polyAddPoint(100,100);
    DrawCntrl.drawPolyEnd();
    
  }
  
  
}

