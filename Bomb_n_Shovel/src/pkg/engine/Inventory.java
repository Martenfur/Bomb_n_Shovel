package pkg.engine;

import java.util.ArrayList;
import java.util.List;

import pkg.Entity;
public class Inventory {

    public enum Item {
        WOOD("Wood"),
        ROCK("Rock"),
        BLOOD("Blood");

        Item(String name) {
        }
    }


    public List<Item> inv;
    public Inventory() {
        inv = new ArrayList<>();
    }
    public void addInv(Item item){
        inv.add(item);
    }
    public void delInv(Item item){
        inv.remove(item);
    }
    public String showInv(){
        String temp=("");
        for(int i=0;i<inv.size();i++)
        temp=temp+inv.get(i);
        return temp;
    }
}
