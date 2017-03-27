package pkg;

import pkg.engine.*;

import javafx.scene.shape.*;
import javafx.scene.paint.Color;

public class ObjTest extends GameObject
{
  double frame=0;
  double ang=0;
  Sprite[] sprArray;
  Sprite animation;
  int sprAm=50;
  double deg=0;
  
  Line line = new Line();
  Line line1 = new Line();
  Circle rect = new Circle();
  Rectangle bkg = new Rectangle();
  
  Sprite sprtest = new Sprite(Spr.animation); 
         
  public ObjTest(double x_arg,double y_arg)
  {
    super(x_arg,y_arg);
    objIndex.add(ObjCntrl.oid.objTest);
    
    animation=new Sprite(Spr.animation);
    sprArray = new Sprite[sprAm];
    for(int i=0; i<sprAm; i+=1)
    {sprArray[i]=new Sprite(Spr.kitten);}
  }
  
  @Override
  public void DRAW()
  {
    /*
    x=Math.cos(Game.currentTime)*32;
    y=Math.sin(Game.currentTime)*32;
    DrawCntrl.setColor(Color.rgb(200,200,200));
    DrawCntrl.setDepth(1);
    DrawCntrl.drawRectangle(bkg,0,0,Game.scr_w,Game.scr_h,false);
    
    DrawCntrl.setDepth(0);
    DrawCntrl.setColor(Color.rgb(32,64,32));
    //DrawCntrl.drawLine(line,0,0,x,y);
    DrawCntrl.drawLine(line1,0,0,100,400);
    DrawCntrl.setColor(Color.rgb(32,32,64));
    
    
    
    DrawCntrl.setDepth(-1);
    Polyline poly = new Polyline();
    DrawCntrl.drawPolyBegin(poly,true);
    DrawCntrl.polyAddPoint(0,100);
    DrawCntrl.polyAddPoint(32,32);
    DrawCntrl.polyAddPoint(48,80);
    DrawCntrl.polyAddPoint(100,100);
    DrawCntrl.drawPolyEnd();
    */
    deg+=0.1;
    double a=Mathe.lsin(1,deg);
    
    for(int i=0; i<sprAm; i+=1)
    {
      DrawCntrl.drawSprite(sprArray[i],0,Game.scr_w/2+i+a,Game.scr_h/2+32,i,i,i+deg*i,0.3);}
      GameWorld.cameraSetScale(0.5,0.5);
    }
  
  @Override
  public void DRAW_GUI()
  {
    DrawCntrl.setColor(Color.rgb(32,0,32));
    DrawCntrl.setAlpha(1);
    DrawCntrl.drawCircle(rect,InputCntrl.mouse_xgui,InputCntrl.mouse_ygui,4,false);
    GameWorld.cameraSetPosition((InputCntrl.mouse_xgui-Game.scr_w/2)*0.5,(InputCntrl.mouse_ygui-Game.scr_h/2)*0.5);
  
    frame+=0.3;
    if (frame>=8)
    {frame-=8;}
    DrawCntrl.drawSprite(sprtest,frame,0,0,1,1,0);
    
  }
  
}

