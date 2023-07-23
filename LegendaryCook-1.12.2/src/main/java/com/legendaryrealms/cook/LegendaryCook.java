package com.legendaryrealms.cook;

import com.legendaryrealms.cook.Command.Commands;
import com.legendaryrealms.cook.Command.CommandsTab;
import com.legendaryrealms.cook.Command.ConvertCommand;
import com.legendaryrealms.cook.Data.PlayerData;
import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.Data.Database.MysqlManager;
import com.legendaryrealms.cook.Manager.LootsManager;
import com.legendaryrealms.cook.Manager.MenusManager;
import com.legendaryrealms.cook.Data.Cook.CookPot;
import com.legendaryrealms.cook.Manager.FileManager;
import com.legendaryrealms.cook.Manager.ListenerManager;
import com.legendaryrealms.cook.Menu.Categorize.CategorizesMenuEvent;
import com.legendaryrealms.cook.Menu.Main.MainMenuEvent;
import com.legendaryrealms.cook.Menu.MaterialPut.MaterialPutMenuEvent;
import com.legendaryrealms.cook.Utils.Hook.Hooks;
import com.legendaryrealms.cook.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class LegendaryCook extends JavaPlugin {


    private static LegendaryCook plugin;

    private ItemUtils itemUtils;
    private Hooks hooks;
    private LootsManager lootsManager=null;
    @Override
    public void onLoad() {
        plugin=this;
    }

    @Override
    public void onEnable() {
        plugin=this;
        FileManager.checkAndLoadFiles();
        if (Config.useMysql) {
            MysqlManager.setConnectPool();
        }
        //Config.load();
        new ListenerManager().registerAll();
        itemUtils=new ItemUtils();
        hooks=new Hooks();
        MenusManager.load();
        lootsManager=new LootsManager();
        Bukkit.getPluginCommand("legendarycook").setExecutor(new Commands());
        Bukkit.getPluginCommand("legendarycook_convert").setExecutor(new ConvertCommand());
        Bukkit.getPluginCommand("legendarycook").setTabCompleter(new CommandsTab());
        Bukkit.getPluginManager().registerEvents(new MaterialPutMenuEvent(), LegendaryCook.getInstance());
        Bukkit.getPluginManager().registerEvents(new CategorizesMenuEvent(), LegendaryCook.getInstance());
        Bukkit.getPluginManager().registerEvents(new MainMenuEvent(), LegendaryCook.getInstance());
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
        {new LegendaryCookPlaceholder().register();}
    }


    @Override
    public void onDisable() {
        PlayerData.saveAllOnline();
        CookPot.removeAll();
    }



    public Config getConfigManager()
    {
        return new Config();
    }
    public ItemUtils getItemUtils() {return itemUtils;}
    public Hooks getHooks()
    {
        return hooks;
    }

    public static LegendaryCook getInstance()
    {
        return plugin;
    }
    public LootsManager getLootsManager(){return lootsManager;}



    public void reload()
    {


        FileManager.checkAndLoadFiles();
        itemUtils=new ItemUtils();
        hooks=new Hooks();
        MenusManager.load();
        lootsManager=new LootsManager();

    }

}
