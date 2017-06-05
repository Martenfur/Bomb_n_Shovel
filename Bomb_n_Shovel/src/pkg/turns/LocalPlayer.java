/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.turns;

import java.io.IOException;
import pkg.foxoft.bombnshovel.net.Cmd;
import pkg.Peasant;
import pkg.engine.Input;
import pkg.engine.Mathe;
import pkg.net.Client;
import pkg.terrain.Terrain;
import pkg.terrain.TileProp;

/**
 *
 * @author gn.fur
 */
public class LocalPlayer extends Player
{ 
  public LocalPlayer()
  {client=null;}
  
  public LocalPlayer(Client client_arg)
  {client=client_arg;}
  
  
  @Override
  public void STEP()
  {
    super.STEP();
    
    if (isMyTurn() && !peasants.isEmpty() && peasants.size()>peasantCur)
    {
      Peasant pCur=peasants.get(peasantCur);
      
      if (pCur.initiative && !pCur.moving && !Terrain.camMove)
      {
        
        if (Input.mbCheckRelease)
        {
          int cx,cy;
          cx=(int)Input.mouse_x/Terrain.cellSize;
          cy=(int)Input.mouse_y/Terrain.cellSize;
        
          //Pathfinding.
          if (pCur.pathList!=null && pCur.cx_prev==cx && pCur.cy_prev==cy)
          {pCur.command=new Cmd("move");}
          else
          {
            if (Mathe.pointDistance(pCur.x/Terrain.cellSize,pCur.y/Terrain.cellSize,cx,cy)==1 
            && !TileProp.isPassable(Terrain.terrain[cx][cy]))
            {
              pCur.command=new Cmd("interact",cx,cy);
              pCur.pathList=null;
            }
            else
            {pCur.command=new Cmd("setpath",cx,cy);}
          }
          //Pathfinding.
        }
   
        //Ending turn.
        if (Terrain.endturn)
        {
          Terrain.endturn=false;
          pCur.command=new Cmd("endturn");
        }
        //Ending turn.
               
        //Sending command to server.
        if (client!=null && pCur.command!=null)
        {
          try
          {client.sender.send(pCur.command);}
          catch (IOException e)
          {}
        }
        //Sending command to server.
        
      }
    }
    
  }
  
}
