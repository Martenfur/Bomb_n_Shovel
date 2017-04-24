package pkg;

import pkg.turns.Player;
import pkg.engine.*;
import pkg.pathfinder.*;
import pkg.turns.PlayerCmd;

public class Peasant extends GameObject
{
  public Player myPlayer=null;
  public int tid=-1;
  public boolean initiative=false;
  public PlayerCmd command;
  
  public PathPoint pathList;
   
  int target_x,target_y;
  public boolean moving=false;
  double x_prev,y_prev;
  double z;
  
  public int team;
  
  Sprite spr;
  
  public int cx_prev,cy_prev;
  
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
      
      x-=Math.signum(x-pathList.x*Terrain.cellSize)*moveSpd;
      y-=Math.signum(y-pathList.y*Terrain.cellSize)*moveSpd;
      z=Mathe.lsin(8,180*(Mathe.lerp(x_prev,x,pathList.x*Terrain.cellSize)+Mathe.lerp(y_prev,y,pathList.y*Terrain.cellSize)));
      
      if (Mathe.pointDistance(x,y,pathList.x*Terrain.cellSize,pathList.y*Terrain.cellSize)<moveSpd)
      {
        x=pathList.x*Terrain.cellSize;
        y=pathList.y*Terrain.cellSize;
        x_prev=x;
        y_prev=y;
        z=0;
        pathList=pathList.next;
        if (pathList==null)
        {
          moving=false;
          myPlayer.endTurn();
        }
      }

      
    }
    
    
    //MOVING INPUTS/////////////////////////////////////////////
    //if (initiative && !moving && Input.mbCheckRelease && !Terrain.camMove)
    //{
      //int cx,cy;
      //cx=(int)Input.mouse_x/Terrain.cellSize;
      //cy=(int)Input.mouse_y/Terrain.cellSize;
 
 
      if (command!=null)
      {
        if (command.get()==PlayerCmd.cmd.move)
        {
          x_prev=x;
          y_prev=y;
          moving=true;
        }
        
        if (command.get()==PlayerCmd.cmd.setpath)
        {
          int cx=(int)command.getarg(0),
              cy=(int)command.getarg(1);
          
          Pathfinder pathfinder=new Pathfinder(Terrain.terrain);
          pathList=pathfinder.pathFind((int)x/Terrain.cellSize,(int)y/Terrain.cellSize,cx,cy);
          cx_prev=cx;
          cy_prev=cy;
        }
        
        command=null;
      }
      
      /*
      //Pathfinding.
      if (pathList!=null)
      {   
        if (cx_prev==cx && cy_prev==cy)
        {
          x_prev=x;
          y_prev=y;
          moving=true;
        }
      }
        
      if (!moving)
      {
        Pathfinder pathfinder=new Pathfinder(Terrain.terrain);
        pathList=pathfinder.pathFind((int)x/32,(int)y/32,cx,cy);
        cx_prev=cx;
        cy_prev=cy;
      }
      //Pathfinding.
      */
    //}
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
        Draw.drawSprite(new Sprite(Spr.path),(p.next==null)?1:0,p.x*Terrain.cellSize,p.y*Terrain.cellSize);
        p=p.next;
      }
    }
  }
}
