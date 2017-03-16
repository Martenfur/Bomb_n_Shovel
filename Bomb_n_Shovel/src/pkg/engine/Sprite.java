package pkg.engine;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Sprite
{
  public final ImageView img;
  final int frames_h;
  final int frames_v;
  int offset_x;
  int offset_y;
  
  double centerDir,centerDist;
  
  public Sprite(Image img_arg,int frh_arg,int frv_arg,int offx_arg,int offy_arg)
  {
    img=new ImageView(img_arg);
    frames_h=frh_arg;
    frames_v=frv_arg;
    setOffset(offx_arg,offy_arg);
    //System.out.println(img.getImage().);
  }
  
  public Sprite(Sprite spr_arg)
  {
    img=     spr_arg.img;
    frames_h=spr_arg.frames_h;
    frames_v=spr_arg.frames_v;
    setOffset(spr_arg.offset_x,spr_arg.offset_y);
    
  }
  
  public final void setOffset(int offx_arg,int offy_arg)
  { 
    offset_x=offx_arg;
    offset_y=offy_arg;
    centerDir=Mathe.pointDirection(offset_x,offset_y,getWidth()/2,getHeight()/2);
    centerDist=Mathe.pointDistance(offset_x,offset_y,getWidth()/2,getHeight()/2);
  }
  
  public final double getWidth()
  {return img.getImage().getWidth()/frames_h;}
  
  public final double getHeight()
  {return img.getImage().getHeight()/frames_v;}
  
}
