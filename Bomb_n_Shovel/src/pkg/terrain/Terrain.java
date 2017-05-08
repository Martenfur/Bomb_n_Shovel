package pkg.terrain;

import pkg.turns.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pkg.GameObject;
import pkg.Peasant;
import pkg.engine.*;

public class Terrain extends GameObject
{
  public static int[][] terrain;
  public static int[][] terrainTile;
  public static Sprite[][] terrainSpr;
  
  public static int terrain_w,terrain_h,cellSize;
  
  int basePt1_x,basePt1_y,basePt2_x,basePt2_y;
  long seed;
  
  Color c_water=Color.rgb(89,125,206);
  Rectangle bkg=new Rectangle(); 
  
  Canvas[][] chunk;
  int chunkSize;
  
  double cam_mx,   cam_my,
         cam_mxgui,cam_mygui;
  public static boolean camMove=false;
  int camDragAl=  -1,
      camDragTime=30;
  
  public Terrain(long seed_arg,TurnManager turnManager)
  {
    super(0,0);
    
    objIndex.add(Obj.oid.terrain);
    terrain_w=96;
    terrain_h=96;
    cellSize= 32;
    chunkSize=16;
    
    chunk=new Canvas[(int)Math.ceil(terrain_w/chunkSize)][(int)Math.ceil(terrain_h/chunkSize)];
    for(int i=0; i<chunk.length; i+=1)
    {
      for(int k=0; k<chunk[0].length; k+=1)
      {
        chunk[i][k]=new Canvas(chunkSize*cellSize,chunkSize*cellSize);
        chunk[i][k].setLayoutX(i*chunkSize*cellSize);
        chunk[i][k].setLayoutY(k*chunkSize*cellSize);
      }
    }
    
    //Creating terrain.    
    seed=seed_arg;
    terrainCreate(seed);
    terrainRender();
    //Creating terrain.
    
    
    //Turn manager.
    Player p1=turnManager.playerGet(0);
    Player p2=turnManager.playerGet(1);
    
    for(int i=0; i<4; i+=1) 
    {p1.peasantAdd(new Peasant((basePt1_x+Mathe.rotate_x[i])*32,(basePt1_y+Mathe.rotate_y[i])*32));}
   
    for(int i=0; i<4; i+=1) 
    {p2.peasantAdd(new Peasant((basePt2_x+Mathe.rotate_x[i])*32,(basePt2_y+Mathe.rotate_y[i])*32));}
    //Turn manager.
    
    
    
    //////////////////////////////////////////////////
    //Camera.setPosition(basePt1_x*32+Camera.view_w/2,basePt1_y*32+Camera.view_h/2);
    //Camera.setScale(0.25,0.25);    
    //////////////////////////////////////////////////
    
   }
  
  @Override
  public void STEP()
  {
    //CAMERA
    if (Input.mbCheckPress)
    {
      cam_mx=   Input.mouse_x;
      cam_my=   Input.mouse_y;
      cam_mxgui=Input.mouse_xgui;
      cam_mygui=Input.mouse_ygui;
      camDragAl=camDragTime;
    }
    
    if (Input.mbCheck)
    {
      if (camDragAl>-1) 
      {camDragAl-=1;}
      
      if (!camMove && (Mathe.pointDistance(Input.mouse_xgui,Input.mouse_ygui,cam_mxgui,cam_mygui)>32 || camDragAl==0))
      {
        camMove=true;
        cam_mx=   Input.mouse_x;
        cam_my=   Input.mouse_y;
        cam_mxgui=Input.mouse_xgui;
        cam_mygui=Input.mouse_ygui;
      }
      
      if (camMove)
      {
        Camera.viewer=null;
        Camera.setPosition((cam_mx-(Input.mouse_x-Camera.get_x())),
                            cam_my-(Input.mouse_y-Camera.get_y()));
        
      }
    }
    else
    {
      if (!Input.mbCheckRelease) //Using this little trick we can activate this next step mouse was released. 
      {camMove=false;}
    }
    //CAMERA
    
  }
  
  @Override
  public void DRAW()
  { 
    Draw.setDepth(10001);
    Draw.setColor(c_water);
    Draw.drawRectangle(bkg,Camera.get_x(),           Camera.get_y(),
                           Camera.get_x()+Game.scr_w/Camera.getScale_x(),
                           Camera.get_y()+Game.scr_h/Camera.getScale_y(),false);
    
    
    int draw_xstart=(int)Math.max(0,      Math.floor(Camera.get_x()/cellSize));
    int draw_ystart=(int)Math.max(0,      Math.floor(Camera.get_y()/cellSize));
    int draw_xend=  (int)Math.min(terrain_w,Math.ceil((Camera.get_x()+Game.scr_w/Camera.getScale_x())/cellSize)+1);
    int draw_yend=  (int)Math.min(terrain_h,Math.ceil((Camera.get_y()+Game.scr_h/Camera.getScale_y())/cellSize)+1);
    
    for(int k=draw_ystart; k<draw_yend; k+=1)
    {   
      for(int i=draw_xstart; i<draw_xend; i+=1)
      {
        //Additional sprite.
        if (terrainSpr[i][k]!=null)
        {
          Draw.setDepth((int)-(k+0.5)*cellSize+TileProp.getDepth(terrain[i][k]));
          Draw.drawSprite(terrainSpr[i][k],(i+0.5)*cellSize,(k+0.5)*cellSize);
        }
        //Additional sprite.
      }
    }
    
    
    Draw.setDepth(10000);
    
    draw_xstart=(int)Math.max(0,      Math.floor(Camera.get_x()/(cellSize*chunkSize)));
    draw_ystart=(int)Math.max(0,      Math.floor(Camera.get_y()/(cellSize*chunkSize)));
    draw_xend=  (int)Math.min(chunk.length,Math.ceil((Camera.get_x()+Game.scr_w/Camera.getScale_x())/(cellSize*chunkSize)));
    draw_yend=  (int)Math.min(chunk[0].length,Math.ceil((Camera.get_y()+Game.scr_h/Camera.getScale_y())/(cellSize*chunkSize)));
   
    for(int i=draw_xstart; i<draw_xend; i+=1)
    {
      for(int k=draw_ystart; k<draw_yend; k+=1)
      {Draw.draw(chunk[i][k]);}
    }
    
  }
  
