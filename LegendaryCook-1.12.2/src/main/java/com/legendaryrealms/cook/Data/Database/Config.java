package com.legendaryrealms.cook.Data.Database;

import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

public class Config {

    public static boolean useMysql;
    public static int maxlevel;
    public static HashMap<Integer,Double> exp=new HashMap<>();
    public static String hot;
    public static String hot_string;

    public static List<String> pot_text=new ArrayList<>();
    public static String pot_block_type;
    public static String pot_block_value;
    public static String hologram_plugin;
    public static double hologram_offset_x;
    public static double hologram_offset_y;
    public static double hologram_offset_z;
    public static int pot_block_break_distance;
    public static int pot_block_max_put_distance;
    public static boolean pot_block_limit_world_whitelist;
    public static List<String> pot_block_limit_worlds;

    public static String sound_cook_put;
    public static String sound_cook_begin;
    public static String effect_process;
    public static String effect_success;
    public static String effect_fail;

    public static void load()
    {
        FileConfiguration yml= LegendaryCook.getInstance().getConfig();

        useMysql=yml.getString("Store").equals("mysql");
        maxlevel=yml.getInt("settings.level.max",5);
        try {
            for (String level:yml.getConfigurationSection("settings.level.exp").getKeys(false))
            {
                exp.put(Integer.parseInt(level), yml.getDouble("settings.level.exp."+level));
            }
        } catch (Exception exception)
        {
            exception.printStackTrace();
            Bukkit.getLogger().warning("config.yml 下的settings.level.exp 配置出错！");
        }

        hot= MsgUtils.msg(yml.getString("settings.bar.hot","✦"));
        hot_string=MsgUtils.msg(yml.getString("settings.bar.string","&#e0ffff&l火候值: %hot%"));

        hologram_plugin=yml.getString("settings.pot.hologram.plugin","holographicdisplays");
        hologram_offset_x=yml.getDouble("settings.pot.hologram.offset.x",0);
        hologram_offset_y=yml.getDouble("settings.pot.hologram.offset.y",2);
        hologram_offset_z=yml.getDouble("settings.pot.hologram.offset.z",0);
        pot_text=yml.getStringList("settings.pot.hologram.text");
        pot_block_type=yml.getString("settings.pot.block.type","default");
        pot_block_value=yml.getString("settings.pot.block.value","CAULDRON");

        pot_block_break_distance=yml.getInt("settings.pot.put.break_distance",10);
        pot_block_max_put_distance=yml.getInt("settings.pot.put.max_put_distance",4);
        pot_block_limit_world_whitelist=yml.getBoolean("settings.pot.put.limit.world.whiteblack",false);
        pot_block_limit_worlds=yml.getStringList("settings.pot.put.limit.world.list");

        sound_cook_put=yml.getString("settings.sound.cook_put","ITEM_BOTTLE_FILL");
        sound_cook_begin=yml.getString("settings.sound.cook_begin","BLOCK_NOTE_BLOCK_BELL");

        effect_process=yml.getString("settings.effect.progress","DRIP_LAVA");
        effect_fail=yml.getString("settings.effect.fail","VILLAGER_ANGRY");
        effect_success=yml.getString("settings.effect.success","VILLAGER_HAPPY");

    }


    public static Map<String,Object> toCookData(String data)
    {
        byte[] bytes= Base64.getDecoder().decode(data);
        try (ByteArrayInputStream stream=new ByteArrayInputStream(bytes);
             BukkitObjectInputStream inputStream=new BukkitObjectInputStream(stream)){
            return (Map<String,Object>) inputStream.readObject();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public static String toString(Map<String,Object> data)
    {
        try (ByteArrayOutputStream stream=new ByteArrayOutputStream();
             BukkitObjectOutputStream outputStream=new BukkitObjectOutputStream(stream)){
            outputStream.writeObject(data);
            return Base64.getEncoder().encodeToString(stream.toByteArray());
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
