package pkg;

import pkg.terrain.Terrain;
import pkg.engine.*;

public class TreeFallingEffect extends Entity
{
	Sprite spr;
	double alpha = 1;
	double ang = 0;
	double angAdd = 0;
	int side;
	public TreeFallingEffect(double x_arg, double y_arg)
	{
		super(x_arg, y_arg, false);
		
		spr = new Sprite(Spr.tree);
		side = Mathe.choose(-1, 1);
	}

	@Override
	public void DRAW()
	{
		ang += angAdd;
		angAdd += 4;
		if (ang > 90)
		{
			ang = 90;
			alpha -= 0.05;
			if (alpha <= 0)
			{
				Obj.objDestroy(this);
			}
		}
		Draw.setDepth((int)(-y));
		
		Draw.drawSprite(new Sprite(Spr.big_shadow), 0, x + Terrain.cellSize / 2, y + Terrain.cellSize / 2, alpha);
		Draw.drawSprite(spr, 0, x + Terrain.cellSize / 2, y + Terrain.cellSize / 2,  1, 1, ang * side, alpha);
	}
}
