package pkg.pathfinder;

/**
 * Linked grid. Has 4 pointers for each side. Used only for pathfinding.
 */
public class PathCell
{

	public int x, y, //Cell coordinates.
		dist_f, //Distance to finish point.
		dist_s; //Distance to start point. NOTE: this is not a closest distance. 

	PathCell[] surrounders;

	/**
	 * Main constructor.
	 *
	 * @param x_arg Cell x.
	 * @param y_arg Cell y.
	 * @param ds_arg Distance to starting point.
	 * @param df_arg Distance to finishing point.
	 */
	PathCell(int x_arg, int y_arg, int ds_arg, int df_arg)
	{
		x = x_arg;
		y = y_arg;
		dist_s = ds_arg;
		dist_f = df_arg;

		surrounders = new PathCell[4];
	}

	/**
	 * @return Cell value. Calculated by adding ds and df.
	 */
	int getValue()
	{
		return dist_s + dist_f;
	}

}
