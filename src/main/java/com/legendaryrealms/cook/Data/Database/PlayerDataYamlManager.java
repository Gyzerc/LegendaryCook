package com.legendaryrealms.cook.Data.Database;

import com.legendaryrealms.cook.Data.PlayerData;
import com.legendaryrealms.cook.Manager.CooksManager;
import com.legendaryrealms.cook.Manager.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerDataYamlManager {

    public static YamlConfiguration getDataFile(String p)
    {
        File file=new File("./plugins/LegendaryCook/Data",p+".yml");
        if (!file.exists())
        {
            Bukkit.getConsoleSender().sendMessage("[LegendaryCook] 为玩家 "+p+" 创建数据缓存");
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        return yml;
    }

    public static boolean hasPlayerData(String player)
    {
        File file=new File("./plugins/LegendaryCook/Data",player+".yml");
        return file.exists();
    }

    public static String getData(String player)
    {
        File file=new File("./plugins/LegendaryCook/Data",player+".yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        return yml.getString("data")!= null ? yml.getString("data") : null;
    }

    public static void setDataFile(String p,String data)
    {
        File file=new File("./plugins/LegendaryCook/Data",p+".yml");
        YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
        yml.set("data",data);
        try {
            yml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static File[] getFiles(String path) {
        List<String> shops = new ArrayList<>();
        File file = new File(path);
        if (!file.exists())
            return new File[]{};
        File[] files = file.listFiles();
        return files;
    }
    public static void convert()
    {
        for (File file: getFiles("./plugins/LegendaryAlchemy/Data"))
        {
            YamlConfiguration yml=YamlConfiguration.loadConfiguration(file);
            String p=file.getName().replace(".yml","");
            Map<String , Object> stats=new HashMap<>();
            stats.put("level", yml.getInt("level"));
            stats.put("exp",yml.getDouble("exp"));
            stats.put("unlock",yml.getStringList("unlock"));
            PlayerData.create(p,stats);
            Bukkit.getConsoleSender().sendMessage(MessageManager.plugin+"已转换玩家 "+p+" 的数据...");

        }
    }


}
