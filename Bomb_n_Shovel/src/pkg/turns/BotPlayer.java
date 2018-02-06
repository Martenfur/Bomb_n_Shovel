package pkg.turns;

import java.util.ArrayList;
import pkg.Entity;
import pkg.Peasant;
import pkg.engine.Mathe;
import pkg.engine.Obj;
import pkg.engine.ObjIter;
import pkg.foxoft.bombnshovel.net.Cmd;
import pkg.pathfinder.PathPoint;
import pkg.pathfinder.Pathfinder;
import pkg.terrain.Terrain;

/**
 *
 * @author gn.fur
 */
public class BotPlayer extends Player
{

	boolean moved;
	int delayAl;
	int delayTime;

	public BotPlayer()
	{
		client = null;
		moved = false;

		delayAl = 0;
		delayTime = 60;
	}

	@Override
	public void STEP()
	{
		super.STEP();

		if (isMyTurn() && !peasants.isEmpty() && peasants.size() > peasantCur)
		{
			AI();
		}

		if (!isMyTurn())
		{
			moved = false;
			delayAl = delayTime;
		}

	}

	public void AI()
	{
		Peasant pCur = peasants.get(peasantCur);

		delayAl -= 1;

		if (delayAl <= 0 && pCur.initiative && !pCur.moving)
		{

			if (moved)
			{
				pCur.command = new Cmd("endturn");
			}
			else
			{
				if (pCur.pathList == null)
				{

					//Picking up an enemy.
					ArrayList<Peasant> enemies = new ArrayList<>();
					for (ObjIter it = new ObjIter(Obj.oid.peasant); it.end(); it.inc())
					{
						if (tid != ((Peasant) it.get()).tid)
						{
							enemies.add((Peasant) it.get());
						}
					}
					//Picking up an enemy.

					int xx, yy;

					if (!enemies.isEmpty())
					{
						Peasant enemy = null;//=enemies.get(Mathe.irandom(0,enemies.size()-1));

						PathPoint pathClosest = null;

						for (ObjIter it = new ObjIter(Obj.oid.entity); it.end(); it.inc())
						{
							if (it.get() != pCur && !((Entity) it.get()).passable)
							{
								((Entity) it.get()).tileStore();
							}
						}

						for (int i = 0; i < enemies.size(); i += 1)
						{
							if (enemies.get(i) != null)
							{
								Pathfinder pathfinder = new Pathfinder(Terrain.terrain);
								PathPoint pathBuf = pathfinder.pathFind((int) pCur.x / Terrain.cellSize, (int) pCur.y / Terrain.cellSize,
									(int) enemies.get(i).x / Terrain.cellSize, (int) enemies.get(i).y / Terrain.cellSize);

								if (pathBuf != null && (pathClosest == null || pathBuf.length() < pathClosest.length()))
								{
									pathClosest = pathBuf;
									enemy = enemies.get(i);
								}
							}
						}

						for (ObjIter it = new ObjIter(Obj.oid.entity); it.end(); it.inc())
						{
							if (it.get() != pCur && !((Entity) it.get()).passable)
							{
								((Entity) it.get()).tileRestore();
							}
						}

						if (enemy != null)
						{
							xx = (int) (enemy.x) / Terrain.cellSize;
							yy = (int) (enemy.y) / Terrain.cellSize;
						}
						else
						{
							xx = (int) (pCur.x + Mathe.irandom(-256, 256)) / Terrain.cellSize;
							yy = (int) (pCur.y + Mathe.irandom(-256, 256)) / Terrain.cellSize;
						}
					}
					else
					{
						xx = (int) (pCur.x + Mathe.irandom(-256, 256)) / Terrain.cellSize;
						yy = (int) (pCur.y + Mathe.irandom(-256, 256)) / Terrain.cellSize;
					}

					pCur.command = new Cmd("setpath", xx, yy);
				}
				else
				{
					pCur.command = new Cmd("move");
					moved = true;
				}
			}
		}
	}

}
