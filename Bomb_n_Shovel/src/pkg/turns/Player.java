/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.turns;

import pkg.engine.*;
import java.util.ArrayList;
import pkg.GameObject;
import pkg.Peasant;
import pkg.terrain.Terrain;

/**
 * Controls 4 peasants.
 * Can be controlled by local player, computer (TODO) or remote player (TODO).
 */
public class Player extends GameObject
{
  public TurnManager turnManager;
  int mode;                    //0 -local, 1 - CPU, 2 - remote.
  public int tid=0;            //Team id.
  ArrayList<Peasant> peasants; //List of peasants.
  int peasantCur=0;            //Current peasant.
  public boolean initiative=false;
  
  public Player(int mode_arg)
  {
    super(0,0);
    mode=mode_arg;
    peasants=new ArrayList<>();
  }
  
  
  @Override
  public void STEP()
  {
    if (initiative && !peasants.isEmpty())
    {initiativeGive();}
    
    if (isMyTurn())
    {
      Peasant pCur=peasants.get(peasantCur);
      
      if (pCur.initiative && !pCur.moving && Input.mbCheckRelease && !Terrain.camMove)
      {
        int cx,cy;
        cx=(int)Input.mouse_x/Terrain.cellSize;
        cy=(int)Input.mouse_y/Terrain.cellSize;
      
        //Pathfinding.
        if (pCur.pathList!=null && pCur.cx_prev==cx && pCur.cy_prev==cy)
        {pCur.command=new PlayerCmd(PlayerCmd.cmd.move);}
        else
        {pCur.command=new PlayerCmd(PlayerCmd.cmd.setpath,cx,cy);}
        //Pathfinding.
      }
    }
  }
  
  /**
   * Gives new peasant under player's control.
   * @param peasant 
   */
  public void peasantAdd(Peasant peasant)
  {
    peasants.add(peasant);
    peasant.myPlayer=this;
    peasant.tid=tid;
    System.out.println(tid);
  }
  
  /**
   * Gives initiative to current player.
   */
  void initiativeGive()
  {
    initiative=false;
    peasants.get(peasantCur).initiative=true;
    peasants.get(peasantCur).staminaRefill();
    Camera.viewer=peasants.get(peasantCur);
  } 
  
  /**
   * Ends this player's turn.
   */
  public void endTurn()
  {
    peasants.get(peasantCur).initiative=false;
    
    peasantCur+=1;
    if (peasantCur>=peasants.size())
    {peasantCur=0;}
    
    turnManager.initiativeTake();   
  }
  
  /**
   * Checks if it is current player's turn right now.
   * @return 
   */
  boolean isMyTurn()
  {
    if (turnManager!=null)
    {return turnManager.players.get(turnManager.playerCur)==this;}
    return false;
  }
  
  
}
