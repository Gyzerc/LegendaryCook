package com.legendaryrealms.cook.Manager;

import com.legendaryrealms.cook.Utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategorizesManager {

    private static HashMap<String, List<String>> cooks=new HashMap<>();
    private static HashMap<String, ItemStack> items=new HashMap<>();
    private static HashMap<String,String> display=new HashMap<>();

    public static void load()
    {
        File file=new File("./plugins/LegendaryCook","Categorizes.yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        int amount=0;
        if (yml.getConfigurationSection("")!=null) {
            for (String cat : yml.getConfigurationSection("").getKeys(false)) {

                ItemStack i=new ItemStack(Material.getMaterial(yml.getString(cat+".material")), yml.getInt(cat+".amount"),(short)yml.getInt(cat+".data"));
                ItemMeta id=i.getItemMeta();
                id.setDisplayName(MsgUtils.msg(yml.getString(cat+".display")));
                id.setLore(MsgUtils.msg(yml.getStringList(cat+".lore")));
                i.setItemMeta(id);
                items.put(cat,i);
                cooks.put(cat,yml.getStringList(cat+".cooks"));
                display.put(cat,MsgUtils.msg(yml.getString(cat+".display")));
                amount++;
            }
            Bukkit.getConsoleSender().sendMessage("[LegendaryCook] 加载 "+amount+" 个分类");
        }
    }
    public static List<String> getCooks(String cat)
    {
        return cooks.get(cat) != null ? cooks.get(cat) : new ArrayList<>();
    }
    public static String getDisplay(String cat)
    {
        return display.get(cat);
    }
}
