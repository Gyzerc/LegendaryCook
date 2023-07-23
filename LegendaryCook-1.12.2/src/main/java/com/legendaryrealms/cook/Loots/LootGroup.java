package com.legendaryrealms.cook.Loots;

import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Utils.StringUtils;
import eu.decentsoftware.holograms.api.utils.scheduler.S;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LootGroup {

    private String id;
    private int weight;
    private int level=0;
    private double recipes_experience_min=0;
    private double recipes_experience_max=-1;
    private double chance=1.0;
    private String item="";
    private boolean papiOnlyOne=false;
    private HashMap<String,String> papi_compare=new HashMap<>();
    private HashMap<String,String> papi_value=new HashMap<>();
    private HashMap<String,String> papi_name=new HashMap<>();

    public void setPapiOnlyOne(boolean papiOnlyOne) {
        this.papiOnlyOne = papiOnlyOne;
    }

    public void addPapi(String key,String compare,String name,String value)
    {
        papi_compare.put(key,compare);
        papi_name.put(key,name);
        papi_value.put(key,value);
    }

    public List<String> getPapis()
    {
        List<String> list=new ArrayList<>();
        for (String key:papi_name.keySet())
        {
            list.add(key);
        }
        return list;
    }
    public boolean compare(Player p,String key)
    {
        if (LegendaryCook.getInstance().getHooks().getPlaceholderAPIHook()==null)
        {
            return false;
        }
        if (papi_value.containsKey(key))
        {
            String value=papi_value.get(key);
            String comapreTag=papi_compare.get(key);
            String name=papi_name.get(key);
            switch (comapreTag)
            {
                case ">":
                    if (!StringUtils.isNum(value) || !StringUtils.isNum(value))
                    {
                        return false;
                    }
                    double a=Double.parseDouble(value);
                    if (a > Double.parseDouble(LegendaryCook.getInstance().getHooks().getPlaceholderAPIHook().getPlaceholder(p,name)))
                    {
                        return true;
                    }
                    break;
                case ">=":
                    if (!StringUtils.isNum(value) || !StringUtils.isNum(value))
                    {
                        return false;
                    }
                    a = Double.parseDouble(value);
                    if (a >= Double.parseDouble(LegendaryCook.getInstance().getHooks().getPlaceholderAPIHook().getPlaceholder(p,name)))
                    {
                        return true;
                    }
                    break;
                case "<":
                    if (!StringUtils.isNum(value) || !StringUtils.isNum(value))
                    {
                        return false;
                    }
                    a = Double.parseDouble(value);
                    if (a < Double.parseDouble(LegendaryCook.getInstance().getHooks().getPlaceholderAPIHook().getPlaceholder(p,name)))
                    {
                        return true;
                    }
                    break;
                case "<=":
                    if (!StringUtils.isNum(value) || !StringUtils.isNum(value))
                    {
                        return false;
                    }
                    a = Double.parseDouble(value);
                    if (a <= Double.parseDouble(LegendaryCook.getInstance().getHooks().getPlaceholderAPIHook().getPlaceholder(p,name)))
                    {
                        return true;
                    }
                    break;
                case "=":
                    if (!StringUtils.isNum(value) || !StringUtils.isNum(value))
                    {
                        return false;
                    }
                    if (value.equals(LegendaryCook.getInstance().getHooks().getPlaceholderAPIHook().getPlaceholder(p,name)))
                    {
                        return true;
                    }
                    break;
            }
        }
        return true;
    }



    public boolean isPapiOnlyOne() {
        return papiOnlyOne;
    }

    private List<String> run=new ArrayList<>();
    public LootGroup(String id,int weight)
    {
        this.id=id;
        this.weight=weight;
    }

    public int getLevel()
    {
        return level;
    }
    public int getWeight()
    {
        return weight;
    }
    public String getId()
    {
        return id;
    }
    public String getItem()
    {
        return item;
    }
    public List<String> getRun()
    {
        return run;
    }
    public double getChance()
    {
        return chance;
    }
    public double getRecipes_experience_min()
    {
        return recipes_experience_min;
    }
    public double getRecipes_experience_max()
    {
        return recipes_experience_max;
    }
    public void setLevel(int level)
    {
        this.level=level;
    }
    public void setItem(String item)
    {
        this.item=item;
    }
    public void setChance(double chance)
    {
        this.chance=chance;
    }
    public void setWeight(int weight)
    {
        this.weight=weight;
    }
    public void setRecipes_experience_min(double min)
    {
        this.recipes_experience_min=min;
    }
    public void setRecipes_experience_max(double max)
    {
        this.recipes_experience_max=max;
    }
    public void setRun(List<String> run)
    {
        if (run == null)
        {
            return;
        }
        this.run=run;
    }




}
