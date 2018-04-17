package pkg.engine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static pkg.engine.Inventory.Item.wood;
import static pkg.engine.Inventory.Item.stone;

public class Inventory
{

	public enum Item
	{
		wood(0),
		stone(1),
		gunpowder(2),
		bomb(3),
		woodenAxe(4),
		stoneAxe(5),
		woodenSword(6),
		stoneSword(7),
		woodenPickaxe(8),
		stonePickaxe(9);
		
		public int imgId;
		
		Item(int imgId_arg)
		{
			imgId = imgId_arg;
		}
	}

	public static Item[][] recipes = new Item[][]
	{
		new Item[]{Item.woodenAxe, Item.wood, Item.wood, Item.wood},
		new Item[]{Item.stoneAxe, Item.woodenAxe, Item.stone, Item.wood, Item.wood},
		new Item[]{Item.woodenSword, Item.wood, Item.wood, Item.wood},
		new Item[]{Item.stoneSword, Item.woodenSword, Item.stone, Item.wood, Item.wood},
		new Item[]{Item.woodenPickaxe, Item.wood, Item.wood, Item.wood},
		new Item[]{Item.stonePickaxe, Item.woodenPickaxe, Item.stone, Item.wood, Item.wood},
	};
	
	public List<Item> inv;

	public Inventory()
	{
		inv = new ArrayList<>();
	}

	public void addItem(Item item)
	{
		if (inv.size() < 4)
		{
			inv.add(item);
		}
	}

	public void removeItem(Item item)
	{
		inv.remove(item);
	}

	public void removeItem(int index)
	{
		inv.remove(index);
	}

	public boolean contains(Item item)
	{
		return inv.contains(item);
	}

	public boolean checkRecipe(Item[] recipe)
	{
		List<Item> temp = new ArrayList<Item>(inv);
		
		for (int i = 1; i < recipe.length; i += 1)
		{
			boolean match = false;
			for (int j = 0; j < temp.size(); j += 1)
			{
				if (recipe[i] == temp.get(j))
				{
					temp.remove(j);
					match = true;
					break;
				}
			}
			if (!match)
			{
				return false;
			}
		}
		return true;
	}
	
	
	public void craft(Item[] recipe)
	{
		for (int i = 1; i < recipe.length; i += 1)
		{
			for (int j = 0; j < inv.size(); j += 1)
			{
				if (recipe[i] == inv.get(j))
				{
					inv.remove(j);
					break;
				}
			}	
		}
		addItem(recipe[0]);
	}

	public int getInvPlaceNumber(Item item)
	{
		return inv.size();
	}

	public String showInv()
	{
		String temp = ("");
		for (int i = 0; i < inv.size(); i++)
			temp += inv.get(i) + " ";
		return temp;
	}
}
