/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import pkg.engine.Obj;
import pkg.terrain.Terrain;

/**
 * Entity class for 
 */
public class Entity extends GameObject
{
  int tileStored=0;
  boolean passable;
  
  public Entity(double x_arg,double y_arg,boolean passable_arg)
  {
    super(x_arg,y_arg);
    objIndex.add(Obj.oid.entity);
    
    passable=passable_arg;
  }
  
  
  
  /**
   * Memorizes current tile and replaces it with unpassable wall.
   */
  protected void tileStore()
  {
    int xx=(int)x/Terrain.cellSize,
        yy=(int)y/Terrain.cellSize;
    tileStored=Terrain.terrain[xx][yy];
    Terrain.terrain[xx][yy]=1;
  }
  
  /**
   * Restores back stored tile.
   */
  protected void tileRestore()
  {
    int xx=(int)x/Terrain.cellSize,
        yy=(int)y/Terrain.cellSize;
    Terrain.terrain[xx][yy]=tileStored;
    tileStored=0;
  }
  
}
