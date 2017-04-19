package pkg.engine;

import java.util.Random;

public class Mathe
{  
  //0   -  1, 0 
  //90  -  0,-1
  //180 - -1, 0
  //270 -  0, 1
  
  //Plain.
  public static final int rotate_x[]={1, 0,-1,0};
  public static final int rotate_y[]={0,-1, 0,1};
  
  //Diagonal.
  public static final int rotated_x[]={1,-1,-1,1};
  public static final int rotated_y[]={-1,-1,1,1};
  
  
  public static int rotateConvert(int x,int y)
  {
    for(int i=0; i<4; i+=1)
    {
      if (rotate_x[i]==x && rotate_y[i]==y)
      {return i;}
    }
    return -1;
  }
  
  
  //MATH/////////////////////////////////////////////////////////////////////////////
  public static double zerosign(double x)
  {
    if (x==0)
    {return 1;}
    else
    {return Math.signum(x);}
  }
  
  public static double lerp(double xp,double x,double xf)
  {
    if (xp==xf)
    {return 0;}
    else
    {return (xp-x)/(xp-xf);}
  }
  
  public static boolean pointInRectangle(double xm,double ym,double x1,double y1,double x2,double y2)
  {return (xm>x1) && (xm<x2) && (ym>y1) && (ym<y2);}

  public static boolean rectangleInRectangle(double x1a,double y1a,double x2a,double y2a,
                                             double x1b,double y1b,double x2b,double y2b)
  {
    double wa=Math.abs(x2a-x1a)/2.0;
    double ha=Math.abs(y2a-y1a)/2.0;
    double wb=Math.abs(x2b-x1b)/2.0;
    double hb=Math.abs(y2b-y1b)/2.0;

    return (Math.abs((x1b+wb)-(x1a+wa)) < wa+wb) && (Math.abs((y1b+hb)-(y1a+ha)) < ha+hb);
  }
  //MATH/////////////////////////////////////////////////////////////////////////////
  
  
  
  //TRIGONOMETRY/////////////////////////////////////////////////////////////////////
  public static double pi=3.14159265359;
  
  public static double lcos(double len,double dir)
  {return  len*Math.cos(Math.toRadians(dir));}

  public static double lsin(double len,double dir)
  {return -len*Math.sin(Math.toRadians(dir));}

  public static double pointDistance(double x1,double y1,double x2,double y2)
  {return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));}

  public static double pointDirection(double x1,double y1,double x2,double y2)
  {
    double deg = -90.0+Math.atan2(x2-x1,y2-y1)/(pi*2.0)*359.0;
    if (deg<0)
    {deg+=360.0;}
    return deg;
  }
  
  public static double angleDifference(double ang1,double ang2)
  {
    double ang=ang2-ang1;

    while(ang>=360)
    {ang-=360;}
    while(ang<=-360)
    {ang+=360;}

    if (ang<-180)
    {ang+=360;}
    if (ang>180)
    {ang-=360;}

    return ang;
  }
  //TRIGONOMETRY/////////////////////////////////////////////////////////////////////
  
  
  
  //RANDOM///////////////////////////////////////////////////////////////////////////
  static Random randomizer;
  static long randomizerSeed;
  
  public static void randomize()
  {
    randomizer=new Random((long)System.nanoTime());
    randomizerSeed=(long)System.nanoTime();
  }
  
  public static void randomSetSeed(long seed)
  {
    randomizer=new Random(seed);
    randomizerSeed=seed;
  }
  
  public static long randomGetSeed()
  {return randomizerSeed;}
  
  public static double random()
  {return randomizer.nextDouble();}
  
  public static double random(double x)
  {return randomizer.nextDouble()*x;}
  
  public static double random(double x1, double x2)
  {return x1+randomizer.nextDouble()*Math.abs(x2-x1);}
  
  public static int irandom(int x)
  {return randomizer.nextInt(x+1);}
  
  public static int irandom(int x1,int x2)
  {return x1+randomizer.nextInt(Math.abs(x2-x1));}
  //RANDOM///////////////////////////////////////////////////////////////////////////
  
  
}
