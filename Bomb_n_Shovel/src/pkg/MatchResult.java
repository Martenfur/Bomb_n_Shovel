package pkg;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pkg.engine.*;

/**
 * Shows match results and offers watching replay.
 */
public class MatchResult extends GameObject
{

	double bscrAl, bscrAlTar;

	int mode; //-1 - disconnect, 0..inf - player loss.

	boolean drawResults;

	public MatchResult(int mode_arg)
	{

		super();

		if (Obj.objCount(Obj.oid.match_result) > 0)
		{
			Obj.objDestroy(this);
		}

		objIndex.add(Obj.oid.match_result);

		mode = mode_arg;
		drawResults = true;

	}

	@Override
	public void STEP()
	{
		if (bscrAl != bscrAlTar)
		{
			if (bscrAlTar > bscrAl)
			{
				bscrAl += 0.01;
			}
			if (bscrAlTar < bscrAl)
			{
				bscrAl -= 0.01;
			}
			if (Math.abs(bscrAlTar - bscrAl) < 0.01)
			{
				bscrAl = bscrAlTar;
				if (bscrAlTar == 1)
				{
					bscrAlTar = 0;
					drawResults = false;

					new Lobby();
					for (ObjIter it = new ObjIter(Obj.oid.terrain); it.end(); it.inc())
					{
						Obj.objDestroy(it.get());
					}
				}
				else
				{
					bscrAlTar = 1;
					Obj.objDestroy(this);
				}
			}
		}

		if (Input.mbCheckRelease && bscrAlTar == 0 && bscrAl == 0)
		{
			bscrAlTar = 1;
		}
	}

	@Override
	public void DRAW_GUI()
	{
		Draw.setColor(Color.BLACK);
		Draw.setDepth(-10);

		if (drawResults)
		{
			int frame;
			if (mode == -1)
			{
				frame = 2;
			}
			else
			{
				frame = mode;
			}

			Draw.drawSprite(new Sprite(Spr.match_results), frame, Camera.scr_w / 2, Camera.scr_h / 2);
		}
		Draw.setAlpha(bscrAl);
		Draw.drawRectangle(new Rectangle(), 0, 0, Camera.scr_w, Camera.scr_h, false);
		Draw.setAlpha(1);
	}
}
