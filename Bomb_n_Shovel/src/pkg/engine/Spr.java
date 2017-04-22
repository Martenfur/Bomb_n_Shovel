package pkg.engine;

import javafx.scene.image.Image;

/**
 * List of sprites.
 */
public class Spr
{
  //frames_h,frames_v,center_x,center_y
  public final static Sprite peasant=  new Sprite(new Image("res/peasant.png",true),1,1,12,32);
  public final static Sprite kitten=   new Sprite(new Image("res/testkitten.png",true),1,1,0,0);
  public final static Sprite animation=new Sprite(new Image("res/testanim.png",true),4,2,0,0);
  public final static Sprite path=     new Sprite(new Image("res/path.png",true),2,1,0,0);
  public final static Sprite tree=     new Sprite(new Image("res/tree.png",true),1,1,24,59);
  public final static Sprite terrain=  new Sprite(new Image("res/terrain.png",true),4,4,0,0);
}
