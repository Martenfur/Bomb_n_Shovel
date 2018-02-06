/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.turns;

import pkg.engine.*;
import java.util.ArrayList;
import pkg.GameObject;
import pkg.Peasant;
import pkg.net.Client;

/**
 * Controls 4 peasants. Can be controlled by local player, computer (TODO) or
 * remote player (TODO).
 */
public class Player extends GameObject
{

	Client client;

	public TurnManager turnManager;
	public int tid = 0;            //Team id.
	ArrayList<Peasant> peasants; //List of peasants.
	int peasantCur = 0;            //Current peasant.

	public boolean initiative = false;

	boolean firstTurn = true;

	public Player()
	{
		super(0, 0);
		peasants = new ArrayList<>();
	}

	@Override
	public void STEP()
	{
		if (initiative && !peasants.isEmpty())
		{
			initiativeGive();
		}

		if (peasantCur >= peasants.size())
		{
			peasantCur = peasants.size() - 1;
		}
	}

	@Override
	public void DESTROY()
	{
		while (!peasants.isEmpty())
		{
			Obj.objDestroy(peasants.get(0));
		}

		if (client != null)
		{
			client.disconnect();
		}
	}

	/**
	 * Gives new peasant under player's control.
	 *
	 * @param peasant
	 */
	public void peasantAdd(Peasant peasant, Logger logger)
	{
		peasants.add(peasant);
		peasant.myPlayer = this;
		peasant.tid = tid;
		peasant.logger = logger;
	}

	/**
	 * Removes peasant from player's control.
	 *
	 * @param peasant
	 */
	public void peasantRemove(Peasant peasant)
	{
		peasants.remove(peasant);
	}

	/**
	 * Gives initiative to current player.
	 */
	void initiativeGive()
	{
		initiative = false;
		if (peasants.size() > peasantCur)
		{
			peasants.get(peasantCur).initiative = true;
			peasants.get(peasantCur).staminaRefill();
			if (!peasants.get(peasantCur).pathCheck())
			{
				peasants.get(peasantCur).pathList = null;
			}

			if (tid == 0 && firstTurn)
			{
				Camera.setPosition(peasants.get(peasantCur).x - Camera.view_w / 2, peasants.get(peasantCur).y - Camera.view_h / 2);
				firstTurn = false;
			}
			Camera.viewer = peasants.get(peasantCur);
		}
	}

	/**
	 * Ends this player's turn.
	 */
	public void endTurn()
	{
		if (peasants.size() > peasantCur)
		{
			peasants.get(peasantCur).initiative = false;
		}

		peasantCur += 1;
		if (peasantCur >= peasants.size())
		{
			peasantCur = 0;
		}

		turnManager.initiativeTake();
	}

	/**
	 * Checks if it is current player's turn right now.
	 *
	 * @return
	 */
	boolean isMyTurn()
	{
		if (turnManager != null)
		{
			try
			{
				return turnManager.players.get(turnManager.playerCur) == this;
			}
			catch (Exception ex)
			{
				return false;
			}
		}
		return false;
	}

	/**
	 * Retrieves current peasant.
	 *
	 * @return peasant
	 */
	public Peasant getCurrentPeasant()
	{
		try
		{
			return peasants.get(peasantCur);
		}
		catch (Exception e)
		{
			System.out.println("NO PEASANTS! size:" + peasants.size() + "current: " + peasantCur);

			return null;
		}
	}

}
