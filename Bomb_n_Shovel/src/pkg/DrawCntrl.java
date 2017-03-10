package pkg;

import java.util.ArrayList;
import javafx.scene.paint.Color;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

public class DrawCntrl
{
  public static ArrayList<Node> drawable = new ArrayList<Node>();   
  static Color currentColor = new Color(0,0,0,1);
  static int   currentDepth = 0;
  static Polyline currentPoly  = null;
  static boolean isPolygon = false;
  
  public static void UPDATE()
  {
    Game.root.getChildren().clear();
    
    for(int i=0; i<drawable.size(); i+=1)
    {Game.root.getChildren().addAll(drawable.get(i));}
    drawable.clear();
  }
  
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
  public static void draw(Node gr)
  {
    //Depth sorting goes here.
    drawable.add(gr);
  }
  //////////////////////////////////////////////////////////////////////
  
  
  //////////////////////////////////////////////////////////////////////
  public static void drawLine(Line gr,double x1,double y1,
                                      double x2,double y2)
  { 
    gr.setStroke(currentColor);
    gr.setStartX(x1);
    gr.setStartY(y1);
    gr.setEndX  (x2);
    gr.setEndY  (y2);
    
    draw(gr);
  }
  //////////////////////////////////////////////////////////////////////
  
  
  //////////////////////////////////////////////////////////////////////
  public static void drawLineWidth(Line gr,double x1,double y1,
                                           double x2,double y2,
                                           int w)
  { 
    gr.setStartX(x1);
    gr.setStartY(y1);
    gr.setEndX  (x2);
    gr.setEndY  (y2);
    
    gr.setStroke(currentColor);
    gr.setStrokeWidth(w);
    
    draw(gr);
  }
  //////////////////////////////////////////////////////////////////////
  
  
  //////////////////////////////////////////////////////////////////////
  public static void drawRectangle(Rectangle gr,double x1,double y1,
                                                double x2,double y2,
                                                boolean outline)
  {
    gr.setFill(currentColor);
    gr.setX     (x1);
    gr.setY     (y1);
    gr.setWidth (x2-x1);
    gr.setHeight(y2-y1); 
    if (outline)
    {
      gr.setStroke(currentColor);
      gr.setFill(Color.rgb(0,0,0,0));
    }
    else
    {gr.setFill(currentColor);}
    draw(gr);
  }
  //////////////////////////////////////////////////////////////////////
  

  //////////////////////////////////////////////////////////////////////
  public static void drawCircle(Circle gr,double x,double y,double r,boolean outline)
  {
    gr.setCenterX(x);
    gr.setCenterY(y);
    gr.setRadius(r); 
    if (outline)
    {
      gr.setStroke(currentColor);
      gr.setFill(Color.rgb(0,0,0,0));
    }
    else
    {gr.setFill(currentColor);}
    draw(gr);
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
