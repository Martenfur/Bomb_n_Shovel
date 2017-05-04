package pkg.engine;

import pkg.terrain.Terrain;
import pkg.terrain.TileProp;

public class GameWorld
{
  public static Terrain field;
  
  public static void CREATE()
  {
    TileProp.init();
    
    Mathe.randomize(); 
    Input.CREATE();
    Draw.CREATE();
    
    field=new Terrain();
  }
  
  public static void UPDATE()
  { 
    Camera.UPDATE();
    Input.UPDATE();
    Obj.UPDATE();
    Draw.UPDATE();
  }
    
}
