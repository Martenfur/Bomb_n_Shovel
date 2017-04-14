package pkg.pathfinder;

public class PathCell
{
  public int x,y,    //Cell coordinates.
             dist_f, //Distance to finish point.
             dist_s; //Distance to start point. NOTE: this is not a closest distance. 
  
  PathCell[] surrounders;
  
  
  PathCell(int x_arg,int y_arg,int ds_arg,int df_arg)
  {
    x=         x_arg;
    y=         y_arg;
    dist_s=   ds_arg;
    dist_f=   df_arg;
    
    surrounders=new PathCell[4];
  }
  
  int getValue()
  {return dist_s+dist_f;}
  
}


