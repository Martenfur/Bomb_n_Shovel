package pkg.pathfinder;

/**
 * Basically, one-sided linked list of coordinate pairs.
 * Used for pathfinding as final path storage,
 * but potentially can be used for any path-related stuff.
 */
public class PathPoint
{
  public int x,y;
  public PathPoint next=null;
  
  /**
   * Main constructor. Use this to create new path.
   * @param x_arg x of starting point.
   * @param y_arg y of starting point.
   */
  public PathPoint(int x_arg,int y_arg)
  {
    x=x_arg;
    y=y_arg;
  }
  
  /**
   * Adds new point to path.
   * @param x_arg x of new point.
   * @param y_arg y of new point.
   * @return 
   */
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
  
  /**
   * @return Last element in path. 
   */
  public PathPoint last()
  {
    if (next==null)
    {return this;}
    else
    {return next.last();}
  }
}
