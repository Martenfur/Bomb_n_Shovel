package pkg.engine;

import java.util.ArrayList;
import javafx.scene.paint.Color;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.geometry.Rectangle2D;

/**
 * Draws stuff on screen.
 */
public class Draw
{

	public static ArrayList<Node>[] drawlist;
	public static ArrayList<Integer>[] depthlist;

	static Color currentColor = new Color(0, 0, 0, 1);
	static int currentDepth = 0;
	static Polyline currentPoly = null;

	/**
	 * Draw flag. Determines layer of draw event. So, node drawn in DRAW_END with
	 * depth 0 will still be drawn higher than node drawn in DRAW_BEGIN with depth
	 * -10000.
	 */
	public static enum df
	{
		BEGIN(0),
		DEFAULT(1),
		END(2),
		GUI(3);

		int val;

		df(int val_arg)
		{
			val = val_arg;
		}

		public int get()
		{
			return val;
		}
	}

	public static df drawFlag = df.DEFAULT;

	/**
	 * Init function.
	 */
	public static void CREATE()
	{
		drawlist = new ArrayList[4];
		for (int i = 0; i < 4; i += 1)
		{
			drawlist[i] = new ArrayList<>();
		}

		depthlist = new ArrayList[4];
		for (int i = 0; i < 4; i += 1)
		{
			depthlist[i] = new ArrayList<>();
		}
	}

	/**
	 * Drawing stuff.
	 */
	public static void UPDATE()
	{
		//Clearing panes.
		Game.appsurf.getChildren().clear();
		Game.gui.getChildren().clear();
		//Clearing panes.

		//Regular draw lists.
		int size;
		for (int k = 0; k < 3; k += 1)
		{
			size = drawlist[k].size();
			for (int i = 0; i < size; i += 1)
			{
				Game.appsurf.getChildren().addAll(drawlist[k].get(i));
			}
			drawlist[k].clear();
			depthlist[k].clear();
		}
		//Reguler draw lists.

		//GUI layer.
		size = drawlist[3].size();
		for (int i = 0; i < size; i += 1)
		{
			Game.gui.getChildren().addAll(drawlist[3].get(i));
		}
		drawlist[3].clear();
		depthlist[3].clear();
		//GUI layer.
	}

