package com.legendaryrealms.cook.Manager;

import com.legendaryrealms.cook.Data.Cook.Cook;
import com.legendaryrealms.cook.Data.Cook.CookRecipe;
import com.legendaryrealms.cook.Utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class CooksManager {

    public static void load()
    {

        if (!getFiles("./plugins/LegendaryCook/Cooks").isEmpty())
        {
            int a=0;
            for (String cooks:getFiles("./plugins/LegendaryCook/Cooks"))
            {
                String cook=cooks.replace(".yml","");
                File file=new File("./plugins/LegendaryCook/Cooks",cooks);
                YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
                Cook data=new Cook(cook);

                data.setName(MsgUtils.msg(yml.getString("display.name","")));
                data.setData(yml.getInt("display.data",0));
                data.setModel(yml.getInt("display.model",0));
                data.setLore(MsgUtils.msg(yml.getStringList("display.lore")));
                data.setMode(yml.getString("settings.mode","mode1"));
                data.setSpeed(yml.getInt("settings.game.mode2.speed",3));
                data.setAddHotTick(yml.getInt("settings.game.mode1.addHotTick",20));
                data.setChance(yml.getDouble("settings.chance",1.0));
                data.setMax_recipes_experience(yml.getDouble("settings.max_recipes_experience",50.0));
                data.setLevel(yml.getInt("settings.limit.level",0));
                data.setHot(yml.getInt("settings.limit.hot",10));
                data.setTime(yml.getInt("settings.limit.time",60));
                data.setResult_item(yml.getString("settings.result.item",""));
                data.setResult_add_cook_experience(yml.getDouble("settings.result.add_cook_experience",5.0));
                data.setResult_add_reicpes_experience(yml.getDouble("settings.result.add_recipes_experince",1.0));
                data.setResult_run(getList("settings.result.run",yml,new ArrayList<>()));
                data.setFail_item(yml.getString("settings.fail.item",""));
                data.setFail_add_cook_experience(yml.getDouble("settings.fail.add_cook_experience",0.0));
                data.setFail_add_reicpes_experience(yml.getDouble("settings.fail.add_recipes_experince",0.0));
                data.setFail_run(getList("settings.fail.run",yml,new ArrayList<>()));
                data.setRemoveBack(yml.getInt("settings.fail.remove",0));
                if (yml.getConfigurationSection("recipes")!=null)
                {
                    CookRecipe recipe=new CookRecipe(cook);
                    for (String in:yml.getConfigurationSection("recipes").getKeys(false))
                    {
                        recipe.add(yml.getInt("recipes."+in+".hot"), yml.getItemStack("recipes."+in+".item") , yml.getInt("recipes."+in+".amount"));
                    }
                }
                a++;
            }
            Bukkit.getConsoleSender().sendMessage(MessageManager.plugin+"加载 "+a+" 个烹饪配方");

        }
    }



    private static List<String> getList(String path,YamlConfiguration yml,List<String> def)
    {
        if (yml.contains(path))
        {
            return yml.getStringList(path);
        }
        return def;
    }

    public static List<String> getFiles(String path) {
        List<String> shops = new ArrayList<>();
        File file = new File(path);
        if (!file.exists())
            return shops;
        File[] files = file.listFiles();
        byte b;
        int i;
        File[] arrayOfFile1;
        for (i = (arrayOfFile1 = files).length, b = 0; b < i; ) {
            File f = arrayOfFile1[b];
            if (!f.isDirectory())
                shops.add(f.getName());
            b++;
        }
        return shops;
    }

}
