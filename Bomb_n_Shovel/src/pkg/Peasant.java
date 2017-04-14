package pkg;

import java.util.ArrayList;
import pkg.engine.*;
import static pkg.engine.GameWorld.field;
import pkg.pathfinder.*;

public class Peasant extends GameObject
{
  ArrayList<PathPoint> pathList;
  
  Field f;
  int team;
  
  Sprite spr;
  
  public Peasant(double x_arg,double y_arg)
  {
    super(x_arg,y_arg);
    objIndex.add(ObjCntrl.oid.peasant);
    
    team=0;
    
    spr=new Sprite(Spr.peasant);
  }
  
  @Override 
  public void STEP()
  {
    if (InputCntrl.mbCheckPress)
    {
      int cx,cy;
      cx=(int)Math.floor(InputCntrl.mouse_x/field.cellSize);
      cy=(int)Math.floor(InputCntrl.mouse_y/field.cellSize);
      if (field.field[cx][cy]==1)
      {field.field[cx][cy]=0;}
      else
      {
        Pathfinder pathfinder=new Pathfinder(field.field,field.field_w,field.field_h);
        int fx=(int)InputCntrl.mouse_x/field.cellSize;
        int fy=(int)InputCntrl.mouse_y/field.cellSize;
        pathList=pathfinder.pathFind((int)x/32,(int)y/32,fx,fy);
      }
    }
  }
  
  @Override 
  public void DRAW()
  {
    DrawCntrl.setDepth((int)-y);
    DrawCntrl.drawSprite(spr,x+16,y+16);
    
    if (pathList!=null)
    {
      for(int i=0; i<pathList.size(); i+=1)
      {DrawCntrl.drawSprite(new Sprite(Spr.path),pathList.get(i).side,pathList.get(i).x*field.cellSize,pathList.get(i).y*field.cellSize);}
    }
  }
}
