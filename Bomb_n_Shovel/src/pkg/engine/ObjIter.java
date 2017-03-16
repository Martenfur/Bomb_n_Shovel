package pkg.engine;

import pkg.GameObject;

public class ObjIter
{
  double counter=0;
  ObjCntrl.oid type=ObjCntrl.oid.all;
  
  public ObjIter(ObjCntrl.oid type_arg)
  {
    type=type_arg;
    
    while(!ObjCntrl.objIndexCmp(ObjCntrl.objects.get((int)counter),type))
    {
      counter+=1;
      if (counter>=ObjCntrl.objectsAm)
      {
        counter=ObjCntrl.objectsAm;
        break;
      }
    }
  }
 
  ////////////////////////////////////////////////
  public GameObject get()
  {return ObjCntrl.objects.get((int)counter);}
  ////////////////////////////////////////////////
  
  
  ////////////////////////////////////////////////
  public boolean end()
  {return (counter<ObjCntrl.objectsAm);}
  ////////////////////////////////////////////////
  
  
  ////////////////////////////////////////////////
  public void inc()
  {
    counter+=1;
    if (counter>=ObjCntrl.objectsAm)
    {
      counter=ObjCntrl.objectsAm;
      return;
    }
    
    while(!ObjCntrl.objIndexCmp(ObjCntrl.objects.get((int)counter),type))
    {
      counter+=1;
      if (counter>=ObjCntrl.objectsAm)
      {
        counter=ObjCntrl.objectsAm;
        break;
      }
    }
  }
  ////////////////////////////////////////////////
}
