package pkg.terrain;

import java.util.ArrayList;
import pkg.engine.*;
import pkg.pathfinder.Pathfinder;

/**
 * Generates terrain.
 */
public class TerrainGenerator
{

	ArrayList<int[]> islandCenters;
	int terrain_w, terrain_h;
	int[] pow2;
	long seed;

	TerrainGenerator(int w, int h, long seed_arg)
	{
		terrain_w = w;
		terrain_h = h;
		seed_arg = seed;

		islandCenters = new ArrayList<>();

		pow2 = new int[4];
		pow2[0] = 1;
		pow2[1] = 2;
		pow2[2] = 4;
		pow2[3] = 8;
	}

	/**
	 * Adds new island for generating.
	 * 
	 * @param x Center x of island.
	 * @param y Center y of island.
	 * @param h Starting height. Determines island size.
	 */
	void islandAdd(int x, int y, int h)
	{
		int[] buf = new int[3];
		buf[0] = x;
		buf[1] = y;
		buf[2] = h;
		islandCenters.add(buf);
	}

	/**
	 * Strangely enough, it generates terrain. Yep.
	 *
	 * @param w
	 * @param h
	 * @return Fresh, ready-to-use terrain, yay!
	 */
	int[][] terrainGenerate(int w, int h)
	{
		Mathe.randomPush();
		Mathe.randomSetSeed(seed);

		int[][] terr = new int[w][h];

		//Generating spine.
		terrainSpineGenerate(terr);
		//Generating spine.

		//Beefing up the spine.
		terrainBeefup(terr);
		//Beefing up the spine.

		//Converting terrain from heights to tiles.
		for (int i = 0; i < w; i += 1)
		{
			for (int k = 0; k < h; k += 1)
			{
				if (terr[i][k] == 0)
				{
					terr[i][k] = 2;
				}
				else
				{
					if (terr[i][k] < 3 && Mathe.irandom(2) == 0)
					{
						terr[i][k] = 1;
					}
					else
					{
						terr[i][k] = 0;
					}
				}
			}
		}
		//Converting terrain from heights to tiles.

		//Getting rid of restricted combinations.
		for(int rep = 0; rep < 4; rep += 1)
		{
			int buf;
			for (int i = 0; i < w; i += 1)
			{
				for (int k = 0; k < h; k += 1)
				{
					buf = 0;
					if (terr[i][k] == 2)
					{
						int counter = 0;
						for (int c = 0; c < 4; c += 1)
						{
							int ii = i + Mathe.rotate_x[c];
							int kk = k + Mathe.rotate_y[c];		
							
							if (Terrain.get(terr, ii, kk) != 2)
							{
								buf += pow2[c];
								counter += 1;
							}

						}
						if (counter > 2 || buf == 5 || buf == 10 || buf == 7 || buf == 11 || buf == 13 || buf == 14 || buf == 15)
						{
							terr[i][k] = 1;
						}
						else
						{
							counter = 0;
							if (Terrain.get(terr, i + 1, k + 1) != 2)
							{counter += 1;}
							if (Terrain.get(terr, i - 1, k + 1) != 2)
							{counter += 1;}
							if (Terrain.get(terr, i - 1, k - 1) != 2)
							{counter += 1;}
							if (Terrain.get(terr, i + 1, k - 1) != 2)
							{counter += 1;}
							if (counter > 2)
							{
								terr[i][k] = 0;
							}
						}
					}

				}
			}
		}
		//Getting rid of restricted combinations.

		//Generating trees.
		treesGenerate(terr);
		//Generating trees.

		//Generating bridges.
		for (int i = 0; i < islandCenters.size(); i += 1)
		{
			for (int k = i + 1; k < islandCenters.size(); k += 1)
			{
				Pathfinder pf = new Pathfinder(terr);
				if (pf.pathFind(islandCenters.get(i)[0], islandCenters.get(i)[1],
					islandCenters.get(k)[0], islandCenters.get(k)[1]) == null)
				{
					terrainBridgeAdd(terr, islandCenters.get(i)[0], islandCenters.get(i)[1],
						islandCenters.get(k)[0], islandCenters.get(k)[1]);
				}
			}
		}
		//Generating bridges.
		
		generateForestWall(terr);
		
		//Clearing some space around starting points.
		for (int i = 0; i < terr.length; i += 1)
		{
			for (int k = 0; k < terr[0].length; k += 1)
			{
				if (Mathe.pointDistance(i, k, islandCenters.get(0)[0], islandCenters.get(0)[1]) < 4
					|| Mathe.pointDistance(i, k, islandCenters.get(1)[0], islandCenters.get(1)[1]) < 4)
				{
					terr[i][k] = 0;
				}
			}
		}
		//Clearing some space around starting points.

		
		Mathe.randomPop();

		return terr;
	}

