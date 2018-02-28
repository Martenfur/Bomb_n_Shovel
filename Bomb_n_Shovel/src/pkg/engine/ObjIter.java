package pkg.engine;

import pkg.GameObject;

/**
 * Object iterator. Iterates all the objects of the same type. Should be used
 * like this: for(ObjIter it = new ObjIter(type); !it.end(); it.inc())
 * {it.get();}
 */
public class ObjIter
{

	double counter = 0;
	Class type;

	/**
	 * Main constructor.
	 *
	 * @param type_arg
	 */
	public ObjIter(Class type_arg)
	{
		type = type_arg;

		while (!Obj.objIndexCmp(Obj.objects.get((int) counter), type))
		{
			counter += 1;
			if (counter >= Obj.objectsAm)
			{
				counter = Obj.objectsAm;
				break;
			}
		}
	}

	////////////////////////////////////////////////
	/**
	 * @return Current object.
	 */
	public GameObject get()
	{
		return Obj.objects.get((int) counter);
	}
	////////////////////////////////////////////////

	////////////////////////////////////////////////
	/**
	 * Checks if list is ended.
	 *
	 * @return
	 */
	public boolean end()
	{
		return (counter < Obj.objectsAm);
	}
	////////////////////////////////////////////////

	////////////////////////////////////////////////
	/**
	 * Increments iterator.
	 */
	public void inc()
	{
		counter += 1;
		if (counter >= Obj.objectsAm)
		{
			counter = Obj.objectsAm;
			return;
		}

		while (!Obj.objIndexCmp(Obj.objects.get((int) counter), type))
		{
			counter += 1;
			if (counter >= Obj.objectsAm)
			{
				counter = Obj.objectsAm;
				break;
			}
		}
	}
	////////////////////////////////////////////////
}
