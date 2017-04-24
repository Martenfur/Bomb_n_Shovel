package pkg;

import java.util.ArrayList;
import pkg.engine.*;

/**
 * Generates terrain.
 */
public class TerrainGenerator
{
  ArrayList<int[]> islandCenters;
  int terrain_w,terrain_h;
  int[] pow2;
  TerrainGenerator(int w,int h)
  {
    terrain_w=w;
    terrain_h=h;
    
    islandCenters=new ArrayList<>();
    
    pow2=new int[4];
    pow2[0]=1;
    pow2[1]=2;
    pow2[2]=4;
    pow2[3]=8;
  }
  
  /**
   * Adds new center point for generating.
   * @param x
   * @param y 
   */
  void islandAdd(int x,int y)
  {
    int[] buf=new int[2];
    buf[0]=x;
    buf[1]=y;
    islandCenters.add(buf);
  }
  
  /**
   * Strangely enough, it generates terrain. Yep.
   * @param w
   * @param h
   * @return Fresh, ready-to-use terrain, yay!
   */
  int[][] terrainGenerate(int w,int h)
  {
    int[][] terr=new int[w][h];  
    
    int spineDir;
    int hMax=10;
    
    for(int i=0; i<islandCenters.size(); i+=1)
    { 
      spineDir=Mathe.irandom(359);
    
      int x0=islandCenters.get(i)[0],
          y0=islandCenters.get(i)[1];
      
      terrainIslandSpineGenerate(terr,spineDir,3,x0,y0,hMax);
      terrainIslandSpineGenerate(terr,spineDir+180+Mathe.irandom(-90,90),Mathe.irandom(3,5),x0,y0,hMax);
    }
    
    //Beefing up the spine.
    for(int n=hMax; n>1; n-=1)
    {
      for(int i=0; i<w; i+=1)
      {
        for(int k=0; k<h; k+=1)
        {
          if (terr[i][k]>=n)
          {
            int[] sides=new int[4];
            int sidesAm=Mathe.irandom(2,4);
            int del;
            for(int c=0; c<4-sidesAm; c+=1)
            {
              del=Mathe.irandom(3-c);
              while(sides[del]==1)
              {del+=1;}
              sides[del]=1;
            }
            
            for(int c=0; c<4; c+=1)
            {        
              if (sides[c]==0 && Terrain.get(terr,i+Mathe.rotate_x[c],k+Mathe.rotate_y[c])==0)
              {terr[i+Mathe.rotate_x[c]][k+Mathe.rotate_y[c]]=n-1;}
            }
          }
        }
      }
    }
    //Beefing up the spine.
    
    //Converting terrain from heights to tiles.
    for(int i=0; i<w; i+=1)
    {
      for(int k=0; k<h; k+=1)
      {
        if (terr[i][k]==0)
        {terr[i][k]=2;}
        else
        {
          if (terr[i][k]<4 && Mathe.irandom(3)!=0) //DEBUG
          {terr[i][k]=1;}     //DEBUG
          else
          {terr[i][k]=0;}
        }
      }
    }
    //Converting terrain from heights to tiles.
    
    //Getting rid of restricted combinations.
    int buf;
    for(int i=0; i<w; i+=1)
    {
      for(int k=0; k<h; k+=1)
      {
        buf=0;
        if (terr[i][k]==2)
        {
          for(int c=0; c<4; c+=1)
          {
            int ii=i+Mathe.rotate_x[c];
            int kk=k+Mathe.rotate_y[c]; 
            
            if (Terrain.get(terr,ii,kk)!=2) 
            {buf+=pow2[c];}
            
          }
          if (buf==5 || buf==10 || buf==7 || buf==11 || buf==13 || buf==14 || buf==15)
          {terr[i][k]=0;}
        }
        
      }
    }
    //Getting rid of restricted combinations.
    return terr;
  }
  
  /**
   * Draws island spine.
   * @param terr Terrain to draw on.
   * @param spineDir Starting direction.
   * @param bones Amount of lines to draw.
   * @param x0 Starting point.
   * @param y0 Starting point.
   * @param hMax Starting height. determines island size.
   */
  void terrainIslandSpineGenerate(int[][] terr,int spineDir,int bones,double x0,double y0,int hMax)
  {
    int spine_lmin=  8,
        spine_lmax=  10,
        diradd_min= 30,
        diradd_max=120;
    
    for(int b=0; b<bones; b+=1)
    {
      int spine_l=Mathe.irandom(spine_lmin,spine_lmax)*Mathe.choose(1,-1);
      
      double lx=Mathe.lcos(1,spineDir),
             ly=Mathe.lsin(1,spineDir);
      for(int i=0; i<spine_l; i+=1)
      {
        if (Terrain.get(terr,(int)x0,(int)y0)!=2)
        {terr[(int)x0][(int)y0]=hMax;}
        x0+=lx;
        y0+=ly;
      }
      spineDir+=Mathe.irandom(diradd_min,diradd_max);
    }
  }  
    
  /**
   * Creates new grid with tile info. Used with terr sprite only.
   * @param terr
   * @return Tiled terr.
   */
  int[][] terrainAutotile(int[][] terr)
  {
    int[][] terrBuf=new int[128][128];
    
    for(int i=0; i<terrain_w; i+=1)
    {
      for(int k=0; k<terrain_h; k+=1)
      {
        
        if (terr[i][k]==2)
        {
          terrBuf[i][k]=0;
          
          //Default autotiling.
          for(int c=0; c<4; c+=1)
          {
            int ii=i+Mathe.rotate_x[c];
            int kk=k+Mathe.rotate_y[c];
          
            if (ii>=0 && kk>=0 && ii<terrain_w && kk<terrain_h)
            if (terr[ii][kk]!=2) 
            {terrBuf[i][k]+=pow2[c];}
          }
          //Default autotiling.
          
          //Diagonals check.
          if (terrBuf[i][k]==0)
          {
            for(int c=0; c<4; c+=1)
            {
              int ii=i+Mathe.rotated_x[c];
              int kk=k+Mathe.rotated_y[c];
          
              if (ii>=0 && kk>=0 && ii<terrain_w && kk<terrain_h)
              {
                if (terr[ii][kk]!=2)
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
