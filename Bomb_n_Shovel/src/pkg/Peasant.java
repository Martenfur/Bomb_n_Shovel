package pkg;

import pkg.net.Cmd;
import pkg.terrain.Terrain;
import pkg.turns.Player;
import pkg.engine.*;
import pkg.pathfinder.*;

public class Peasant extends GameObject
{
  public Player myPlayer=null;
  public Cmd command=null;
  
  public boolean initiative=false;
  public int tid=-1;
  
  public PathPoint pathList;
   
  //Movement.
  int target_x,target_y;
  public boolean moving=false;
  double x_prev,y_prev;
  double z;
  
  public int cx_prev,cy_prev;
  
  int tileStored=0;
  
  double moveSpd=        3,
         moveStaminaMax=10,
         moveStamina=    0;
      
  //Movement.
  
  Sprite spr;

  public Peasant(double x_arg,double y_arg)
  {
    super(x_arg,y_arg);
    objIndex.add(Obj.oid.peasant);
    
    spr=new Sprite(Spr.peasant);
  }
  
  @Override 
  public void STEP()
  {
    if (moving)
    { 
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
        
        moveStamina-=1;
        
        if (pathList==null || moveStamina==0)
        {
          moving=false;
          myPlayer.endTurn();
        }
      }   
    }
    
    
    //MOVING INPUTS/////////////////////////////////////////////
   
    if (command!=null)
    {
      if (command.cmp("move"))
      {
        Camera.viewer=this;
        
        x_prev=x;
        y_prev=y;
        moving=true;
      }
        
      if (command.cmp("setpath"))
      {
        int cx=(int)command.get(0),
            cy=(int)command.get(1);
        
        for(ObjIter it=new ObjIter(Obj.oid.peasant); it.end(); it.inc())
        {
          if (it.get()!=this)
          {((Peasant)it.get()).tileStore();}
        }
        
        Pathfinder pathfinder=new Pathfinder(Terrain.terrain);
        pathList=pathfinder.pathFind((int)x/Terrain.cellSize,(int)y/Terrain.cellSize,cx,cy);
        
        for(ObjIter it=new ObjIter(Obj.oid.peasant); it.end(); it.inc())
        {
          if (it.get()!=this)
          {((Peasant)it.get()).tileRestore();}
        }
        
        cx_prev=cx;
        cy_prev=cy;
      }
        
      command=null;
    }
     
    //MOVING INPUTS/////////////////////////////////////////////
  }
  
  @Override 
  public void DRAW()
  {
    Draw.setDepth((int)(-y));
    Draw.drawSprite(spr,tid,x+16,y+16+z);

    if (initiative && pathList!=null)
    {
      PathPoint p=pathList;
      
      int add,i=0;
      while(p!=null)
      {
        Draw.setDepth((int)-p.y*Terrain.cellSize+1);
        
        if (i<moveStamina)
        {add=0;}
        else
        {add=2;}
        i+=1;
        Draw.drawSprite(new Sprite(Spr.path),((p.next==null)?1:0)+add,p.x*Terrain.cellSize,p.y*Terrain.cellSize);
        p=p.next;
      }
    }
  }
  
  /**
   * Memorizes current tile and replaces it with unpassable wall.
   */
  private void tileStore()
  {
    int xx=(int)x/Terrain.cellSize,
        yy=(int)y/Terrain.cellSize;
    tileStored=Terrain.terrain[xx][yy];
    Terrain.terrain[xx][yy]=1;
  }
  
  /**
   * Restores back stored tile.
   */
  private void tileRestore()
  {
    int xx=(int)x/Terrain.cellSize,
        yy=(int)y/Terrain.cellSize;
    Terrain.terrain[xx][yy]=tileStored;
    tileStored=0;
  }
  
  /**
   * Restores stamina.
   */
  public void staminaRefill()
  {moveStamina=moveStaminaMax;}
}


