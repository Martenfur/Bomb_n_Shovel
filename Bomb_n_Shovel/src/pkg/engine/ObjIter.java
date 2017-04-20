package pkg.engine;

import pkg.GameObject;

public class ObjIter
{
  double counter=0;
  Obj.oid type=Obj.oid.all;
  
  public ObjIter(Obj.oid type_arg)
  {
    type=type_arg;
    
    while(!Obj.objIndexCmp(Obj.objects.get((int)counter),type))
    {
      counter+=1;
      if (counter>=Obj.objectsAm)
      {
        counter=Obj.objectsAm;
        break;
      }
    }
  }
 
  ////////////////////////////////////////////////
  public GameObject get()
  {return Obj.objects.get((int)counter);}
  ////////////////////////////////////////////////
  
  
  ////////////////////////////////////////////////
  public boolean end()
  {return (counter<Obj.objectsAm);}
  ////////////////////////////////////////////////
  
  
  ////////////////////////////////////////////////
  public void inc()
  {
    counter+=1;
    if (counter>=Obj.objectsAm)
    {
      counter=Obj.objectsAm;
      return;
    }
    
    while(!Obj.objIndexCmp(Obj.objects.get((int)counter),type))
    {
      counter+=1;
      if (counter>=Obj.objectsAm)
      {
        counter=Obj.objectsAm;
        break;
      }
    }
  }
  ////////////////////////////////////////////////
}
