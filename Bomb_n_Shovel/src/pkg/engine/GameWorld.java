package pkg.engine;

import pkg.Lobby;
import pkg.net.Client;
import pkg.terrain.Terrain;
import pkg.terrain.TileProp;
import pkg.turns.Logger;

public class GameWorld
{
  public static Terrain terrain;
  public static Client client;
  
  public static void CREATE()
  {
    TileProp.init();
    
    Mathe.randomize(); 
    Input.CREATE();
    Draw.CREATE();
    
    new Lobby();          
  }
  
  public static void UPDATE()
  { 
    Camera.UPDATE();
    Input.UPDATE();
    Obj.UPDATE();
    Draw.UPDATE();
  }
    
}
