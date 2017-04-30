package pkg.engine;

import java.util.Random;

/**
 * Some useful functions here and there.
 */
public class Mathe
{  
  
  //ROTATING/////////////////////////////////////////////////////////////////////////
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
  
  /**
   * Converts plain vector into an index for rotation matrix. 
   * @param x
   * @param y
   * @return Index for rotation matrix.
   */
  public static int rotateConvert(int x,int y)
  {
    x=(int)Math.signum(x);
    y=(int)Math.signum(y);

    for(int i=0; i<4; i+=1)
    {
      if (rotate_x[i]==x && rotate_y[i]==y)
      {return i;}
    }
    return -1;
  }
  //ROTATING/////////////////////////////////////////////////////////////////////////
  
  //MATH/////////////////////////////////////////////////////////////////////////////
  /**
   * Works just like regular sign, but in case of
   * 0 returns 1.
   */
  public static double zerosign(double x)
  {
    if (x==0)
    {return 1;}
    else
    {return Math.signum(x);}
  }
  
  /**
   * Linear interpolation. Shows how far x is between xp and xf.
   * @param xp First border value.
   * @param x Value to check.
   * @param xf Second border value.
   * @return if xp=x, returns 0, if xf=x returns 1.
   */
  public static double lerp(double xp,double x,double xf)
  {
    if (xp==xf)
    {return 0;}
    else
    {return (xp-x)/(xp-xf);}
  }
  
  /**
   * Checks if point intersects with rectangle.
   * @param xm Checkable point.
   * @param ym Checkable point.
   * @param x1 First rectangle point.
   * @param y1 First rectangle point.
   * @param x2 Second rectangle point.
   * @param y2 Second rectangle point.
   */
  public static boolean pointInRectangle(double xm,double ym,double x1,double y1,double x2,double y2)
  {return (xm>x1) && (xm<x2) && (ym>y1) && (ym<y2);}
  
  /**
   * Checks if rectangle intersects with rectangle.
   * @param x1a First rectangle A point.
   * @param y1a First rectangle A point.
   * @param x2a Second rectangle A point.
   * @param y2a Second rectangle A point.
   * @param x1b First rectangle B point.
   * @param y1b First rectangle B point.
   * @param x2b Second rectangle B point.
   * @param y2b Second rectangle B point.
   * @return 
   */ 
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
  
  /**
   * Cos multiplied by length. Uses degrees.
   */
  public static double lcos(double len,double dir)
  {return  len*Math.cos(Math.toRadians(dir));}

  /**
   * Sin multiplied by length. Uses degrees.
   */
  public static double lsin(double len,double dir)
  {return -len*Math.sin(Math.toRadians(dir));}

  /**
   * Calculates distance between two points.
   * @param x1 First point x.
   * @param y1 First point y.
   * @param x2 Second point x.
   * @param y2 Second point y.
   * @return Distance.
   */
  public static double pointDistance(double x1,double y1,double x2,double y2)
  {return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));}
  
  /**
   * Calculates direction between two points.
   * @param x1 First point x.
   * @param y1 First point y.
   * @param x2 Second point x.
   * @param y2 Second point y.
   * @return Direction in degrees.
   */
  public static double pointDirection(double x1,double y1,double x2,double y2)
  {
    double deg = -90.0+Math.atan2(x2-x1,y2-y1)/(pi*2.0)*359.0;
    if (deg<0)
    {deg+=360.0;}
    return deg;
  }
  
  /**
   * Calculates difference between two angles. Uses degrees.
   * @param ang1 First angle.
   * @param ang2 Second angle.
   * @return Angle between -180 and 180.
   */
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
  
  /**
   * Sets seed based on current time.    
   * !!!EITHER THIS OR randomSetSeed() 
   * MUST BE CALLED BEFORE USING ANY OF OTHER RANDOM FUNCTIONS!!!
   */
  public static void randomize()
  {
    randomizer=new Random((long)System.nanoTime());
    randomizerSeed=(long)System.nanoTime();
  }
  
  /**
   * Sets seed manually.    
   * !!!EITHER THIS OR randomize() 
   * MUST BE CALLED BEFORE USING ANY OF OTHER RANDOM FUNCTIONS!!!
   * @param seed Seed.
   */
  public static void randomSetSeed(long seed)
  {
    randomizer=new Random(seed);
    randomizerSeed=seed;
  }
  
  /**
   * @return Current seed. 
   */
  public static long randomGetSeed()
  {return randomizerSeed;}
  
  /**
   * @return Value between 0 and 1.
   */
  public static double random()
  {return randomizer.nextDouble();}
  
  /**
   * @return Value between 0 and x.
   * @param x
   */
  public static double random(double x)
  {return randomizer.nextDouble()*x;}
  
  /**
   * @param x1
   * @param x2
   * @return Value between x1 and x2.
   */
  public static double random(double x1, double x2)
  {
    if (x1!=x2)
    {return x1+randomizer.nextDouble()*Math.abs(x2-x1);}
    else
    {return x1;}
  }
  
  /**
   * @return Integer value between 0 and x.
   * @param x
   */
  public static int irandom(int x)
  {return randomizer.nextInt(x+1);}
  
  /**
   * @param x1
   * @param x2
   * @return Integer value between x1 and x2.
   */
  public static int irandom(int x1,int x2)
  {
    if (x1!=x2)
    {return x1+randomizer.nextInt(Math.abs(x2-x1));}
    else
    {return x1;}
  }
  
  /**
   * Returns one of given arguments. Can be used with any type.
   * @param <T> 
   * @param arr
   * @return One of arguments.
   */
  public static <T> T choose(T... arr)
  {return arr[irandom(arr.length-1)];}
  //RANDOM///////////////////////////////////////////////////////////////////////////
   
}