	//////////////////////////////////////////////////////////////////////
	/**
	 * Universal draw function. Any node can use it.
	 *
	 * @param dr
	 */
	public static void draw(Node dr)
	{
		ArrayList<Node> listCur = drawlist[drawFlag.get()];
		ArrayList<Integer> depthCur = depthlist[drawFlag.get()];
		listCur.add(dr);
		depthCur.add(currentDepth);

		Node nodeBuf;
		int depthBuf;
		for (int i = listCur.size() - 1; i > 0; i -= 1)
		{
			if (depthCur.get(i) > depthCur.get(i - 1))
			{
				depthBuf = depthCur.get(i);
				nodeBuf = listCur.get(i);
				depthCur.set(i, depthCur.get(i - 1));
				listCur.set(i, listCur.get(i - 1));
				depthCur.set(i - 1, depthBuf);
				listCur.set(i - 1, nodeBuf);
			}
			else
			{
				break;
			}
		}
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Sets depth.
	 *
	 * @param depth
	 */
	public static void setDepth(int depth)
	{
		currentDepth = depth;
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Sets color.
	 *
	 * @param c
	 */
	public static void setColor(Color c)
	{
		currentColor = c;
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Sets alpha.
	 *
	 * @param a
	 */
	public static void setAlpha(double a)
	{
		if (a < 0)
		{
			a = 0;
		}
		else
		{
			if (a > 1)
			{
				a = 1;
			}
		}

		currentColor = new Color(currentColor.getRed(),
			currentColor.getGreen(),
			currentColor.getBlue(),
			a);
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * The most basic sprite function. Just draws image as is. No rotation, no
	 * frames, no scale.
	 *
	 * @param spr
	 * @param x
	 * @param y
	 */
	public static void drawSprite(Sprite spr, double x, double y)
	{
		spr.img.setX(x - spr.offset_x);
		spr.img.setY(y - spr.offset_y);

		draw(spr.img);
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Draws sprite with given frame.
	 *
	 * @param spr
	 * @param frame
	 * @param x
	 * @param y
	 */
	public static void drawSprite(Sprite spr, double frame, double x, double y)
	{
		frame = Math.floor(frame);
		spr.img.setX(x - spr.offset_x);
		spr.img.setY(y - spr.offset_y);

		double vp_w = spr.getWidth();
		double vp_h = spr.getHeight();

		double vp_x = (frame - Math.floor(frame / spr.frames_h) * spr.frames_h) * vp_w;
		double vp_y = Math.floor(frame / spr.frames_h) * vp_h;

		spr.img.setViewport(new Rectangle2D(vp_x, vp_y, vp_w, vp_h));

		draw(spr.img);
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Draws sprite with given frame and alpha.
	 *
	 * @param spr
	 * @param frame
	 * @param x
	 * @param y
	 * @param alpha
	 */
	public static void drawSprite(Sprite spr, double frame, double x, double y, double alpha)
	{
		spr.img.setOpacity(alpha);
		drawSprite(spr, frame, x, y);
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Draws sprite with given frame, scale, rotation and alpha.
	 *
	 * @param spr
	 * @param frame
	 * @param x
	 * @param y
	 * @param xscale
	 * @param yscale
	 * @param ang
	 * @param alpha
	 */
	public static void drawSprite(Sprite spr, double frame, double x, double y,
		double xscale, double yscale,
		double ang, double alpha)
	{
		spr.img.setOpacity(alpha);
		drawSprite(spr, frame, x, y, xscale, yscale, ang);
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Draws sprite with given frame, scale and rotation.
	 *
	 * @param spr
	 * @param frame
	 * @param x
	 * @param y
	 * @param xscale
	 * @param yscale
	 * @param ang
	 */
	public static void drawSprite(Sprite spr, double frame, double x, double y,
		double xscale, double yscale,
		double ang)
	{
		ang -= Math.floor(ang / 360) * 360;

		frame = Math.floor(frame);

		spr.setOffset(spr.offset_x, spr.offset_y);
		double centerDir = Mathe.pointDirection(spr.offset_x * xscale, spr.offset_y * yscale, spr.getWidth() / 2 * xscale, spr.getHeight() / 2 * yscale);
		double centerDist = Mathe.pointDistance(spr.offset_x * xscale, spr.offset_y * yscale, spr.getWidth() / 2 * xscale, spr.getHeight() / 2 * yscale);

		spr.img.setX(x - spr.getWidth() / 2 + Mathe.lcos(centerDist, centerDir - ang));
		spr.img.setY(y - spr.getHeight() / 2 + Mathe.lsin(centerDist, centerDir - ang));
		spr.img.setRotate(ang);
		spr.img.setScaleX(xscale);
		spr.img.setScaleY(yscale);
		double vp_w = spr.getWidth();
		double vp_h = spr.getHeight();

		double vp_x = (frame - Math.floor(frame / spr.frames_h) * spr.frames_h) * vp_w;
		double vp_y = Math.floor(frame / spr.frames_h) * vp_h;

		spr.img.setViewport(new Rectangle2D(vp_x, vp_y, vp_w, vp_h));

		draw(spr.img);
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Draws line between two points.
	 *
	 * @param line
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public static void drawLine(Line line, double x1, double y1,
		double x2, double y2)
	{
		line.setStartX(x1);
		line.setStartY(y1);
		line.setEndX(x2);
		line.setEndY(y2);

		line.setStroke(currentColor);

		draw(line);
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Draws wide line between two points.
	 *
	 * @param line
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param w
	 */
	public static void drawLine(Line line, double x1, double y1,
		double x2, double y2,
		int w)
	{
		line.setStartX(x1);
		line.setStartY(y1);
		line.setEndX(x2);
		line.setEndY(y2);

		line.setStroke(currentColor);
		line.setStrokeWidth(w);

		draw(line);
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Draws rectangle.
	 *
	 * @param rect
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param outline
	 */
	public static void drawRectangle(Rectangle rect, double x1, double y1,
		double x2, double y2,
		boolean outline)
	{
		rect.setX(x1);
		rect.setY(y1);
		rect.setWidth(x2 - x1);
		rect.setHeight(y2 - y1);
		if (outline)
		{
			rect.setStroke(currentColor);
			rect.setFill(Color.rgb(0, 0, 0, 0));
		}
		else
		{
			rect.setFill(currentColor);
		}
		draw(rect);
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Draws circle.
	 *
	 * @param circ
	 * @param x
	 * @param y
	 * @param r
	 * @param outline
	 */
	public static void drawCircle(Circle circ, double x, double y, double r, boolean outline)
	{
		circ.setCenterX(x);
		circ.setCenterY(y);
		circ.setRadius(r);
		if (outline)
		{
			circ.setStroke(currentColor);
			circ.setFill(Color.rgb(0, 0, 0, 0));
		}
		else
		{
			circ.setFill(currentColor);
		}
		draw(circ);
	}
	//////////////////////////////////////////////////////////////////////  

	//////////////////////////////////////////////////////////////////////
	/**
	 * Starts drawing polygon.
	 *
	 * @param poly
	 * @param outline
	 */
	public static void drawPolyBegin(Polyline poly, boolean outline)
	{
		currentPoly = poly;

		if (outline)
		{
			poly.setStroke(currentColor);
		}
		else
		{
			poly.setFill(currentColor);
			poly.setStroke(Color.rgb(0, 0, 0, 0));
		}
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Ends drawing polygon.
	 */
	public static void drawPolyEnd()
	{
		if (currentPoly != null)
		{
			draw(currentPoly);
			currentPoly = null;
		}
	}
	//////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////
	/**
	 * Adds vertex to current polygon.
	 *
	 * @param x
	 * @param y
	 */
	public static void polyAddPoint(double x, double y)
	{
		if (currentPoly != null)
		{
			currentPoly.getPoints().addAll(new Double[]
			{
				x, y
			});
		}
	}
	//////////////////////////////////////////////////////////////////////  

}
