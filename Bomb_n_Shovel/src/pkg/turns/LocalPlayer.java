/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.turns;

import java.io.IOException;
import pkg.net.Cmd;
import pkg.Peasant;
import pkg.engine.Input;
import pkg.net.Client;
import pkg.terrain.Terrain;

/**
 *
 * @author gn.fur
 */
public class LocalPlayer extends Player
{
  Client client;
  
  public LocalPlayer()
  {client=null;}
  
  public LocalPlayer(Client client_arg)
  {client=client_arg;}
  
  
  @Override
  public void STEP()
  {
    super.STEP();
    
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
        {
          pCur.command=new Cmd("move");
          //
          if (client!=null)
          {
            try
            {client.sender.send(pCur.command);}
            catch (IOException e)
            {}
          }
          //
        }
        else
        {
          pCur.command=new Cmd("setpath",cx,cy);
          //
          if (client!=null)
          {
            try
            {client.sender.send(pCur.command);}
            catch (IOException e)
            {}
          }
          //
        }
        //Pathfinding.
      }
    }
    
  }
  
}
