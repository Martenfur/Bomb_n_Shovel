package pkg.pathfinder;

import java.util.ArrayList;
import pkg.engine.*;

public class Pathfinder
{
  int[][] wallGrid;
  int grid_w,grid_h;
  
  
  
  public Pathfinder(int[][] grid_arg,int grid_w_arg,int grid_h_arg)
  {
    grid_w=grid_w_arg;
    grid_h=grid_h_arg;
     
    wallGrid=grid_arg;   
  }
  
  public PathPoint pathFind(int sx,int sy,int fx,int fy)//PathPoint ptStart,PathCell ptFinish)
  {
    ArrayList<PathCell> openList   = new ArrayList<>(); 
    ArrayList<PathCell> closedList = new ArrayList<>();  
    
    PathCell[][] pathGrid=new PathCell[grid_w][grid_h];
    pathGrid[sx][sy]=new PathCell(sx,sy,0,getDist(sx,sy,fx,fy));
    
    openList.add(pathGrid[sx][sy]);
    
    while(!openList.isEmpty())
    { 
      PathCell ptCheck=listFindLesser(openList);
      closedList.add(ptCheck);
      ///////////////////////////////////////////////////
      int px,py;
      for(int i=0; i<4; i+=1)
      {
        px=ptCheck.x+Mathe.rotate_x[i];
        py=ptCheck.y+Mathe.rotate_y[i];
        if (wallGrid[px][py]==0)
        {
          if (pathGrid[px][py]==null)
          {
            pathGrid[px][py]=new PathCell(px,py,ptCheck.dist_s+1,getDist(px,py,fx,fy));
            openList.add(pathGrid[px][py]);
          }
          else
          {
            if (pathGrid[px][py].dist_f>ptCheck.dist_f)
            {pathGrid[px][py].dist_f=ptCheck.dist_f;}
          }
          ptCheck.surrounders[i]=pathGrid[px][py];
        }
      }
      
      if (ptCheck.dist_f==0)
      {
        PathPoint pathBuf=pathConstruct(ptCheck);
        //pathBuf.add(fx,fy);
        return pathBuf;
      }
          
      ///////////////////////////////////////////////////

    }
    return null;
    
  }  
  
  PathPoint pathConstruct(PathCell ptStart)
  {
    ArrayList<PathCell> resList=new ArrayList<>();
    resList.add(ptStart);
    PathCell ptBuf=ptStart;
    
    while(true)
    {
      PathCell ptLesser=null;
      PathCell ptBufS;
      
      for(int i=0; i<4; i+=1)
      {
        ptBufS=ptBuf.surrounders[i];
        
        if (ptBufS!=null)
        {
          if (ptLesser==null)
          {ptLesser=ptBufS;}
          else
          {
            if (ptLesser.getValue()>ptBufS.getValue() || (ptLesser.getValue()==ptBufS.getValue() && ptLesser.dist_s>ptBufS.dist_s))
            {ptLesser=ptBufS;}
          }
        }  
      }
      
      if (ptLesser!=null)
      {
        resList.add(ptLesser);
        ptBuf=ptLesser;
        if (ptLesser.dist_s==0)
        {
          //Generating path.  
          PathCell cBuf=resList.get(resList.size()-2);
          PathPoint resPath=new PathPoint(cBuf.x,cBuf.y);
          
          for(int i=resList.size()-3; i>=0; i-=1)
          {
            cBuf=resList.get(i); 
            resPath.add(cBuf.x,cBuf.y);
          }
          
          return resPath;
          //Generating path.
        }
      }
      else
      {
        System.out.println("PATH CONSTRUCT ERROR");
        return null;
      }
    }
  }
  
  
  PathCell listFindLesser(ArrayList<PathCell> list)
  {
    if (list.isEmpty())
    {return null;}
    
    PathCell ptLesser=list.get(0);
    PathCell ptBuffer;
    int s=list.size();
    int iLesser=0;
    for(int i=1; i<s; i+=1)
    {
      ptBuffer=list.get(i);
      if (ptBuffer.getValue()<ptLesser.getValue())
      {
        ptLesser=ptBuffer;
        iLesser=i;
      }
    }    
    list.remove(iLesser);
    
    return ptLesser;
  }
   
  int getDist(int sx,int sy,int fx,int fy)
  {return Math.abs(sx-fx)+Math.abs(sy-fy);}
  
}