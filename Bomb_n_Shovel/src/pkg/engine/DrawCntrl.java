package pkg.engine;

import java.util.ArrayList;
import javafx.scene.paint.Color;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.geometry.Rectangle2D;

public class DrawCntrl
{
  public static ArrayList<Node>[] drawlist;
  public static ArrayList<Integer>[] depthlist;
  
  static Color    currentColor = new Color(0,0,0,1);
  static int      currentDepth = 0;
  static Polyline currentPoly  = null;
  
  public static enum df
  {
    BEGIN(0),
    DEFAULT(1),
    END(2);
    
    int val;
    df(int val_arg) 
    {val=val_arg;}
    
    public int get() 
    {return val;}
  }
  
  public static df drawFlag=df.DEFAULT;
  
  public static void CREATE()
  {
    drawlist = new ArrayList[3];
    for(int i=0; i<3; i+=1)
    {drawlist[i] = new ArrayList<>();}
    
    depthlist = new ArrayList[3];
    for(int i=0; i<3; i+=1)
    {depthlist[i] = new ArrayList<>();}
  }
  
  public static void UPDATE()
  {
    Game.root.getChildren().clear();
    
    int size;
    for(int k=0; k<3; k+=1)
    {
      size=drawlist[k].size();
      for(int i=0; i<size; i+=1)
      {Game.root.getChildren().addAll(drawlist[k].get(i));}
      drawlist[k].clear();
      depthlist[k].clear();
    }
  }
  
  //////////////////////////////////////////////////////////////////////
  public static void draw(Node dr)
  {
    ArrayList<Node>    listCur  = drawlist[drawFlag.get()];
    ArrayList<Integer> depthCur = depthlist[drawFlag.get()];
    listCur.add(dr);
    depthCur.add(currentDepth);
    
    Node nodeBuf;
    int depthBuf;
    for(int i=listCur.size()-1; i>0; i-=1)
    {
      if (depthCur.get(i)>depthCur.get(i-1))
      {
        depthBuf=depthCur.get(i);
        nodeBuf= listCur.get(i);
        depthCur.set(i,depthCur.get(i-1));
        listCur.set(i,listCur.get(i-1));
        depthCur.set(i-1,depthBuf);
        listCur.set(i-1,nodeBuf);
      }
      else
      {break;}
    }
    
  }
  //////////////////////////////////////////////////////////////////////
  
  
  //////////////////////////////////////////////////////////////////////
  public static void setDepth(int depth)
  {currentDepth=depth;}
  //////////////////////////////////////////////////////////////////////
  
  
  //////////////////////////////////////////////////////////////////////
  public static void setColor(Color c)
  {currentColor=c;}
  //////////////////////////////////////////////////////////////////////
  
  
  //////////////////////////////////////////////////////////////////////
  public static void setAlpha(double a)
  {
    if (a<0)
    {a=0;}
    else
    {
      if (a>1)
      {a=1;}
    }
    
    currentColor = new Color(currentColor.getRed(),
                             currentColor.getGreen(),
                             currentColor.getBlue(),
                             a);
  }
  //////////////////////////////////////////////////////////////////////
  
