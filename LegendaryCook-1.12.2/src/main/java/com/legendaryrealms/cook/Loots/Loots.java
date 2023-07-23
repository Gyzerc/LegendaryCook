package com.legendaryrealms.cook.Loots;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Loots {

    private static HashMap<String,Loots> cache=new HashMap<>();
    private List<Integer> weights=new ArrayList<>();
    private String id;
    private HashMap<Integer, List<LootGroup>> loots=new HashMap<>();
    public Loots(String id, HashMap<Integer, List<LootGroup>> map)
    {
        this.id=id;
        this.loots=map;
        sort(map);
        cache.put(id,this);
    }
    public void sort(HashMap<Integer, List<LootGroup>> map)
    {
        List<Integer> list=new ArrayList<>();
        for (int weight:map.keySet())
        {
            list.add(weight);
        }
        Collections.sort(list);
        weights=list;
    }

    public List<Integer> getWeights()
    {
        return weights;
    }
    public static Loots getLoots(String id)
    {
        return cache.get(id);
    }
    public List<LootGroup> getLoots(int weight)
    {
        return loots.get(weight) != null ? loots.get(weight): new ArrayList<>();
    }
    public String getId()
    {
        return id;
    }

    public static void clear()
    {
        cache.clear();
    }


}
