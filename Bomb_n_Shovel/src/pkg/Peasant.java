package pkg;

import pkg.engine.*;
import static pkg.engine.GameWorld.field;
import pkg.pathfinder.*;

public class Peasant extends GameObject
{
  PathPoint pathList;
   
  int target_x,target_y;
  boolean moving=false;
  double prev_x,prev_y;
  double z;
  
  int team;
  
  Sprite spr;
  
  public Peasant(double x_arg,double y_arg)
  {
    super(x_arg,y_arg);
    objIndex.add(Obj.oid.peasant);
    
    team=0;
    
    spr=new Sprite(Spr.peasant);
  }
  
  @Override 
  public void STEP()
  {
    if (moving)
    { 
    
      double moveSpd=3;
      
      x-=Math.signum(x-pathList.x*field.cellSize)*moveSpd;
      y-=Math.signum(y-pathList.y*field.cellSize)*moveSpd;
      z=Mathe.lsin(8,180*(Mathe.lerp(prev_x,x,pathList.x*field.cellSize)+Mathe.lerp(prev_y,y,pathList.y*field.cellSize)));
      
      if (Mathe.pointDistance(x,y,pathList.x*field.cellSize,pathList.y*field.cellSize)<moveSpd)
      {
        x=pathList.x*field.cellSize;
        y=pathList.y*field.cellSize;
        prev_x=x;
        prev_y=y;
        z=0;
        pathList=pathList.next;
        if (pathList==null)
        {moving=false;}
      }

      
    }
    
    
    //MOVING INPUTS/////////////////////////////////////////////
    if (Input.mbCheckPress && !moving)
    {
      int cx,cy;
      cx=(int)Input.mouse_x/field.cellSize;
      cy=(int)Input.mouse_y/field.cellSize;
 
      //System.out.println(field.terrain[cx][cy]+" : "+field.terrainTile[cx][cy]);
      
      //if (field.terrain[cx][cy]==1)
      //{field.terrain[cx][cy]=0;}
      //else
      {
        //Pathfinding.
        if (pathList!=null)
        {
          PathPoint last=pathList.last();
          
          if (last.x==cx && last.y==cy)
          {
            prev_x=x;
            prev_y=y;
            moving=true;
          }
        }
        
        if (!moving)
        {
          Pathfinder pathfinder=new Pathfinder(field.terrain);
          pathList=pathfinder.pathFind((int)x/32,(int)y/32,cx,cy);
        }
        //Pathfinding.
      }
    }
    //MOVING INPUTS/////////////////////////////////////////////
  }
  
  @Override 
  public void DRAW()
  {
    Draw.setDepth((int)(-y));
    Draw.drawSprite(spr,x+16,y+16+z);
    Draw.setDepth((int)(0));

    if (pathList!=null)
    {
      PathPoint p=pathList;
      
      while(p!=null)
      {
        Draw.drawSprite(new Sprite(Spr.path),(p.next==null)?1:0,p.x*field.cellSize,p.y*field.cellSize);
        p=p.next;
      }
    }
  }
}