  //////////////////////////////////////////////////////////////////////
  /*
  The most basic sprite function.
  Just draws image as is. No rotation, no frames, no scale.
  */
  public static void drawSprite(Sprite spr,double x,double y)
  {
    spr.img.setX(x-spr.offset_x/2);
    spr.img.setY(y-spr.offset_y/2);
    
    draw(spr.img);
  }
  //////////////////////////////////////////////////////////////////////
  
  
  //////////////////////////////////////////////////////////////////////
  /*
  Draws sprite with given frame.
  */
  public static void drawSprite(Sprite spr,double frame,double x,double y)
  {
    frame=Math.floor(frame);
    spr.img.setX(x-spr.offset_x/2);
    spr.img.setY(y-spr.offset_y/2);
    
    double vp_w=spr.getWidth()/spr.frames_h;
    double vp_h=spr.getHeight()/spr.frames_v;
    
    double vp_x=(frame-Math.floor(frame/spr.frames_h)*spr.frames_h)*vp_w;
    double vp_y=Math.floor(frame/spr.frames_h)*vp_h;

    spr.img.setViewport(new Rectangle2D(vp_x,vp_y,vp_w,vp_h));
    
    draw(spr.img);
  }
  //////////////////////////////////////////////////////////////////////
  
  
  //////////////////////////////////////////////////////////////////////
  /*
  Draws sprite with given frame.
  */
  public static void drawSprite(Sprite spr,double frame,double x,double y,double ang)
  {
    ang-=Math.floor(ang/360)*360;
   
    frame=Math.floor(frame);
    
    spr.setOffset(spr.offset_x,spr.offset_y);
    
    spr.img.setX(x-spr.getWidth()/2+Mathe.lcos(spr.centerDist,spr.centerDir-ang));
    spr.img.setY(y-spr.getHeight()/2+Mathe.lsin(spr.centerDist,spr.centerDir-ang));
    spr.img.setRotate(ang);
    
    double vp_w=spr.getWidth();
    double vp_h=spr.getHeight();
    
    double vp_x=(frame-Math.floor(frame/spr.frames_h)*spr.frames_h)*vp_w;
    double vp_y=Math.floor(frame/spr.frames_h)*vp_h;

    spr.img.setViewport(new Rectangle2D(vp_x,vp_y,vp_w,vp_h));
    
    draw(spr.img);
  }
  //////////////////////////////////////////////////////////////////////
  
  
  //////////////////////////////////////////////////////////////////////
  public static void drawLine(Line line,double x1,double y1,
                                        double x2,double y2)
  {  
    line.setStartX(x1);
    line.setStartY(y1);
    line.setEndX  (x2);
    line.setEndY  (y2);
    
    line.setStroke(currentColor);
    
    draw(line);
  }
  //////////////////////////////////////////////////////////////////////
  
  
  //////////////////////////////////////////////////////////////////////
  public static void drawLine(Line line,double x1,double y1,
                                        double x2,double y2,
                                        int w)
  {   
    line.setStartX(x1);
    line.setStartY(y1);
    line.setEndX  (x2);
    line.setEndY  (y2);
    
    line.setStroke(currentColor);
    line.setStrokeWidth(w);
    
    draw(line);
  }
  //////////////////////////////////////////////////////////////////////
  
  
  //////////////////////////////////////////////////////////////////////
  public static void drawRectangle(Rectangle rect,double x1,double y1,
                                                  double x2,double y2,
                                                  boolean outline)
  {
    rect.setX     (x1);
    rect.setY     (y1);
    rect.setWidth (x2-x1);
    rect.setHeight(y2-y1); 
    if (outline)
    {
      rect.setStroke(currentColor);
      rect.setFill(Color.rgb(0,0,0,0));
    }
    else
    {rect.setFill(currentColor);}
    draw(rect);
  }
  //////////////////////////////////////////////////////////////////////
  

  //////////////////////////////////////////////////////////////////////
  public static void drawCircle(Circle circ,double x,double y,double r,boolean outline)
  {
    circ.setCenterX(x);
    circ.setCenterY(y);
    circ.setRadius(r); 
    if (outline)
    {
      circ.setStroke(currentColor);
      circ.setFill(Color.rgb(0,0,0,0));
    }
    else
    {circ.setFill(currentColor);}
    draw(circ);
  }
  //////////////////////////////////////////////////////////////////////  

  
  //////////////////////////////////////////////////////////////////////
  public static void drawPolyBegin(Polyline poly,boolean outline)
  {
    currentPoly=poly;
    
    if (outline)
    {poly.setStroke(currentColor);}
    else
    {
      poly.setFill(currentColor);
      poly.setStroke(Color.rgb(0,0,0,0));
    }
  }
  //////////////////////////////////////////////////////////////////////

  
  //////////////////////////////////////////////////////////////////////
  public static void drawPolyEnd()
  {
    if (currentPoly!=null)
    {
      draw(currentPoly);
      currentPoly=null;
    }
  }
  //////////////////////////////////////////////////////////////////////

  
  //////////////////////////////////////////////////////////////////////
  public static void polyAddPoint(double x,double y)
  {
    if (currentPoly!=null)
    {currentPoly.getPoints().addAll(new Double[]{x,y});}
  }
  //////////////////////////////////////////////////////////////////////  
  
}
