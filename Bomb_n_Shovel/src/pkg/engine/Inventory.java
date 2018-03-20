package pkg.engine;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    public enum Item {
        WOOD("wood"), ROCK("rock"), BLOOD("blood"),GUNPOWDER("gunpowder_stone");

        Item(String name) {
        }
    }


    public List<Item> inv;

    public Inventory() {
        inv = new ArrayList<>();
    }

    public void addItem(Item item) {
        if (inv.size() < 4)
            inv.add(item);
    }

    public void delItem(Item item) {
        inv.remove(item);
    }

    public void removeItem(int index) {
        inv.remove(index);
    }

    public int getInvPlaceNumber(Item item) {
        int result = 0;
        switch (inv.size()) {
            case 1: {
                result = 1;
                break;
            }
            case 2: {
                result = 2;
                break;
            }
            case 3: {
                result = 3;
                break;
            }
            case 4: {
                result = 4;
                break;
            }
        }
        return result;
    }

    public String showInv() {
        String temp = ("");
        for (int i = 0; i < inv.size(); i++)
            temp = temp + inv.get(i);
        return temp;
    }
}
