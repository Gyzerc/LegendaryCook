package com.legendaryrealms.cook.Manager;

import com.legendaryrealms.cook.Menu.Editor.EditorMenuEvent;
import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.ListenerManager.*;
import org.bukkit.Bukkit;

public class ListenerManager {

    public void registerAll()
    {
        Bukkit.getPluginManager().registerEvents(new JoinEvent(),LegendaryCook.getInstance());
        Bukkit.getPluginManager().registerEvents(new EditorMenuEvent(), LegendaryCook.getInstance());
        Bukkit.getPluginManager().registerEvents(new ChangeWorldEvent(),LegendaryCook.getInstance());
        Bukkit.getPluginManager().registerEvents(new QuitEvent(),LegendaryCook.getInstance());
        Bukkit.getPluginManager().registerEvents(new MoveEvent(),LegendaryCook.getInstance());

        String type=LegendaryCook.getInstance().getConfig().getString("settings.pot.block.type","default");
        switch (type)
        {
            case "default":
                Bukkit.getPluginManager().registerEvents(new BreakEvent(),LegendaryCook.getInstance());
                Bukkit.getPluginManager().registerEvents(new InteractBlock(),LegendaryCook.getInstance());
                break;
        }


    }
}
