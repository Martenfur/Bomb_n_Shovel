package pkg;

import java.util.ArrayList;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import pkg.engine.*;

public class Field extends GameObject
{
  public int[][] terrain;
  public int[][] terrainTile;
  
  public int terrain_w,terrain_h,cellSize;
  
  Canvas[][] chunk;
  int chunkSize;
  
  boolean g=false;
  
  ArrayList<Sprite> terrainCellSpr;
  
  double cam_mx,cam_my,cam_cx,cam_cy;
  
  public Field(double x_arg,double y_arg)
  {
    super(x_arg,y_arg);
    objIndex.add(Obj.oid.field);
    
    //////////////////////////////////////////////////
    new Peasant(32*64,32*64);
    GameWorld.cameraSetPosition(64*32-320,64*32-240);
    GameWorld.cameraSetScale(0.25,0.25);    
    //////////////////////////////////////////////////
    
    
    terrain_w=128;
    terrain_h=128;
    cellSize=  32;
    chunkSize= 16;
    
    chunk=new Canvas[(int)Math.ceil(terrain_w/chunkSize)][(int)Math.ceil(terrain_h/chunkSize)];
    for(int i=0; i<chunk.length; i+=1)
    {
      for(int k=0; k<chunk[0].length; k+=1)
      {
        chunk[i][k]=new Canvas(chunkSize*cellSize,chunkSize*cellSize);
        chunk[i][k].setLayoutX(i*chunkSize*cellSize);
        chunk[i][k].setLayoutY(k*chunkSize*cellSize);
        chunk[i][k].getGraphicsContext2D().drawImage(Spr.kitten.img.getImage(),0,0);
      }
    }
    
    //////////////////////////////////
    terrainCellSpr=new ArrayList<>();
    terrainCellSpr.add(null);     //Grass.
    terrainCellSpr.add(Spr.tree); //Tree.
    terrainCellSpr.add(null);     //Water.
    //////////////////////////////////
    
    //Generator.
    TerrainGenerator gen=new TerrainGenerator(terrain_w,terrain_h);
    terrain=gen.terrainGenerate(terrain_w,terrain_h);
    terrainTile=gen.terrainAutotile(terrain);
    //Generator. 
    
   }
  
  @Override
  public void STEP()
  {
    //GameWorld.cameraSetScale(1+Mathe.lsin(0.5,Game.currentTime*5),1+Mathe.lsin(0.5,Game.currentTime*5));
    
    if (Input.mbCheckPress)
    {
      cam_mx=Input.mouse_x-GameWorld.cameraGet_x();
      cam_my=Input.mouse_y-GameWorld.cameraGet_y();
      cam_cx=GameWorld.cameraGet_x();
      cam_cy=GameWorld.cameraGet_y();
    }
    
    if (Input.mbCheck)
    {
      GameWorld.cameraSetPosition(cam_cx-(Input.mouse_x-GameWorld.cameraGet_x())+cam_mx,
                                  cam_cy-(Input.mouse_y-GameWorld.cameraGet_y())+cam_my);
    }
    
    
  }
  
  @Override
  public void DRAW()
  { 
    
    int draw_xstart=(int)Math.max(0,      Math.floor(GameWorld.cameraGet_x()/cellSize));
    int draw_ystart=(int)Math.max(0,      Math.floor(GameWorld.cameraGet_y()/cellSize));
    int draw_xend=  (int)Math.min(terrain_w,Math.ceil((GameWorld.cameraGet_x()+Game.scr_w/GameWorld.cameraGetScale_x())/cellSize)+1);
    int draw_yend=  (int)Math.min(terrain_h,Math.ceil((GameWorld.cameraGet_y()+Game.scr_h/GameWorld.cameraGetScale_y())/cellSize)+1);
    
    Sprite sBuf;
    
    for(int k=draw_ystart; k<draw_yend; k+=1)
    {
      Draw.setDepth((int)-(k+0.5)*cellSize);   
      for(int i=draw_xstart; i<draw_xend; i+=1)
      {
        //Additional sprite.
        sBuf=terrainCellSpr.get(terrain[i][k]);
        
        if (sBuf!=null)
        {Draw.drawSprite(new Sprite(sBuf),(i+0.5)*cellSize,(k+0.5)*cellSize);}
        //Additional sprite.
      }
    }
    Draw.setDepth(10000);
    
    if (!g)
    {
      terrainRender();
      if (Spr.terrain.img.getImage().getProgress()==1)
      {g=true;}
    }
    
    draw_xstart=(int)Math.max(0,      Math.floor(GameWorld.cameraGet_x()/(cellSize*chunkSize)));
    draw_ystart=(int)Math.max(0,      Math.floor(GameWorld.cameraGet_y()/(cellSize*chunkSize)));
    draw_xend=  (int)Math.min(chunk.length,Math.ceil((GameWorld.cameraGet_x()+Game.scr_w/GameWorld.cameraGetScale_x())/(cellSize*chunkSize)));
    draw_yend=  (int)Math.min(chunk[0].length,Math.ceil((GameWorld.cameraGet_y()+Game.scr_h/GameWorld.cameraGetScale_y())/(cellSize*chunkSize)));
   
    for(int i=draw_xstart; i<draw_xend; i+=1)
    {
      for(int k=draw_ystart; k<draw_yend; k+=1)
      {Draw.draw(chunk[i][k]);}
    }
    
  }
  
  void terrainRender()
  {
    for(int i=0; i<chunk.length; i+=1)
    {
      for(int k=0; k<chunk[0].length; k+=1)
      {terrainChunkRender(i,k);}
    }
  }
  
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
        
        //System.out.println("tid: "+tid+" x: "+xx+" y: "+yy);
          
        surf.drawImage(terrImg,xx*cellSize,yy*cellSize,cellSize,cellSize,i*cellSize,k*cellSize,cellSize,cellSize);
        //x,y,w,h,x1,y1,x2,y2
      }
    }
  }
  
}
