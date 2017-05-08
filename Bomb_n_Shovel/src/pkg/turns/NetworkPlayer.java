/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.turns;

import pkg.net.Cmd;
import pkg.Peasant;
import pkg.net.Client;

/**
 *
 * @author gn.fur
 */
public class NetworkPlayer extends Player
{
  Client client;
  
  public NetworkPlayer()
  {client=null;}
  
  public NetworkPlayer(Client client_arg)
  {client=client_arg;}
  
  @Override 
  public void STEP()
  {
    super.STEP();
    
    
    if (isMyTurn())
    {
      Peasant pCur=peasants.get(peasantCur);
        
      if (client!=null && client.reader.isReceived())
      {pCur.command=(Cmd)client.reader.read();}
    }
      
  }
  
  
}
