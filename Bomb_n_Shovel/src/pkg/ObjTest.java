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
    objIndex.add(Obj.oid.objTest);
    
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
    Draw.setColor(Color.rgb(200,200,200));
    Draw.setDepth(1);
    Draw.drawRectangle(bkg,0,0,Game.scr_w,Game.scr_h,false);
    
    Draw.setDepth(0);
    Draw.setColor(Color.rgb(32,64,32));
    //DrawCntrl.drawLine(line,0,0,x,y);
    Draw.drawLine(line1,0,0,100,400);
    Draw.setColor(Color.rgb(32,32,64));
    
    
    
    Draw.setDepth(-1);
    Polyline poly = new Polyline();
    Draw.drawPolyBegin(poly,true);
    Draw.polyAddPoint(0,100);
    Draw.polyAddPoint(32,32);
    Draw.polyAddPoint(48,80);
    Draw.polyAddPoint(100,100);
    Draw.drawPolyEnd();
    */
    deg+=0.1;
    double a=Mathe.lsin(1,deg);
    
    for(int i=0; i<sprAm; i+=1)
    {
      Draw.drawSprite(sprArray[i],0,Game.scr_w/2+i+a,Game.scr_h/2+32,i,i,i+deg*i,0.3);}
      GameWorld.cameraSetScale(0.5,0.5);
    }
  
  @Override
  public void DRAW_GUI()
  {
    Draw.setColor(Color.rgb(32,0,32));
    Draw.setAlpha(1);
    Draw.drawCircle(rect,Input.mouse_xgui,Input.mouse_ygui,4,false);
    GameWorld.cameraSetPosition((Input.mouse_xgui-Game.scr_w/2)*0.5,(Input.mouse_ygui-Game.scr_h/2)*0.5);
  
    frame+=0.3;
    if (frame>=8)
    {frame-=8;}
    Draw.drawSprite(sprtest,frame,0,0,1,1,0);
    
  }
  
}

