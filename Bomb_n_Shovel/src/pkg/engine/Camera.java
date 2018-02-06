package pkg.engine;

import pkg.GameObject;

/**
 * Main camera.
 */
public class Camera
{

	public static GameObject viewer;
	public static double view_w = 0,
		view_h = 0,
		scr_w = Game.scr_w,
		scr_h = Game.scr_h;

	static double xscale_tar = 1,
		yscale_tar = 1;

	/**
	 * Updates smooth camera.
	 */
	public static void UPDATE()
	{
		view_w = Game.scr_w / getScale_x();
		view_h = Game.scr_h / getScale_y();

		if (viewer != null)
		{
			//Moving camera.
			double cx = get_x() + view_w / 2,
				cy = get_y() + view_h / 2,
				vx = viewer.x,
				vy = viewer.y;

			double l = Mathe.pointDistance(cx, cy, vx, vy),
				d = Mathe.pointDirection(cx, cy, vx, vy);

			cx += Mathe.lcos(l / 4.0, d);
			cy += Mathe.lsin(l / 4.0, d);

			setPosition(cx - view_w / 2, cy - view_h / 2);
			//Moving camera.
		}

		//Scaling.
		double dscale_x = getScale_x() - xscale_tar,
			dscale_y = getScale_y() - yscale_tar;

		if (dscale_x != 0)
		{
			if (Math.abs(dscale_x) < 0.01)
			{
				setScale_x(xscale_tar);
			}
			else
			{
				setScale_x(getScale_x() + (xscale_tar - getScale_x()) / 2.0);
			}
		}

		if (dscale_y != 0)
		{
			if (Math.abs(dscale_y) < 0.01)
			{
				setScale_y(yscale_tar);
			}
			else
			{
				setScale_y(getScale_y() + (yscale_tar - getScale_y()) / 2.0);
			}
		}
		//Scaling.

	}

	/**
	 * Sets x and y of the camera.
	 *
	 * @param x
	 * @param y
	 */
	public static void setPosition(double x, double y)
	{
		Game.appsurf.setTranslateX(Math.round(-x * Game.appsurf.getScaleX() + (Game.root.getWidth() * Game.appsurf.getScaleX() - Game.appsurf.getWidth()) / 2));
		Game.appsurf.setTranslateY(Math.round(-y * Game.appsurf.getScaleY() + (Game.root.getHeight() * Game.appsurf.getScaleY() - Game.appsurf.getHeight()) / 2));
	}

	/**
	 * @return x of upper left camera's corner.
	 */
	public static double get_x()
	{
		return -Game.appsurf.getTranslateX() / Game.appsurf.getScaleX() - (Game.root.getWidth() / Game.appsurf.getScaleX() - Game.appsurf.getWidth()) / 2;
	}

	/**
	 * @return y of upper left camera's corner.
	 */
	public static double get_y()
	{
		return -Game.appsurf.getTranslateY() / Game.appsurf.getScaleY() - (Game.root.getHeight() / Game.appsurf.getScaleY() - Game.appsurf.getHeight()) / 2;
	}

	/**
	 * Sets scale.
	 *
	 * @param xscale
	 * @param yscale
	 */
	public static void setScale(double xscale, double yscale)
	{

		Game.appsurf.setTranslateX(Game.appsurf.getTranslateX() / Game.appsurf.getScaleX());
		Game.appsurf.setTranslateY(Game.appsurf.getTranslateY() / Game.appsurf.getScaleY());

		Game.appsurf.setScaleX(xscale);
		Game.appsurf.setScaleY(yscale);

		Game.appsurf.setTranslateX(Game.appsurf.getTranslateX() * xscale);
		Game.appsurf.setTranslateY(Game.appsurf.getTranslateY() * yscale);
	}

	/**
	 * Sets scale.
	 *
	 * @param xscale
	 */
	public static void setScale_x(double xscale)
	{
		Game.appsurf.setTranslateX(Game.appsurf.getTranslateX() / Game.appsurf.getScaleX());
		Game.appsurf.setScaleX(xscale);
		Game.appsurf.setTranslateX(Game.appsurf.getTranslateX() * xscale);
	}

	/**
	 * Sets scale.
	 *
	 * @param yscale
	 */
	public static void setScale_y(double yscale)
	{
		Game.appsurf.setTranslateY(Game.appsurf.getTranslateY() / Game.appsurf.getScaleY());
		Game.appsurf.setScaleY(yscale);
		Game.appsurf.setTranslateY(Game.appsurf.getTranslateY() * yscale);
	}

	/**
	 * @return xscale
	 */
	public static double getScale_x()
	{
		return Game.appsurf.getScaleX();
	}

	/**
	 * @return yscale
	 */
	public static double getScale_y()
	{
		return Game.appsurf.getScaleY();
	}

	/**
	 * Sets target scale for smooth WHOOSHes.
	 *
	 * @param xscale
	 * @param yscale
	 */
	public static void setScaleTar(double xscale, double yscale)
	{
		xscale_tar = xscale;
		yscale_tar = yscale;
	}

	/**
	 * Sets rotation in degrees. !!!THIS WILL MESS UP MOUSE COORDINATES, SO BE
	 * CAREFUL!!!
	 *
	 * @param rot
	 */
	public static void setRotate(double rot)
	{
		Game.rotatesurf.setRotate(rot);
	}

	/**
	 * @return Camera angle in degrees.
	 */
	public static double getRotate()
	{
		return Game.rotatesurf.getRotate();
	}
}
