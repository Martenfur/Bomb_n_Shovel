package pkg;

import javafx.scene.paint.Color;
import pkg.engine.*;
import javafx.scene.shape.*;

public class Field extends GameObject
{
  public int[][] field;
  public int field_w,field_h,cellSize;
  
  double cam_mx,cam_my,cam_cx,cam_cy;
  
  Circle cursor; 
  Rectangle[][] rectArray;
  
  public Field(double x_arg,double y_arg)
  {
    super(x_arg,y_arg);
    objIndex.add(ObjCntrl.oid.field);
    
    field_w=128;
    field_h=128;
    cellSize=32;
    
    field=new int[field_w][field_h];
    
    for(int i=0; i<field_w; i+=1)
    {
      for(int k=0; k<field_h; k+=1)
      {field[i][k]=Mathe.irandom(1);}
    }
    
    cursor = new Circle();
    rectArray = new Rectangle[128][128];
    for(int i=0; i<128; i+=1) 
    {
      for(int k=0; k<128; k+=1)
      {rectArray[i][k]=new Rectangle();}
    }
  }
  
  @Override
  public void STEP()
  {
    GameWorld.cameraSetScale(1+Mathe.lsin(0.5,Game.currentTime*5),1+Mathe.lsin(0.5,Game.currentTime*5));
    
    if (InputCntrl.mbCheckPress)
    {
      cam_mx=InputCntrl.mouse_x-GameWorld.cameraGet_x();
      cam_my=InputCntrl.mouse_y-GameWorld.cameraGet_y();
      cam_cx=GameWorld.cameraGet_x();
      cam_cy=GameWorld.cameraGet_y();
    }
    
    if (InputCntrl.mbCheck)
    {
      GameWorld.cameraSetPosition(cam_cx-(InputCntrl.mouse_x-GameWorld.cameraGet_x())+cam_mx,
                                  cam_cy-(InputCntrl.mouse_y-GameWorld.cameraGet_y())+cam_my);
    }
    
    //GameWorld.cameraSetPosition(0,0);
  
  }
  
  @Override
  public void DRAW()
  { 
    
    DrawCntrl.setColor(Color.BROWN);
    
    int draw_xstart=(int)Math.max(0,      Math.floor(GameWorld.cameraGet_x()            /cellSize));
    int draw_ystart=(int)Math.max(0,      Math.floor(GameWorld.cameraGet_y()            /cellSize));
    int draw_xend=  (int)Math.min(field_w,Math.ceil((GameWorld.cameraGet_x()+Game.scr_w/GameWorld.cameraGetScale_x())/cellSize));
    int draw_yend=  (int)Math.min(field_w,Math.ceil((GameWorld.cameraGet_y()+Game.scr_h/GameWorld.cameraGetScale_y())/cellSize));
    
    for(int i=draw_xstart; i<draw_xend; i+=1)
    {
      for(int k=draw_ystart; k<draw_yend; k+=1)
      {
        if (field[i][k]==1)
        {
          DrawCntrl.drawRectangle(rectArray[i][k],i*cellSize,k*cellSize,(i+1)*cellSize,(k+1)*cellSize,false);
        }
      }
    }
   
    DrawCntrl.setColor(Color.AQUAMARINE);
    DrawCntrl.drawCircle(cursor,InputCntrl.mouse_x,InputCntrl.mouse_y,8,false);
    
    
  }
  
}
