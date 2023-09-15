package com.legendaryrealms.cook.Menu.Categorize;

import com.legendaryrealms.cook.Data.Cook.Cook;
import com.legendaryrealms.cook.Data.Cook.CookPot;
import com.legendaryrealms.cook.Data.PlayerData;
import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Manager.MessageManager;
import com.legendaryrealms.cook.Menu.Editor.EditorMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CategorizesMenuEvent implements Listener {
    @EventHandler
    public void onCatClick(InventoryClickEvent e)
    {
        Player p=(Player) e.getWhoClicked();
        CategorizesMenu menu=getMenu(e.getInventory());
        if (menu!=null)
        {
            e.setCancelled(true);
            if (e.getRawSlot() >=0)
            {
                String fuction=menu.getFuction(e.getRawSlot());
                switch (fuction)
                {
                    case "null":
                        break;
                    case "pre":
                        if (menu.getPage() > 1)
                        {
                            menu=new CategorizesMenu(p,(menu.getPage()-1),menu.getCat());
                            menu.load();
                            p.openInventory(menu.getInventory());
                            return;
                        }
                        break;
                    case "next":
                        if (!menu.getCooks((menu.getPage()+1)).isEmpty())
                        {
                            menu=new CategorizesMenu(p,(menu.getPage()+1),menu.getCat());
                            menu.load();
                            p.openInventory(menu.getInventory());
                            return;
                        }
                        break;
                    case "cook":
                        if (CookPot.getCookPot(p)!=null)
                        {
                            if (!CookPot.getCookPot(p).isInGame())
                            {
                                String cook=menu.getCookId(e.getRawSlot());
                                if (cook!=null)
                                {
                                    if (e.isLeftClick()) {
                                        if (PlayerData.getPlayerData(p).getUnlocks().contains(cook) || p.hasPermission("LegendaryCook.categorize." + menu.getCat())) {
                                            CookPot.getCookPot(p).startCook(menu.getCookId(e.getRawSlot()));
                                            p.closeInventory();
                                            return;
                                        }
                                        p.sendMessage(MessageManager.plugin + MessageManager.cook_locked);
                                    }
                                    if (e.isRightClick()){
                                        Inventory inv= Bukkit.createInventory(p,9,"预览");
                                        inv.setItem(4, LegendaryCook.getInstance().getItemUtils().getItemArg(p,cook, Cook.getCook(cook).getResult_item()));
                                        p.openInventory(inv);
                                    }
                                }
                            }
                        }
                        break;
                }

            }
        }
    }
    public CategorizesMenu getMenu(Inventory inv)
    {
        InventoryHolder holder=inv.getHolder();
        if (holder instanceof CategorizesMenu)
        {
            return (CategorizesMenu) holder;
        }
        return null;
    }

}
