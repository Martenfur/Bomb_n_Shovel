package pkg.engine;

import javafx.scene.image.Image;

/**
 * List of sprites.
 */
public class Spr
{
  public final static String resPath="resources/";
  
  //frames_h,frames_v,center_x,center_y
  public final static Sprite peasant=  new Sprite("peasant.png",   2,1,12,32),
                             kitten=   new Sprite("testkitten.png",1,1, 0, 0),
                             animation=new Sprite("testanim.png",  4,2, 0, 0),
                             path=     new Sprite("path.png",      2,2, 0, 0),
                             tree=     new Sprite("tree.png",      1,1,24,59),
                             terrain=  new Sprite("terrain.png",   4,6, 0, 0);
}

