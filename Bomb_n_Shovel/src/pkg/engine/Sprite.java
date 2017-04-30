package pkg.engine;

import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * Game sprite class. Supports animation, offsets, blend modes and opacity.
 */
public class Sprite
{
  public final ImageView img;
  final int frames_h;
  final int frames_v;
  int offset_x;
  int offset_y;
  
  double centerDir,centerDist;
  
  /**
   * Long constructor. Use this if you want to load image from file.
   * @param img_arg Image name.
   * @param frh_arg Amount of horizontal frames.
   * @param frv_arg Amount of vertical frames.
   * @param offx_arg Horizontal offset.
   * @param offy_arg Vertical offset.
   */
  public Sprite(String img_arg,int frh_arg,int frv_arg,int offx_arg,int offy_arg)
  {
    img=new ImageView(new Image(Spr.resPath+img_arg,true));
    frames_h=frh_arg;
    frames_v=frv_arg;
    setOffset(offx_arg,offy_arg);
    
  }
  
  /**
   * Short constructor. Copies previously created sprite.
   * Works well with {@link pkg.engine.Spr}.
   * @param spr_arg 
   */
  public Sprite(Sprite spr_arg)
  {
    img=new ImageView(spr_arg.img.getImage());
    frames_h=spr_arg.frames_h;
    frames_v=spr_arg.frames_v;
    setOffset(spr_arg.offset_x,spr_arg.offset_y);
    
  }
  
  /**
   * Sets new offset to sprite.
   * @param offx_arg
   * @param offy_arg 
   */
  public final void setOffset(int offx_arg,int offy_arg)
  { 
    offset_x=offx_arg;
    offset_y=offy_arg;
    centerDir=Mathe.pointDirection(offset_x,offset_y,getWidth()/2,getHeight()/2);
    centerDist=Mathe.pointDistance(offset_x,offset_y,getWidth()/2,getHeight()/2);
  }
  
  /**
   * @return Sprite width taking frames in account.
   */
  public final double getWidth()
  {return img.getImage().getWidth()/frames_h;}
  
  /**
   * @return Sprite height taking frames in account.
   */
  public final double getHeight()
  {return img.getImage().getHeight()/frames_v;}
  
  /**
   * Sets blend mode.
   * @param blend Standard JavaFX blend mode object.
   */
  public void setBlendMode(BlendMode blend)
  {img.setBlendMode(blend);}
  
  /**
   * Sets alpha.
   * @param alpha Value between 0 and 1. 
   */
  public void setAlpha(double alpha)
  {img.setOpacity(alpha);}
  
}
