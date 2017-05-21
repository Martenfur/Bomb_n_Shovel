package pkg.net;

import java.io.Serializable;

/**
 * 
 * @author gn.fur
 */
public class Cmd implements Serializable
{
  String command;
  double[] args;
  
  /**
   * Set of commands:
   * 
   * ping - n/a
   * System command. Ignored by reader.
   * 
   * disconnect - n/a
   * Tells that player is disconnected.
   * 
   * setpath - x,y
   * Sets path to given point.
   * 
   * move - n/a
   * Moves peasant with created path.
   * 
   * endturn - n/a
   * 
   */
   
  /**
   * Command id and some parameters.
   * @param command_arg
   * @param args_arg 
   */
  public Cmd(String command_arg,double... args_arg)
  {
    command=command_arg;
    args=args_arg.clone();
  }
  
  public boolean cmp(String command_arg)
  {return command.equals(command_arg);}
  
  /**
   * @return Command.
   */
  public String get()
  {return command;}
  
  /**
   * @param i Argument index.
   * @return Argument with given index.
   */
  public double get(int i)
  {return args[i];}
  
  /**
   * Returns amount of arguments.
   * @return 
   */
  public int size()
  {return args.length;}
}
