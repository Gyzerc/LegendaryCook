package com.legendaryrealms.cook.Menu.Main;

import com.legendaryrealms.cook.Menu.Categorize.CategorizesMenu;
import com.legendaryrealms.cook.Menu.Editor.EditorMenu;
import com.legendaryrealms.cook.Menu.LMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MainMenuEvent implements Listener {
    @EventHandler
    public void onMainMenuEvent(InventoryClickEvent e)
    {
        Player p=(Player) e.getWhoClicked();
        MainMenu menu=getMenu(e.getInventory());
        if (menu!=null)
        {
            e.setCancelled(true);
            if (e.getRawSlot() >=0)
            {
                String fuction=menu.getFuction(e.getRawSlot());
                switch (fuction){
                    case "null":
                        break;
                    case "close":
                        p.closeInventory();
                        break;
                    case "categorize":
                        String value= LMenu.getLMenu("MainMenu").getFuction_Value(menu.getKey(e.getRawSlot()));
                        if (value!=null)
                        {
                            CategorizesMenu menu1=new CategorizesMenu(p,1,value);
                            menu1.load();
                            p.openInventory(menu1.getInventory());
                            break;
                        }
                        break;
                }
            }
        }
    }
    public MainMenu getMenu(Inventory inv)
    {
        InventoryHolder holder=inv.getHolder();
        if (holder instanceof MainMenu)
        {
            return (MainMenu) holder;
        }
        return null;
    }
}
