package pkg.pathfinder;


public class PathPoint
{
  public int x,y,side;
  public PathPoint next;
  
  public PathPoint(int x_arg,int y_arg)
  {
    x=x_arg;
    y=y_arg;
    next=null;
  }
  
  public PathPoint add(int x_arg,int y_arg)
  {
    PathPoint newpoint;
    if (next==null)
    {
      newpoint=new PathPoint(x_arg,y_arg);
      next=newpoint;
    }
    else
    {newpoint=next.add(x_arg,y_arg);}
    
    return newpoint;
  }
  
  public PathPoint last()
  {
    if (next==null)
    {return this;}
    else
    {return next.last();}
  }
}