  /**
   * Renders terrain.
   */
  final void terrainRender()
  {
    for(int i=0; i<chunk.length; i+=1)
    {
      for(int k=0; k<chunk[0].length; k+=1)
      {terrainChunkRender(i,k);}
    }
  }
  
  /**
   * Renders one chunk.
   * @param x
   * @param y 
   */
  void terrainChunkRender(int x,int y)
  {
    GraphicsContext surf=chunk[x][y].getGraphicsContext2D();
    Image terrImg=Spr.terrain.img.getImage();
    
    int tid,yy,xx;
    
    for(int i=0; i<chunkSize; i+=1)
    {
      for(int k=0; k<chunkSize; k+=1)
      {
        tid=terrainTile[x*chunkSize+i][y*chunkSize+k];
        yy=tid/4;
        xx=tid-yy*4;
   
        surf.drawImage(terrImg,xx*cellSize,yy*cellSize,cellSize,cellSize,i*cellSize,k*cellSize,cellSize,cellSize);
        //x,y,w,h,x1,y1,x2,y2
      }
    }
  }
  
  private final void terrainCreate(long seed)
  {
    //Generator.
    TerrainGenerator gen=new TerrainGenerator(terrain_w,terrain_h,seed);
    
    Mathe.randomPush();
    Mathe.randomSetSeed(seed);
    
    //ISLANDS
    int island_lmin=16;
    int island_lmax=32;
    int island_diradd=30;
    
    double l,d;
   
    //First team.
    l=Mathe.random(island_lmin,island_lmax);
    d=Mathe.random(360);
    basePt1_x=terrain_w/2+(int)Mathe.lcos(l,d);
    basePt1_y=terrain_h/2+(int)Mathe.lsin(l,d);
    gen.islandAdd(basePt1_x,basePt1_y,Mathe.irandom(8,10));
    //First team.
    
    //Second team.
    d+=180+Mathe.irandom(-island_diradd,island_diradd);
    basePt2_x=terrain_w/2+(int)Mathe.lcos(l,d);
    basePt2_y=terrain_h/2+(int)Mathe.lsin(l,d);
    gen.islandAdd(basePt2_x,basePt2_y,Mathe.irandom(8,10));
    //Second team.
    
    //Middle island.
    gen.islandAdd(terrain_w/2,terrain_h/2,Mathe.irandom(8,10));
    //Middle island.
    
    //Center point.
    int ptC_x=(basePt1_x+basePt2_x+terrain_w/2)/3,
        ptC_y=(basePt1_y+basePt2_y+terrain_h/2)/3;
    //Center point.
    
    double baseDir=Mathe.pointDirection(basePt1_x,basePt1_y,basePt2_x,basePt2_y)+90;
    
    //Additional islands.
    int islandAm=Mathe.irandom(3,5);
    for(int i=0; i<islandAm; i+=1)
    {
      l=Mathe.random(island_lmin/2,island_lmax/2);
      d=baseDir+180*Mathe.irandom(1)+Mathe.irandom(-45,45);
      gen.islandAdd(ptC_x+(int)Mathe.lcos(l,d),ptC_y+(int)Mathe.lsin(l,d),Mathe.irandom(5,10));
    }
    //Additional islands.
    
    islandAm=Mathe.irandom(3,5);
    for(int i=0; i<islandAm; i+=1)
    {
      l=Mathe.random(island_lmax*0.5,island_lmax*0.75);
      d=baseDir+Mathe.irandom(-30,30)+90*Mathe.irandom(1);
      gen.islandAdd(ptC_x+(int)Mathe.lcos(l,d),ptC_y+(int)Mathe.lsin(l,d),Mathe.irandom(3,4));
    }
    
    Mathe.randomPop();
    
    terrain=gen.terrainGenerate(terrain_w,terrain_h);
    terrainSpr=new Sprite[terrain_w][terrain_h];
    terrainTile=gen.terrainAutotile(terrain,terrainSpr);
    //Generator. 
    
  }
  
  
  /**
   * Safely gets value out of terrain. 
   * @param x
   * @param y
   * @return Terrain value or 2 if out of bounds.
   */
  public static int tget(int x,int y)
  {
    try
    {return terrain[x][y];}
    catch(Exception e)
    {return 2;}
  }
  
  /**
   * Safely gets value out of array. 
   * @param terr
   * @param x
   * @param y
   * @return Array value or 2 if out of bounds.
   */
  public static int get(int[][] terr,int x,int y)
  {
    try
    {return terr[x][y];}
    catch(Exception e)
    {return 2;}
  }
  
}
