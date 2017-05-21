/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.terrain;

import java.util.ArrayList;
import pkg.engine.*;

/**
 * Holds tile properties, such as:
 - Ground/WATER.
 * - Passable/UNPASSABLE.
 - Additional sprite.
 - Additional sprite's depth.
 */
public class TileProp
{
  
  //////////////////////////////////
  /*
  Sprite array.
  0 - Grass.
  1 - Tree.
  2 - Water.
  3 - Bridge.
  */
  
  static ArrayList<Tile> tiles;
  
  static final boolean PASSABLE=  true,
                       UNPASSABLE=false,
                       GROUND=    true,
                       WATER=     false;
  
  public static void init()
  {
    tiles=new ArrayList<>();
    
    tileAdd(GROUND,PASSABLE);              //Grass.
    tileAdd(GROUND,UNPASSABLE,Spr.tree,0); //Tree.
    tileAdd(WATER,UNPASSABLE);             //Water.
    tileAdd(WATER,PASSABLE,null,2);       //Bridge.  
  }
  
  final static void tileAdd(boolean ground,boolean passable,Sprite spr,int depth)
  {tiles.add(new Tile(ground,passable,spr,depth));}
  
  final static void tileAdd(boolean ground,boolean passable)
  {tiles.add(new Tile(ground,passable,null,0));}
  
  public static Sprite getSpr(int i)
  {
    try
    {return tiles.get(i).spr;}
    catch(Exception e)
    {return null;}
  }
  
  public static int getDepth(int i)
  {
    try
    {return tiles.get(i).depth;}
    catch(Exception e)
    {return 0;}
  }
 
  public static boolean isPassable(int i)
  {
    try
    {return tiles.get(i).passable;}
    catch(Exception e)
    {return false;}
  }
  
  public static boolean isGround(int i)
  {
    try
    {return tiles.get(i).ground;}
    catch(Exception e)
    {return false;}
  }
  
  
  
  /**
   * Property class.
   */
  static class Tile
  {
    boolean ground,passable;
    Sprite spr;
    int depth;
    
    Tile(boolean ground_arg,boolean passable_arg,Sprite spr_arg,int depth_arg)
    {
      ground=ground_arg;
      passable=passable_arg;
      spr=spr_arg;
      depth=depth_arg;
    }
  }
    
}
