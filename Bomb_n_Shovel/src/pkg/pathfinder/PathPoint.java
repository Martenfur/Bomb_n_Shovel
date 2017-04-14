package pkg.pathfinder;


public class PathPoint
{
  public int x,y,side;
  public PathPoint next;
  
  public PathPoint(int x_arg,int y_arg)
  {
    x=x_arg;
    y=y_arg;
  }
  
  public PathPoint(int x_arg,int y_arg,int side_arg)
  {
    x=x_arg;
    y=y_arg;
    side=side_arg;
  }
}
