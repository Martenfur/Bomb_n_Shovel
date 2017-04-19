package pkg;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import pkg.engine.*;
import javafx.scene.shape.*;

public class Field extends GameObject
{
  public int[][] terrain;
  public int[][] terrainTile;
  
  public int terrain_w,terrain_h,cellSize;
  
  ArrayList<Sprite> terrainCellSpr;
  
  double cam_mx,cam_my,cam_cx,cam_cy;
  
  public Field(double x_arg,double y_arg)
  {
    super(x_arg,y_arg);
    objIndex.add(ObjCntrl.oid.field);
    
    new Peasant(32*64,32*64);
    GameWorld.cameraSetPosition(64*32-320,64*32-240);
    
    //////////////////////////////////
    terrainCellSpr=new ArrayList<>();
    terrainCellSpr.add(null);     //Grass.
    terrainCellSpr.add(Spr.tree); //Tree.
    terrainCellSpr.add(null);     //Water.
    //////////////////////////////////
    
    terrain_w=128;
    terrain_h=128;
    cellSize=32;
    
    terrain=terrainGenerate(terrain_w,terrain_h);
    System.out.println("seps");
    terrainTile=terrainAutotile();
    System.out.println("seps");
    GameWorld.cameraSetScale(0.5,0.5);
  }
  
  @Override
  public void STEP()
  {
    //GameWorld.cameraSetScale(1+Mathe.lsin(0.5,Game.currentTime*5),1+Mathe.lsin(0.5,Game.currentTime*5));
    
    if (InputCntrl.mbCheckPress)
    {
      cam_mx=InputCntrl.mouse_x-GameWorld.cameraGet_x();
      cam_my=InputCntrl.mouse_y-GameWorld.cameraGet_y();
      cam_cx=GameWorld.cameraGet_x();
      cam_cy=GameWorld.cameraGet_y();
    }
    
    if (InputCntrl.mbCheck)
    {
      GameWorld.cameraSetPosition(cam_cx-(InputCntrl.mouse_x-GameWorld.cameraGet_x())+cam_mx,
                                  cam_cy-(InputCntrl.mouse_y-GameWorld.cameraGet_y())+cam_my);
    }
    
    
  }
  
  @Override
  public void DRAW()
  { 
    
    int draw_xstart=(int)Math.max(0,      Math.floor(GameWorld.cameraGet_x()/cellSize));
    int draw_ystart=(int)Math.max(0,      Math.floor(GameWorld.cameraGet_y()/cellSize));
    int draw_xend=  (int)Math.min(terrain_w,Math.ceil((GameWorld.cameraGet_x()+Game.scr_w/GameWorld.cameraGetScale_x())/cellSize)+1);
    int draw_yend=  (int)Math.min(terrain_h,Math.ceil((GameWorld.cameraGet_y()+Game.scr_h/GameWorld.cameraGetScale_y())/cellSize)+1);
    
    DrawCntrl.setDepth(10000);
    
    for(int i=draw_xstart; i<draw_xend; i+=1)
    {
      for(int k=draw_ystart; k<draw_yend; k+=1)
      {
        //Floor tile.
        DrawCntrl.drawSprite(new Sprite(Spr.terrain),terrainTile[i][k],i*cellSize,k*cellSize);
        //Floor tile.
        
        //Additional sprite.
        Sprite sBuf=terrainCellSpr.get(terrain[i][k]);
        
        if (sBuf!=null)
        {
          DrawCntrl.setDepth((int)-(k+0.5)*cellSize);
          DrawCntrl.drawSprite(new Sprite(sBuf),i*cellSize+16,k*cellSize+16);
          DrawCntrl.setDepth(10000);
        }
        //Additional sprite.
      }
    }
    
  }
  
  int[][] terrainGenerate(int w,int h)
  {
    int[][] terr=new int[w][h];  
    
    for(int i=0; i<w; i+=1)
    {
      for(int k=0; k<h; k+=1)
      {terr[i][k]=0;}
    }
    
    int x0=w/2;
    int y0=h/2;
    
    int height=10;
    
    terr[x0][y0]=height;
    
    terrainGenDownhill(x0,y0,terr);  
    
    for(int i=0; i<w; i+=1)
    {
      for(int k=0; k<h; k+=1)
      {
        if (terr[i][k]==0)
        {terr[i][k]=2;}
        else
        {terr[i][k]=0;}
      }
    }
    
    return terr;
  }
  
  void terrainGenDownhill(int x_,int y_,int[][] terr)
  {
    if (terr[x_][y_]>0)
    {
      for(int i=0; i<4; i+=1)
      {
        int xx=x_+Mathe.rotate_x[i];
        int yy=y_+Mathe.rotate_y[i];
        
        //if (xx>=0 && yy>=0 && xx<terrain_w && yy<terrain_h)
        if (Mathe.irandom(1)!=0 && terr[xx][yy]<terr[x_][y_])
        {
          terr[xx][yy]=terr[x_][y_]-1;
          terrainGenDownhill(xx,yy,terr);
        }
      }
    }
  }
  
  int[][] terrainAutotile()
  {
    int[][] terrBuf=new int[128][128];
    
    int[] pow2=new int[4];
    pow2[0]=1;
    pow2[1]=2;
    pow2[2]=4;
    pow2[3]=8;
    
    for(int i=0; i<terrain_w; i+=1)
    {
      for(int k=0; k<terrain_h; k+=1)
      {
        
        if (terrain[i][k]==2)
        {
          terrBuf[i][k]=0;
          
          for(int c=0; c<4; c+=1)
          {
            int ii=i+Mathe.rotate_x[c];
            int kk=k+Mathe.rotate_y[c];
          
            if (ii>=0 && kk>=0 && ii<terrain_w && kk<terrain_h)
            if (terrain[ii][kk]!=2) 
            {terrBuf[i][k]+=pow2[c];}
          }
          
          //Diagonals check.
          if (terrBuf[i][k]==0)
          {
            for(int c=0; c<4; c+=1)
            {
              int ii=i+Mathe.rotated_x[c];
              int kk=k+Mathe.rotated_y[c];
          
              if (ii>=0 && kk>=0 && ii<terrain_w && kk<terrain_h)
              {
                if (terrain[ii][kk]!=2)
                {
                  terrBuf[i][k]=15-pow2[c];
                  break;
                }
              }
            }
            
          }
          //Diagonals check.
        }
        else
        {terrBuf[i][k]=15;}  
      }
    }
    
    return terrBuf;   
  }
  
}