	/**
	 * Draws island spine.
	 *
	 * @param terr Terrain to draw on.
	 * @param spineDir Starting direction.
	 * @param bones Amount of lines to draw.
	 * @param x0 Starting point.
	 * @param y0 Starting point.
	 * @param hMax Starting height. determines island size.
	 */
	void terrainIslandSpineGenerate(int[][] terr, int spineDir, int bones, double x0, double y0, int hMax)
	{
		int spine_lmin = 8,
			spine_lmax = 10,
			diradd_min = 30,
			diradd_max = 120;

		for (int b = 0; b < bones; b += 1)
		{
			int spine_l = Mathe.irandom(spine_lmin, spine_lmax) * Mathe.choose(1, -1);

			double lx = Mathe.lcos(1, spineDir),
				ly = Mathe.lsin(1, spineDir);
			for (int i = 0; i < spine_l; i += 1)
			{
				if (Terrain.get(terr, (int) x0, (int) y0) != 2)
				{
					terr[(int) x0][(int) y0] = hMax;
				}
				x0 += lx;
				y0 += ly;
			}
			spineDir += Mathe.irandom(diradd_min, diradd_max);
		}
	}

	/**
	 * Generates spine from list.
	 *
	 * @param terr
	 */
	void terrainSpineGenerate(int[][] terr)
	{
		int spineDir, h0;

		for (int i = 0; i < islandCenters.size(); i += 1)
		{
			spineDir = Mathe.irandom(359);

			int x0 = islandCenters.get(i)[0],
				y0 = islandCenters.get(i)[1];

			h0 = islandCenters.get(i)[2];

			terrainIslandSpineGenerate(terr, spineDir, 3, x0, y0, h0);
			terrainIslandSpineGenerate(terr, spineDir + 180 + Mathe.irandom(-90, 90), Math.max(1, Mathe.irandom(h0 / 3, h0 / 2)), x0, y0, h0);
		}
	}

