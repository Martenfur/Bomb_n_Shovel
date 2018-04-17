package pkg.engine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static pkg.engine.Inventory.Item.ROCK;
import static pkg.engine.Inventory.Item.WOOD;

public class Inventory
{

	public enum Item
	{
		WOOD("wood", 0), 
		ROCK("rock", 1), 
		GUNPOWDER("gunpowder_stone", 2);

		public String name;
		public int imgId;
		
		Item(String name_arg, int imgId_arg)
		{
			name = name_arg;
			imgId = imgId_arg;
		}
	}

	public List<Item> WOOD_AXE = new ArrayList<Item>();//WOOD, WOOD,ROCK;
	public List<Item> STONE_AXE = new ArrayList<Item>();//WOOD, WOOD, ROCK, ROCK;
	public List<Item> WOOD_SWORD = new ArrayList<Item>();//WOOD, WOOD, WOOD);
	public List<Item> STONE_SWORD = new ArrayList<Item>();//(WOOD,ROCK,ROCK);
	public List<Item> WOOD_PICKAXE = new ArrayList<Item>();//(WOOD,ROCK,ROCK);
	public List<Item> STONE_PICKAXE = new ArrayList<Item>();//(WOOD,ROCK,ROCK);

	public List<Item> inv;

	public Inventory()
	{
		inv = new ArrayList<>();
		Collections.addAll(WOOD_AXE, WOOD, WOOD, ROCK);
		Collections.addAll(STONE_AXE, WOOD, WOOD, ROCK, ROCK);
		Collections.addAll(WOOD_SWORD, WOOD, WOOD, WOOD);
		Collections.addAll(STONE_SWORD, WOOD, ROCK, ROCK);
		Collections.addAll(WOOD_PICKAXE, WOOD, ROCK, ROCK);
		Collections.addAll(STONE_PICKAXE, WOOD, ROCK, ROCK, ROCK);

	}

	public void addItem(Item item)
	{
		if (inv.size() < 4)
		{
			inv.add(item);
		}
	}

	public void delItem(Item item)
	{
		inv.remove(item);
	}

	public void removeItem(int index)
	{
		inv.remove(index);
	}

	public boolean isContain(List<Item> m1, List<Item> m2)
	{
		List<Item> temp = new ArrayList<Item>(m2);
		int count = 0;
		for (int i = 0; i < m1.size(); i++)
		{
			for (int j = 0; j < temp.size(); j++)
			{
				if (m1.get(i) == temp.get(j))
				{
					count++;
					temp.remove(j);
					break;
				}
			}
		}
	
		return count == m1.size();
	}

	public boolean RemoveisContain(List<Item> m1, List<Item> m2)
	{
		List<Item> temp = new ArrayList<Item>(m2);
		int count = 0;
		for (int i = 0; i < m1.size(); i++)
		{
			for (int j = 0; j < temp.size();)
			{
				if (m1.get(i) == m2.get(j))
				{
					m2.remove(j);
					count++;
					break;
				}
				else
				{
					j++;
				}
			}
		}
		return count == m1.size();
	}

	public int getInvPlaceNumber(Item item)
	{
		int result = 0;
		switch (inv.size())
		{
			case 1:
			{
				result = 1;
				break;
			}
			case 2:
			{
				result = 2;
				break;
			}
			case 3:
			{
				result = 3;
				break;
			}
			case 4:
			{
				result = 4;
				break;
			}
		}
		return result;
	}

	public String showInv()
	{
		String temp = ("");
		for (int i = 0; i < inv.size(); i++)
			temp = temp + inv.get(i);
		return temp;
	}
}
