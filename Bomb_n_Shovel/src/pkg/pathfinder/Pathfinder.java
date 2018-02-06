package pkg.pathfinder;

import java.util.ArrayList;
import pkg.terrain.Terrain;
import pkg.terrain.TileProp;
import pkg.engine.*;

/**
 * Generates path on given int[][] array. Treats any non-zero value as an
 * obstacle. Ignores diagonals. Returns {@link pkg.pathfinder.PathPoint} or
 * null, if path wasn't constructed.
 */
public class Pathfinder
{

	int[][] wallGrid;
	int grid_w, grid_h;

	/**
	 * Main constructor.
	 *
	 * @param grid_arg grid for which you want to find path.
	 */
	public Pathfinder(int[][] grid_arg)
	{
		wallGrid = grid_arg;
		grid_w = wallGrid.length;
		grid_h = wallGrid[0].length;
	}

	/**
	 * A* pathfinding. Treats non-zero values as obstacles.
	 *
	 * @param sx x of start point.
	 * @param sy y of start point.
	 * @param fx x of finish point.
	 * @param fy y of finish point.
	 * @return Path list or null, if path wasn't found.
	 */
	public PathPoint pathFind(int sx, int sy, int fx, int fy)
	{
		//Beginning path from wall cell? Seriously?
		if (!TileProp.isPassable(wallGrid[sx][sy]))
		{
			return null;
		}
		//Beginning path from wall cell? Seriously?

		//If points are THAT close to each other, no need in fancy pathfinding.
		if (Mathe.pointDistance(sx, sy, fx, fy) == 1)
		{
			if (TileProp.isPassable(wallGrid[fx][fy]))
			{
				return new PathPoint(fx, fy);
			}
			else
			{
				return null;
			}
		}
		//If points are THAT close to each other, no need in fancy pathfinding.

		//And if they are even closer... you got it.
		if (sx == fx && sy == fy)
		{
			return null;
		}
		//And if they are even closer... you got it.

		ArrayList<PathCell> openList = new ArrayList<>();
		ArrayList<PathCell> closedList = new ArrayList<>();

		PathCell[][] pathGrid = new PathCell[grid_w][grid_h];
		pathGrid[sx][sy] = new PathCell(sx, sy, 0, getDist(sx, sy, fx, fy));

		openList.add(pathGrid[sx][sy]);

		try
		{

			while (!openList.isEmpty())
			{
				PathCell ptCheck = listFindLesser(openList);
				closedList.add(ptCheck);
				///////////////////////////////////////////////////
				int px, py;
				for (int i = 0; i < 4; i += 1)
				{
					px = ptCheck.x + Mathe.rotate_x[i];
					py = ptCheck.y + Mathe.rotate_y[i];
					if (TileProp.isPassable(Terrain.get(wallGrid, px, py)))
					{
						if (pathGrid[px][py] == null)
						{
							pathGrid[px][py] = new PathCell(px, py, ptCheck.dist_s + 1, getDist(px, py, fx, fy));
							openList.add(pathGrid[px][py]);
						}
						else
						{
							if (pathGrid[px][py].dist_f > ptCheck.dist_f)
							{
								pathGrid[px][py].dist_f = ptCheck.dist_f;
							}
						}
						ptCheck.surrounders[i] = pathGrid[px][py];
					}
				}

				if (ptCheck.dist_f == 1)
				{
					PathPoint pathBuf = pathConstruct(ptCheck);

					if (TileProp.isPassable(wallGrid[fx][fy]))
					{
						pathBuf.add(fx, fy);
					}

					return pathBuf;
				}

				///////////////////////////////////////////////////
			}

		}
		catch (OutOfMemoryError e)
		{
			//System.out.println("OUTOFMEMORY");
			return null;
		}

		return null;

	}

	/**
	 * Constructs path using precalculated {@link pkg.pathfinder.PathCell} array.
	 *
	 * @param ptStart Starting point in grid.
	 * @return Path list or null, if path wasn't found.
	 */
	PathPoint pathConstruct(PathCell ptStart)
	{
		ArrayList<PathCell> resList = new ArrayList<>();
		resList.add(ptStart);
		PathCell ptBuf = ptStart;

		while (true)
		{
			PathCell ptLesser = null;
			PathCell ptBufS;

			for (int i = 0; i < 4; i += 1)
			{
				ptBufS = ptBuf.surrounders[i];

				if (ptBufS != null)
				{
					if (ptLesser == null)
					{
						ptLesser = ptBufS;
					}
					else
					{
						if (ptLesser.getValue() > ptBufS.getValue() || (ptLesser.getValue() == ptBufS.getValue() && ptLesser.dist_s > ptBufS.dist_s))
						{
							ptLesser = ptBufS;
						}
					}
				}
			}

			if (ptLesser != null)
			{
				resList.add(ptLesser);
				ptBuf = ptLesser;
				if (ptLesser.dist_s == 0)
				{
					//Generating path.  
					PathCell cBuf = resList.get(resList.size() - 2);
					PathPoint resPath = new PathPoint(cBuf.x, cBuf.y);

					for (int i = resList.size() - 3; i >= 0; i -= 1)
					{
						cBuf = resList.get(i);
						resPath.add(cBuf.x, cBuf.y);
					}

					return resPath;
					//Generating path.
				}
			}
			else
			{
				System.out.println("PATH CONSTRUCT ERROR");
				return null;
			}
		}
	}

	/**
	 * Finds lesser element of {link pkg.pathfinding.PathCell} array.
	 *
	 * @param list
	 * @return Lesser element.
	 */
	PathCell listFindLesser(ArrayList<PathCell> list)
	{
		if (list.isEmpty())
		{
			return null;
		}

		PathCell ptLesser = list.get(0);
		PathCell ptBuffer;
		int s = list.size(),
			iLesser = 0;
		for (int i = 1; i < s; i += 1)
		{
			ptBuffer = list.get(i);
			if (ptBuffer.getValue() < ptLesser.getValue())
			{
				ptLesser = ptBuffer;
				iLesser = i;
			}
		}
		list.remove(iLesser);

		return ptLesser;
	}

	/**
	 * Quick distance finding for grid.
	 *
	 * @param sx x of start point.
	 * @param sy y of start point.
	 * @param fx x of finish point.
	 * @param fy y of finish point.
	 * @return Rough distance between two points. Not accurate, but good enough.
	 */
	int getDist(int sx, int sy, int fx, int fy)
	{
		return Math.abs(sx - fx) + Math.abs(sy - fy);
	}

}