	/**
	 * Beefing up the spine.
	 *
	 * @param terr
	 */
	void terrainBeefup(int[][] terr)
	{
		for (int n = 10; n > 1; n -= 1)
		{
			for (int i = 0; i < terr.length; i += 1)
			{
				for (int k = 0; k < terr[0].length; k += 1)
				{
					if (terr[i][k] >= n)
					{
						int[] sides = new int[4];
						int sidesAm = Mathe.irandom(2, 4);
						int del;
						for (int c = 0; c < 4 - sidesAm; c += 1)
						{
							del = Mathe.irandom(3 - c);
							while (sides[del] == 1)
							{
								del += 1;
							}
							sides[del] = 1;
						}

						for (int c = 0; c < 4; c += 1)
						{
							if (sides[c] == 0 && Terrain.get(terr, i + Mathe.rotate_x[c], k + Mathe.rotate_y[c]) == 0)
							{
								terr[i + Mathe.rotate_x[c]][k + Mathe.rotate_y[c]] = n - 1;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Creates new grid with tile info. Used with terr sprite only.
	 *
	 * @param terr Reference terrain.
	 * @param terrSpr Empty Sprite array.
	 * @return Tiled terr.
	 */
	int[][] terrainAutotile(int[][] terr, Sprite[][] terrSpr)
	{
		int[][] terrTiles = new int[terr.length][terr[0].length];

		for (int i = 0; i < terr.length; i += 1)
		{
			for (int k = 0; k < terr[0].length; k += 1)
			{

				//TILING ISLANDS/////////////////////////////////////////////////////////
				if (!TileProp.isGround(terr[i][k]))
				{
					terrTiles[i][k] = 0;

					//Default autotiling.
					for (int c = 0; c < 4; c += 1)
					{
						int ii = i + Mathe.rotate_x[c];
						int kk = k + Mathe.rotate_y[c];

						if (TileProp.isGround(Terrain.get(terr, ii, kk)))
						{
							terrTiles[i][k] += pow2[c];
						}
					}
					//Default autotiling.

					//Diagonals check.
					if (terrTiles[i][k] == 0)
					{
						for (int c = 0; c < 4; c += 1)
						{
							int ii = i + Mathe.rotated_x[c];
							int kk = k + Mathe.rotated_y[c];

							if (TileProp.isGround(Terrain.get(terr, ii, kk)))
							{
								terrTiles[i][k] = 15 - pow2[c];
								break;
							}

						}
					}
					//Diagonals check.
				}
				else
				{
					terrTiles[i][k] = 15;
				}
				//TILING ISLANDS/////////////////////////////////////////////////////////

				if (TileProp.getSpr(terr[i][k]) != null)
				{
					terrSpr[i][k] = new Sprite(TileProp.getSpr(terr[i][k]));
				}

				//TILING COASTS/////////////////////////////////////////////////////////
				if (terr[i][k] == 3)
				{
					//Tiling bridges.
					int tileBuf = 0;
					for (int c = 0; c < 4; c += 1)
					{
						int ii = i + Mathe.rotate_x[c];
						int kk = k + Mathe.rotate_y[c];

						if (Terrain.get(terr, ii, kk) == 3)
						{
							tileBuf += pow2[c];
						}
					}
					//Tiling bridges.

					if (tileBuf == 15)
					{
						tileBuf = 15 + 4 + 1;
					}
					else
					{
						if (tileBuf / 5 * 5 == tileBuf) //Straight parts.
						{
							tileBuf = 15 + 5;
						}
						else
						{
							if (tileBuf / 3 * 3 == tileBuf) //Corners.
							{
								tileBuf = 15 + tileBuf / 3;
							}
							else
							{
								tileBuf = 15 + 4 + 1;
							}
						}
					}

					if (terrTiles[i][k] != 0)
					{
						terrSpr[i][k] = new Sprite(Spr.terrain);
						terrSpr[i][k].setFrame(20); //Connected to land = no corner tiles.
						terrSpr[i][k].setOffset(Terrain.cellSize / 2, Terrain.cellSize / 2);
					}
					else
					{
						terrTiles[i][k] = tileBuf;
					}

				}
				//TILING COASTS/////////////////////////////////////////////////////////

			}

		}

		for (int i = 0; i < terr.length; i += 1)
		{
			for (int k = 0; k < terr[0].length - 1; k += 1)
			{
				if ((terrTiles[i][k] == 20 //Regular bridge tile.
					|| (terr[i][k] == 3 && terrTiles[i][k] < 16)) //Bridge tile id and terrain tile = fake tile.
					&& terr[i][k + 1] == 2) //Check for water below. 
				{
					if (terrTiles[i][k + 1] != 0)
					{
						terrSpr[i][k + 1] = new Sprite(Spr.terrain);
						terrSpr[i][k + 1].setFrame(15 + 4 + 2);
						terrSpr[i][k + 1].setOffset(Terrain.cellSize / 2, Terrain.cellSize / 2);
					}
					else
					{
						terrTiles[i][k + 1] = 15 + 4 + 2;
					}
				}
			}
		}

		return terrTiles;
	}

	void terrainBridgeAdd(int[][] terr, int sx, int sy, int fx, int fy)
	{
		boolean is_h = Mathe.choose(true, false);

		int dx = (int) Math.signum(fx - sx),
			dy = (int) Math.signum(fy - sy),
			tc = 0; //Turn counter. If reaches 2, cycle ends.

		while (tc < 2)
		{
			if (terr[sx][sy] == 2)
			{
				terr[sx][sy] = 3;
			}
			if (terr[sx][sy] == 1)
			{
				terr[sx][sy] = 0;
			}
			//Moving.
			if (is_h)
			{
				sx += dx;
				if (sx == fx)
				{
					tc += 1;
					is_h = false;
				}
			}
			else
			{
				sy += dy;
				if (sy == fy)
				{
					tc += 1;
					is_h = true;
				}
			}
			//Moving.
		}
	}

	void treesGenerate(int[][] terr)
	{
		int w = terr.length,
			h = terr[0].length;
		int[][] trees = new int[w][h];

		TerrainGenerator treesGen = new TerrainGenerator(w, h, seed);

		int step = 10;

		for (int i = 0; i < w; i += step)
		{
			for (int k = 0; k < h; k += step)
			{
				treesGen.islandAdd(i + Mathe.irandom(-step / 2, step / 2),
					k + Mathe.irandom(-step / 2, step / 2),
					Mathe.irandom(2, 5));
			}
		}

		treesGen.terrainSpineGenerate(trees);
		treesGen.terrainBeefup(trees);

		for (int i = 0; i < w; i += 1)
		{
			for (int k = 0; k < h; k += 1)
			{
				if (terr[i][k] == 0 && ((trees[i][k] != 0 && Mathe.irandom(2) != 0) || Mathe.irandom(32) == 0))
				{
					terr[i][k] = chooseObject();
				}
			}
		}

	}
	
	private void generateForestWall(int[][] terr)
	{
		int middlePointX = (islandCenters.get(1)[0] - islandCenters.get(0)[0]) / 2 + islandCenters.get(0)[0];
		int middlePointY = (islandCenters.get(1)[1] - islandCenters.get(0)[1]) / 2 + islandCenters.get(0)[1];
		int spawnpointsDir = (int)Mathe.pointDirection(
			islandCenters.get(0)[0], 
			islandCenters.get(0)[1], 
			islandCenters.get(1)[0],
			islandCenters.get(1)[1]);
		
		terr[middlePointX][middlePointY] = 4;
		
		int[][] buffer = new int[terr.length][terr[0].length];
		
		terrainIslandSpineGenerate(buffer, spawnpointsDir + 90, 16, middlePointX, middlePointY, 4);
		terrainIslandSpineGenerate(buffer, spawnpointsDir - 90, 16, middlePointX, middlePointY, 4);
		
		terrainBeefup(buffer);
		
		for(int i = 0; i <terr.length; i += 1)
		{
			for(int k = 0; k <terr[0].length; k += 1)
			{
				if (terr[i][k] == 0 && buffer[i][k] != 0 && Mathe.irandom(10) > 0)
				{
					terr[i][k] = chooseObject();
				}
			}
		}
	}
	
	
	/**
	 * Chooses between tree, stone and gunpowder stone.
	 * @return 
	 */
	private int chooseObject()
	{
		if (Mathe.irandom(5) == 0)
		{
			return 4;
		}
		else
		{
			if (Mathe.irandom(30) == 0)
			{
				return 5;
			}
			else
			{
				return 1;
			}
		}
	}

}
