package pkg.turns;

/**
 * Command for peasant.
 */
public class PlayerCmd
{
  cmd command;
  double[] args;
  
  /**
   * Set of commands.
   * setpath - x,y
   * move - n/a
   */
  public static enum cmd
  {
    setpath(0), //Constructing path to certain point.
    move(1);    //Moving using existing path.
    
    int val;
    cmd(int val_arg) 
    {val=val_arg;}
    
    public int get() 
    {return val;}
  }  
  
  /**
   * Command id and some parameters.
   * @param command_arg
   * @param args_arg 
   */
  PlayerCmd(cmd command_arg,double... args_arg)
  {
    command=command_arg;
    args=args_arg.clone();
  }
  
  
  public cmd get() 
  {return command;}
  
  public double getarg(int i) 
  {return args[i];}
}
