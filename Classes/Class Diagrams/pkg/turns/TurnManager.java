package pkg.turns;

import java.util.ArrayList;
import pkg.GameObject;
import pkg.Peasant;
import pkg.engine.Obj;

/**
 * Manages turns.
 */
public class TurnManager extends GameObject
{
  ArrayList<Player> players; 
  int playerCur=0;
  boolean initiative=true;
  
  int kekAl=60*5;
  
  public TurnManager()
  {
    super();
    players=new ArrayList<>();
  }  
  
  @Override
  public void STEP()
  {
    if (initiative && !players.isEmpty())
    {initiativeGive();}
    
    //kekAl-=1;
    //if (kekAl==0)
    //{new MatchResult();}
    
    for(int i=0; i<players.size(); i+=1)
    {
      if (players.get(i).peasants.isEmpty())
      {
        System.out.println("Looks like team "+i+"has lost!");
        break;
      }
    }
  }
  
  @Override 
  public void DESTROY()
  {
    while(!players.isEmpty())
    {
      Obj.objDestroy(players.get(0));
      players.remove(0);
    }
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
  
  /**
   * Retrieves current peasant.
   * @return peasant
   */
  public Peasant getCurrentPeasant()
  {
    try
    {return players.get(playerCur).getCurrentPeasant();}
    catch(Exception e)
    {
      System.out.println("NO PLAYER!");
      return null;
    }
  }
  
  /**
   * Tells if current player is local.
   * @return 
   */
  public boolean isCurrentPlayerLocal()
  {
    try
    {return players.get(playerCur) instanceof LocalPlayer;}
    catch(Exception e)
    {return false;}
  }
}
