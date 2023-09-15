package com.legendaryrealms.cook.Manager;

import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Menu.Main.MainMenuEvent;
import com.legendaryrealms.cook.Menu.Categorize.CategorizesMenuEvent;
import com.legendaryrealms.cook.Menu.LMenu;
import com.legendaryrealms.cook.Menu.MaterialPut.MaterialPutMenuEvent;
import org.bukkit.Bukkit;

public class MenusManager {

    public static void load()
    {
        new LMenu("MaterialPut");
        new LMenu("CategorizesMenu");
        new LMenu("MainMenu");
    }
}
