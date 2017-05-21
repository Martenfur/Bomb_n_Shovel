package pkg;

import pkg.engine.Obj;
import pkg.engine.ObjIter;

/**
 *
 * @author gn.fur
 */
public class MatchResult extends GameObject
{
  public MatchResult()
  {
    super();
    
    for(ObjIter it=new ObjIter(Obj.oid.terrain); it.end(); it.inc())
    {Obj.objDestroy(it.get());}
    
    new Lobby();
    
    Obj.objDestroy(this);
  }
}
