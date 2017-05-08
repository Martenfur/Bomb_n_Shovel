package pkg.turns;

import java.util.ArrayList;
import pkg.GameObject;

/**
 * Manages turns.
 */
public class TurnManager extends GameObject
{
  ArrayList<Player> players; 
  int playerCur=0;
  boolean initiative=true;
  
  public TurnManager()
  {
    super(0,0);
    players=new ArrayList<>();
  }  
  
  @Override
  public void STEP()
  {
    if (initiative && !players.isEmpty())
    {initiativeGive();}
  }
  
  /**
   * Adds new player.
   * @param player 
   */
  public void playerAdd(Player player)
  {
    players.add(player);
    player.turnManager=this;
    player.tid=players.size()-1;
  }
  
  /**
   * @param i
   * @return Player with given id or null.
   */
  public Player playerGet(int i)
  {
    try
    {return players.get(i);}
    catch(Exception e)
    {return null;}
  }
  
  /**
   * Gives initiative to current player.
   */
  void initiativeGive()
  {
    initiative=false;
    players.get(playerCur).initiative=true;
  }  
  
  /**
   * Takes initiative back from current player.
   */
  void initiativeTake()
  {
    initiative=true;
    players.get(playerCur).initiative=false;
    
    playerCur+=1;
    if (playerCur>=players.size())
    {playerCur=0;}
  }
  
}
