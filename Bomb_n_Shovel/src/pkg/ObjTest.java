package pkg;

import pkg.engine.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;

public class ObjTest extends GameObject
{
  double frame=0;
  double ang=0;
  
  Line line = new Line();
  Line line1 = new Line();
  Circle rect = new Circle();
  Rectangle bkg = new Rectangle();
  
  Sprite sprtest = new Sprite(Spr.animation); 
         
  public ObjTest(double x_arg,double y_arg)
  {
    super(x_arg,y_arg);
    objIndex.add(ObjCntrl.oid.objTest);
  }
  
  @Override
  public void DRAW()
  {
    
    x=Math.cos(Game.currentTime)*32;
    y=Math.sin(Game.currentTime)*32;
    DrawCntrl.setColor(Color.rgb(200,200,200));
    DrawCntrl.setDepth(1);
    DrawCntrl.drawRectangle(bkg,0,0,Game.scr_w,Game.scr_h,false);
    
    DrawCntrl.setDepth(0);
    DrawCntrl.setColor(Color.rgb(32,64,32));
    //DrawCntrl.drawLine(line,0,0,x,y);
    DrawCntrl.drawLine(line1,0,0,100,400);
    frame+=0.3;
    if (frame>=8)
    {frame-=8;}
    
    //if (InputCntrl.mbCheck)
    {DrawCntrl.drawSprite(sprtest,frame,0,0,Math.sin(Game.currentTime),Math.cos(Game.currentTime),Game.currentTime*30);}
    DrawCntrl.setColor(Color.rgb(32,32,64));
    
    
    
    DrawCntrl.setDepth(-1);
    Polyline poly = new Polyline();
    DrawCntrl.drawPolyBegin(poly,true);
    DrawCntrl.polyAddPoint(0,100);
    DrawCntrl.polyAddPoint(32,32);
    DrawCntrl.polyAddPoint(48,80);
    DrawCntrl.polyAddPoint(100,100);
    DrawCntrl.drawPolyEnd();
    
    DrawCntrl.drawCircle(rect,InputCntrl.mouse_x,InputCntrl.mouse_y,4,false);
    
    GameWorld.cameraSetRotate(Game.currentTime*30);
    ang+=1;
    if (ang>360)
    {ang-=360;}
    double camX=Mathe.lsin(128,ang);
    
    GameWorld.cameraSetPosition(camX,0);
    
    double scale = Mathe.lsin(0.5,Game.currentTime*40)+0.5;
    GameWorld.cameraSetScale(scale,scale);
    
    //DrawCntrl.drawCircle(rect,Game.scr_w/2+camX,Game.scr_h/2+0,4,false);
    

  }
  
  @Override
  public void DRAW_GUI()
  {
    DrawCntrl.setDepth(10000);
    DrawCntrl.setColor(Color.rgb(0,0,0));
    DrawCntrl.setAlpha(1);
    DrawCntrl.drawLine(line,100,100,100+x*2,100+y*2);
  }
  
}

