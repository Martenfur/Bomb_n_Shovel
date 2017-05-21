package pkg.turns;

import pkg.MatchResult;
import pkg.net.Cmd;
import pkg.net.Client;
import pkg.Peasant;
import pkg.engine.Obj;
import pkg.engine.ObjIter;
import pkg.terrain.Terrain;

/**
 * Distant player.
 * Class receives messages from server and replicates other client's actions.
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
        client.reader.read(); //Taking disconnect command away.
        for(ObjIter it=new ObjIter(Obj.oid.terrain); it.end(); it.inc())
        {((Terrain)it.get()).uiBlock=true;}
        
        new MatchResult(-1);
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
