package com.legendaryrealms.cook.Manager;

import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.Data.Database.MysqlManager;
import com.legendaryrealms.cook.Data.Database.PlayerDataYamlManager;
import com.legendaryrealms.cook.Menu.Editor.SettingsFile;
import com.legendaryrealms.cook.LegendaryCook;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.List;

public class FileManager {


    public static void checkAndLoadFiles()
    {
        File file=new File("./plugins/LegendaryCook","config.yml");
        if (!file.exists())
        {
            LegendaryCook.getInstance().saveResource("config.yml",false);
            Bukkit.getLogger().fine("生成配置文件 config.yml");
        }
        LegendaryCook.getInstance().getConfigManager().load();

        file=new File("./plugins/LegendaryCook","message.yml");
        if (!file.exists())
        {
            LegendaryCook.getInstance().saveResource("message.yml",false);
            Bukkit.getLogger().fine("生成配置文件 message.yml");
        }
        MessageManager.load();

        file=new File("./plugins/LegendaryCook/Cooks");
        if (!file.exists())
        {
            LegendaryCook.getInstance().saveResource("Cooks/配方1.yml",false);
        }
        CooksManager.load();


        file=new File("./plugins/LegendaryCook/settings.yml");
        if (!file.exists())
        {
            LegendaryCook.getInstance().saveResource("settings.yml",false);
        }
        SettingsFile.load();


        file=new File("./plugins/LegendaryCook/Items.yml");
        if (!file.exists())
        {
            LegendaryCook.getInstance().saveResource("settings.yml",false);
        }
        SettingsFile.load();

        file=new File("./plugins/LegendaryCook/Categorizes.yml");
        if (!file.exists())
        {
            LegendaryCook.getInstance().saveResource("Categorizes.yml",false);
        }
        CategorizesManager.load();
    }

    public static void setData(String player,String data)
    {
        if (Config.useMysql)
        {
            MysqlManager.setPlayerString(player,data);
            return;
        }
        PlayerDataYamlManager.setDataFile(player,data);
    }

    public static String getData(String player)
    {
        if (Config.useMysql)
        {
            return  MysqlManager.getPlayerStringData(player);
        }
        return PlayerDataYamlManager.getData(player);
    }



}
