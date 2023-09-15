package com.legendaryrealms.cook.Data.Cook;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cook {

    private static Map<String,Cook> cache=new HashMap<>();
    private String id;
    public Cook(String id)
    {
        this.id=id;
        cache.put(id,this);

    }

    public static Cook getCook(String id)
    {
        return cache.get(id);
    }

    private Material material=Material.APPLE;
    private int data=0;
    private int model=0;
    private List<String> lore=new ArrayList<>();
    private String name="无";
    private String mode="mode1";
    private int level=0;
    private int hot=10;
    private int time=60;
    private double chance=1.0;
    private double max_recipes_experience=50.0;
    private String result_item="";
    private double result_add_cook_experience=5.0;
    private double result_add_reicpes_experience=1.0;
    private List<String> result_run=new ArrayList<>();
    private String fail_item="";
    private double fail_add_cook_experience=5.0;
    private double fail_add_reicpes_experience=1.0;
    private List<String> fail_run=new ArrayList<>();
    private int addHotTick=20;
    private int speed=3;
    private int removeBack=0;
    public int getRemoveBack()
    {
        return removeBack;
    }

    public Material getMaterial()
    {
        return material;
    }

    public int getData()
    {
        return data;
    }
    public int getSpeed()
    {
        return speed;
    }
    public int getAddHotTick()
    {
        return addHotTick;
    }

    public int getModel()
    {
        return model;
    }
    public int getLevel()
    {
        return level;
    }
    public int getHot()
    {
        return hot;
    }
    public int getTime()
    {
        return time;
    }
    public String getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getMode()
    {
        return mode;
    }
    public String getResult_item()
    {
        return result_item;
    }
    public String getFail_item()
    {
        return fail_item;
    }
    public List<String> getLore()
    {
        return lore;
    }
    public List<String> getResult_run()
    {
        return result_run;
    }
    public List<String> getFail_run()
    {
        return  fail_run;
    }
    public double getChance()
    {
        return chance;
    }
    public double getMax_recipes_experience()
    {
       return max_recipes_experience;
    }
    public double getResult_add_cook_experience()
    {
        return result_add_cook_experience;
    }
    public double getResult_add_reicpes_experience()
    {
        return result_add_reicpes_experience;
    }
    public double getFail_add_cook_experience()
    {
        return fail_add_cook_experience;
    }
    public double getFail_add_reicpes_experience()
    {
        return fail_add_reicpes_experience;
    }
    public void setData(int data)
    {
        this.data=data;
    }
    public void setSpeed(int speed)
    {
        this.speed=speed;
    }
    public void setRemoveBack(int removeBack)
    {
        this.removeBack=removeBack;
    }
    public void setAddHotTick(int addHotTick)
    {
        this.addHotTick=addHotTick;
    }
    public void setMaterial(Material material)
    {
        this.material=material;
    }
    public void setModel(int model)
    {
        this.model=model;
    }
    public void setLore(List<String> lore)
    {
        this.lore=lore;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public void setMode(String mode)
    {
        this.mode=mode;
    }
    public void setLevel(int level)
    {
        this.level=level;
    }
    public void setHot(int hot)
    {
        this.hot=hot;
    }
    public void setTime(int time)
    {
        this.time=time;
    }
    public void setChance(double chance)
    {
        this.chance=chance;
    }
    public void setMax_recipes_experience(double maxRecipesExperience)
    {
        this.max_recipes_experience=maxRecipesExperience;
    }
    public void setResult_item(String resultItem)
    {
        this.result_item=resultItem;
    }
    public void setResult_add_cook_experience(double addCookExperience)
    {
        this.result_add_cook_experience=addCookExperience;
    }
    public void setResult_add_reicpes_experience(double addReicpesExperience)
    {
        this.result_add_reicpes_experience=addReicpesExperience;
    }
    public void setResult_run(List<String> run)
    {
        this.result_run=run;
    }
    public void setFail_item(String fail_item)
    {
        this.fail_item=fail_item;
    }
    public void setFail_add_cook_experience(double fail_add_cook_experience)
    {
        this.fail_add_cook_experience=fail_add_cook_experience;
    }
    public void setFail_add_reicpes_experience(double fail_add_reicpes_experience)
    {
        this.fail_add_reicpes_experience=fail_add_reicpes_experience;
    }
    public void setFail_run(List<String> fail_run)
    {
        this.fail_run=fail_run;
    }

    public static void clear(
    ){
        cache.clear();
    }

}
