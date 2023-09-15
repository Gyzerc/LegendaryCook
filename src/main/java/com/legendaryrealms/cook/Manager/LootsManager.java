package com.legendaryrealms.cook.Manager;

import com.legendaryrealms.cook.Data.PlayerData;
import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Loots.LootGroup;
import com.legendaryrealms.cook.Loots.Loots;
import com.legendaryrealms.cook.Utils.ItemUtils;
import com.legendaryrealms.cook.Utils.RunGroupUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LootsManager {


    public LootsManager()
    {
        int amount=0;
        if (!(new File("./plugins/LegendaryCook/Loot").exists()))
        {
            LegendaryCook.getInstance().saveResource("Loot/Loot1.yml",false);
        }
        for (String fileName:getFiles("./plugins/LegendaryCook/Loot"))
        {

            File file=new File("./plugins/LegendaryCook/Loot",fileName);
            YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
            String id=fileName.replace(".yml","");
            ConfigurationSection sec=yml.getConfigurationSection("loots");
            HashMap<Integer,List<LootGroup>> map=new HashMap<>();
            if (sec!=null)
            {
                for (String group:sec.getKeys(false))
                {
                    String path="loots."+group;
                    int weight=yml.getInt("loots."+group+".weight");
                    LootGroup lootGroup=new LootGroup(group, weight);
                    lootGroup.setChance(yml.getDouble(path+".chance"));
                    lootGroup.setLevel(yml.getInt(path+".level"));
                    lootGroup.setItem(yml.getString(path+".item"));
                    lootGroup.setRecipes_experience_min(yml.getDouble(path+".recipe-experience.min"));
                    lootGroup.setRecipes_experience_max(yml.getDouble(path+".recipe-experience.max"));
                    lootGroup.setRun(yml.getStringList(path+".run"));
                    if (yml.getConfigurationSection(path+".placeholder_equals.placeholders")!=null)
                    {
                        String method= yml.getString(path+".placeholder_equals.method","all");
                        boolean kf=method.equals("all") ? false : true;
                        lootGroup.setPapiOnlyOne(kf);
                        for (String key:yml.getConfigurationSection(path+".placeholder_equals.placeholders").getKeys(false))
                        {
                            lootGroup.addPapi(key,yml.getString(path+".placeholder_equals.placeholders."+key+".equal"),yml.getString(path+".placeholder_equals.placeholders."+key+".name"),yml.getString(path+".placeholder_equals.placeholders."+key+".value"));
                        }
                    }
                    List<LootGroup> list=map.get(weight)!=null ? map.get(weight) : new ArrayList<>();
                    list.add(lootGroup);
                    map.put(weight,list);
                }
            }
            Loots loots=new Loots(id,map);
            amount++;
        }
        Bukkit.getConsoleSender().sendMessage("[LegendaryCook] 加载 "+amount+" 个奖励组");
    }

    public boolean equalPlaceholderALL(Player p,LootGroup group)
    {
        for (String key:group.getPapis())
        {
            if (!group.compare(p,key))
            {
                return false;
            }
        }
        return true;
    }

    public boolean equalPlaceholderOne(Player p,LootGroup group)
    {
        for (String key: group.getPapis())
        {
            if (group.compare(p,key))
            {
                return true;
            }
        }
        return false;
    }

    public ItemStack getGroupItem(Player p, double experience, String loot)
    {

        Loots lootgroup= Loots.getLoots(loot);

        for (int weight:lootgroup.getWeights())
        {
            List<LootGroup> loots=lootgroup.getLoots(weight);
            for (LootGroup group:loots)
            {
                if (experience < group.getRecipes_experience_min() || experience > group.getRecipes_experience_max())
                {
                    continue;
                }
                if (group.isPapiOnlyOne())
                {
                    if (!equalPlaceholderOne(p,group))
                    {
                        continue;
                    }
                    else {
                        if (!equalPlaceholderALL(p,group))
                        {
                            continue;
                        }
                    }
                }
                ItemStack i=  LegendaryCook.getInstance().getItemUtils().getItemArg(p,null,group.getItem());
                return i!=null ? i : new ItemStack(Material.AIR);
            }
        }
        return new ItemStack(Material.AIR);
    }
    public void runGroup(Player p,double experience,String loot)
    {

        Loots lootgroup= Loots.getLoots(loot);

        for (int weight:lootgroup.getWeights())
        {
            List<LootGroup> loots=lootgroup.getLoots(weight);
            for (LootGroup group:loots)
            {
                int level=group.getLevel();
                if (level > PlayerData.getPlayerData(p).getCookLevel())
                {
                    continue;
                }
                if (experience < group.getRecipes_experience_min() || experience > group.getRecipes_experience_max())
                {
                    continue;
                }
                if (group.isPapiOnlyOne())
                {
                    if (!equalPlaceholderOne(p,group))
                    {
                        continue;
                    }
                    else {
                        if (!equalPlaceholderALL(p,group))
                        {
                            continue;
                        }
                    }
                }
                int chance=(int)group.getChance()*100;
                if ((new Random()).nextInt(101) <= chance)
                {
                    RunGroupUtils runGroupUtils=new RunGroupUtils();
                    runGroupUtils.Run(p,group.getRun());
                    LegendaryCook.getInstance().getItemUtils().giveItemArg(p,null,group.getItem());
                    p.sendMessage(group.getItem());
                }
            }
        }
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
