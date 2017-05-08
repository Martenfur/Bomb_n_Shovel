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

/**
 * Controls 4 peasants.
 * Can be controlled by local player, computer (TODO) or remote player (TODO).
 */
public class Player extends GameObject
{
  public TurnManager turnManager;
  public int tid=0;            //Team id.
  ArrayList<Peasant> peasants; //List of peasants.
  int peasantCur=0;            //Current peasant.
  
  public boolean initiative=false;
  
  boolean firstTurn=true;
  
  public Player()
  {
    super(0,0);
    peasants=new ArrayList<>();
  }
  
  
  @Override
  public void STEP()
  {
    if (initiative && !peasants.isEmpty())
    {initiativeGive();}
    
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
  }
  
  /**
   * Gives initiative to current player.
   */
  void initiativeGive()
  {
    initiative=false;
    peasants.get(peasantCur).initiative=true;
    peasants.get(peasantCur).staminaRefill();
    if (tid==0 && firstTurn)
    {
      Camera.setPosition(peasants.get(peasantCur).x-Game.scr_w/2,peasants.get(peasantCur).y-Game.scr_h/2);
      firstTurn=false;
    }
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
