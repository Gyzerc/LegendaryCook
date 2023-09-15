package com.legendaryrealms.cook.Menu;

import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Utils.MsgUtils;
import eu.decentsoftware.holograms.api.utils.scheduler.S;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LMenu {

    private static  HashMap<String,LMenu> cache=new HashMap<String,LMenu>();
    private String title;
    private List<String> keys=new ArrayList<>();
    private int size;
    private String fileName;
    private List<Integer> layout;
    private HashMap<String , ItemStack> custom_item=new HashMap<>();
    private HashMap<String , List<Integer>> custom_slot=new HashMap<>();
    private HashMap<String , String> custom_fuction=new HashMap<>();
    private HashMap<String , String> custom_fuction_value=new HashMap<>();
    private HashMap<String,String> holder=new HashMap<>();
    private YamlConfiguration yml;
    private List<String> lore_format;
    public LMenu(String fileName)
    {

        this.fileName=fileName;
        File file=new File("./plugins/LegendaryCook/Menus",fileName+".yml");
        this.yml=YamlConfiguration.loadConfiguration(file);
        if (!file.exists())
        {
            LegendaryCook.getInstance().saveResource("Menus/"+fileName+".yml",false);
        }
        this.title= MsgUtils.msg(yml.getString("title",""));
        this.size= yml.getInt("size",54);
        this.layout= yml.getIntegerList("layout")!=null ? yml.getIntegerList("layout") : new ArrayList<>();
        this.lore_format=yml.contains("lore-format") ? yml.getStringList("lore-format") : new ArrayList<>();
        if (yml.getConfigurationSection("placeholder")!=null)
        {
            for (String key:yml.getConfigurationSection("placeholder").getKeys(false))
            {
                holder.put(key,MsgUtils.msg(yml.getString("placeholder."+key)));
            }
        }
        ConfigurationSection sec=yml.getConfigurationSection("customItems");
        if (sec!=null)
        {
            for (String key:sec.getKeys(false))
            {
                ItemStack i=new ItemStack(getMaterial(yml.getString("customItems."+key+".material")), yml.getInt("customItems."+key+".amount",1),(short)yml.getInt("customItems."+key+".data",0));
                ItemMeta id=i.getItemMeta();
                id.setDisplayName(MsgUtils.msg(yml.getString("customItems."+key+".display","")));
                List<String> l=yml.getStringList("customItems."+key+".lore")!=null ? MsgUtils.msg(yml.getStringList("customItems."+key+".lore")) : new ArrayList<>();
                id.setLore(l);
                i.setItemMeta(id);
                custom_item.put(key,i);
                custom_slot.put(key,yml.getIntegerList("customItems."+key+".slot"));
                custom_fuction.put(key,yml.getString("customItems."+key+".fuction.type","null"));
                String value=yml.getString("customItems."+key+".fuction.value") != null ? MsgUtils.msg(yml.getString("customItems."+key+".fuction.value")) : null;
                custom_fuction_value.put(key,value);
                keys.add(key);
            }
        }
        cache.put(fileName,this);
    }


    public static LMenu getLMenu(String fileName)
    {
        return cache.get(fileName);
    }

    public String getHolder(String key)
    {
        return holder.get(key)!=null ? holder.get(key) : "";
    }
    public YamlConfiguration getYml()
    {
        return yml;
    }
    public String getTitle()
    {
        return title;
    }
    public List<Integer> getLayout()
    {
        return layout;
    }
    public int getSize()
    {
        return size;
    }
    public String getFileName()
    {
        return fileName;
    }
    public String getFuction(String key)
    {
        return custom_fuction.get(key);
    }
    public String getFuction_Value(String key)
    {
        return custom_fuction_value.get(key);
    }
    public List<Integer> getSlot(String key)
    {
        return custom_slot.get(key);
    }
    public ItemStack getItem(String key)
    {
        return custom_item.get(key);
    }
    public List<String> getKeys()
    {
        return keys;
    }

    public List<String> getLore_format() {
        return lore_format;
    }

    public Material getMaterial(String material)
    {
        return Material.getMaterial(material) != null ? Material.getMaterial(material) : Material.STONE;
    }
}
