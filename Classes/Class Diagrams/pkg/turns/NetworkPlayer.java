/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.turns;

import pkg.MatchResult;
import pkg.net.Cmd;
import pkg.net.Client;
import pkg.Peasant;
import pkg.engine.Obj;
import pkg.engine.ObjIter;
import pkg.terrain.Terrain;

/**
 *
 * @author gn.fur
 */
public class NetworkPlayer extends Player
{
  
  public NetworkPlayer()
  {client=null;}
  
  public NetworkPlayer(Client client_arg)
  {client=client_arg;}
  
  @Override 
  public void STEP()
  {
    super.STEP();
    
    
    //DISCONNECTING
    if (client!=null && client.reader.isReceived())
    {
      if (((Cmd)client.reader.watch()).cmp("disconnect"))
      {
        for(ObjIter it=new ObjIter(Obj.oid.terrain); it.end(); it.inc())
        {((Terrain)it.get()).uiBlock=true;}
        new MatchResult();
      }
    }
    //DISCONNECTING
    
    if (isMyTurn())
    {
      Peasant pCur=peasants.get(peasantCur);
        
      if (client!=null && client.reader.isReceived())
      {pCur.command=(Cmd)client.reader.read();}
    }  
  }
  
  
}
