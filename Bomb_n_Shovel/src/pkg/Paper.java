package pkg;

import pkg.engine.*;

/**
 *
 * @author gn.fur
 */
public class Paper extends GameObject
{

	Sprite spr;
	public boolean disappear = false;
	int disappearSpd = 5;
	int depth;

	public Paper(double x_arg, double y_arg)
	{
		super(x_arg, y_arg);
		objIndex.add(Obj.oid.paper);

		spr = new Sprite(Spr.paper);
		spr.img.setRotate(Mathe.irandom(359));

		depth = Mathe.irandom(1, 8);
	}

	@Override
	public void STEP()
	{
		if (disappear)
		{
			double dir = Mathe.pointDirection(Game.scr_w / 2, Game.scr_h / 2, x, y);
			double spd = Math.max(1, Mathe.pointDistance(Game.scr_w / 2, Game.scr_h / 2, x, y) / 8.0);
			x += Mathe.lcos(spd, dir);
			y += Mathe.lsin(spd, dir);

			if (Mathe.pointDistance(Game.scr_w / 2, Game.scr_h / 2, x, y) > Game.scr_w * 2 + 256)
			{
				Obj.objDestroy(this);
			}
		}
	}

	@Override
	public void DRAW_GUI()
	{
		Draw.setDepth(depth);
		Draw.drawSprite(spr, x, y);
	}

}
