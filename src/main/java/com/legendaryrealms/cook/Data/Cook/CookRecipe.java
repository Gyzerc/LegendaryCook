package com.legendaryrealms.cook.Data.Cook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CookRecipe {

    private static HashMap<String,CookRecipe> cache=new HashMap<>();
    private String id;
    public CookRecipe(String id)
    {
        this.id=id;
        cache.put(id,this);
    }
    public static CookRecipe getCookRecipe(String id)
    {
        return cache.get(id);
    }
    private int amount=0;
    private HashMap<Integer,Integer> Hot=new HashMap<>();
    private HashMap<Integer,Integer> Need=new HashMap<>();
    private HashMap<Integer, ItemStack> Item=new HashMap<>();

    public void add(int hot,ItemStack i,int need)
    {
        Hot.put(amount,hot);
        Item.put(amount,i);
        Need.put(amount,need);
        amount++;
    }

    public int getNeed(int in)
    {
        return Need.get(in) != null ? Need.get(in) : 1;
    }
    public int getHot(int in)
    {
        return Hot.get(in);
    }
    public ItemStack getItem(int in)
    {
        return Item.get(in);
    }
    public int getAmounts()
    {
        return amount;
    }

    public void addNeed(int in,int add)
    {
        int old=getNeed(in);
        Need.put(in,(old+add));
    }

    public void setHot(int in,int hot)
    {
        Hot.put(in,hot);
    }
    public void takeNeed(int in,int take)
    {
        int old=getNeed(in);
        int fina=old-take >= 0? old-take:0;
        Need.put(in,fina);
    }

    public void remove(int in)
    {
        Hot.remove(in);
        Item.remove(in);
        Need.remove(in);
        amount=amount-1;
    }
    public void setNeed(int in,int amount)
    {
        Need.put(in,amount);
    }

    public void setItem(int in,ItemStack i)
    {
        Item.put(in,i);
    }

    public static void clear()
    {
        cache.clear();
    }
}
