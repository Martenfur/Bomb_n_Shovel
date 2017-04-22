package pkg;

import pkg.engine.*;

/**
 * Generates terrain.
 */
public class TerrainGenerator
{
  int terrain_w,terrain_h;
  int[] pow2;
  TerrainGenerator(int w,int h)
  {
    terrain_w=w;
    terrain_h=h;
    
    pow2=new int[4];
    pow2[0]=1;
    pow2[1]=2;
    pow2[2]=4;
    pow2[3]=8;
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
    
    for(int i=0; i<w; i+=1)
    {
      for(int k=0; k<h; k+=1)
      {terr[i][k]=0;}
    }
    
    int spineDir;
    int hMax=10;
    
    for(int i=0; i<4; i+=1)
    {
      double x0=w/2+Mathe.irandom(-32,32),
             y0=h/2+Mathe.irandom(-32,32);
      
      spineDir=Mathe.irandom(359);
    
      terrainIslandSpineGenerate(terr,spineDir,3,x0,y0,hMax);
      terrainIslandSpineGenerate(terr,spineDir+180+Mathe.irandom(-90,90),3,x0,y0,hMax);
    }
    
    //Beefing up the spine.
    for(int n=hMax; n>1; n-=1)
    {
      for(int i=1; i<w-1; i+=1)
      {
        for(int k=1; k<h-1; k+=1)
        {
          if (terr[i][k]>=n)
          {
            for(int c=0; c<4; c+=1)
            {
              if ((Mathe.irandom(2)!=0 || n==hMax) && terr[i+Mathe.rotate_x[c]][k+Mathe.rotate_y[c]]==0)
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
        {terr[i][k]=0;}
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
             
            if (ii>=0 && kk>=0 && ii<terrain_w && kk<terrain_h)
            {
              if (terr[ii][kk]!=2) 
              {buf+=pow2[c];}
            }
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
    int spine_lmin=  4,
        spine_lmax=  6,
        diradd_min=-90,
        diradd_max= 90;
    
    for(int b=0; b<bones; b+=1)
    {
      int spine_l=Mathe.irandom(spine_lmin,spine_lmax);
      
      double lx=Mathe.lcos(1,spineDir),
             ly=Mathe.lsin(1,spineDir);
      for(int i=0; i<spine_l; i+=1)
      {
        terr[(int)x0][(int)y0]=hMax;
        x0+=lx;
        y0+=ly;
      }
      spineDir+=Mathe.irandom(diradd_min,diradd_max);
    }
  }  
    
  /**
   * Creates new grid with tile info. Used with terrain sprite only.
   * @param terrain
   * @return Tiled terrain.
   */
  int[][] terrainAutotile(int[][] terrain)
  {
    int[][] terrBuf=new int[128][128];
    
    for(int i=0; i<terrain_w; i+=1)
    {
      for(int k=0; k<terrain_h; k+=1)
      {
        
        if (terrain[i][k]==2)
        {
          terrBuf[i][k]=0;
          
          //Default autotiling.
          for(int c=0; c<4; c+=1)
          {
            int ii=i+Mathe.rotate_x[c];
            int kk=k+Mathe.rotate_y[c];
          
            if (ii>=0 && kk>=0 && ii<terrain_w && kk<terrain_h)
            if (terrain[ii][kk]!=2) 
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
