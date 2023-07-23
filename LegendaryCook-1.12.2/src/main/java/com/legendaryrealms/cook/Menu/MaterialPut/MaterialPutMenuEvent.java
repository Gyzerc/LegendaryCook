package com.legendaryrealms.cook.Menu.MaterialPut;

import com.legendaryrealms.cook.Data.Cook.CookPot;
import com.legendaryrealms.cook.Manager.MessageManager;
import com.legendaryrealms.cook.Menu.LMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class MaterialPutMenuEvent implements Listener {

    @EventHandler
    public void onMaterialPutMenuEvent(InventoryClickEvent e)
    {
        Player p=(Player) e.getWhoClicked();
        MaterialPutMenu menu=getMenu(e.getInventory());
        if (menu!=null)
        {
            if (e.getRawSlot() >=0 && e.getRawSlot()< LMenu.getLMenu("MaterialPut").getSize())
            {
                if (e.getRawSlot() == menu.getPut_slot())
                {
                    return;
                }
                e.setCancelled(true);
                if (menu.getFuction(e.getRawSlot()).equals("confirm"))
                {
                    CookPot pot=CookPot.getCookPot(p);
                    if (pot == null || !pot.isInGame())
                    {
                        p.closeInventory();
                        return;
                    }
                    ItemStack i=e.getInventory().getItem(menu.getPut_slot());
                    if (i!=null &&! i.getType().equals(Material.AIR))
                    {
                        if (pot == null || !pot.isInGame())
                        {
                            p.getInventory().addItem(i);
                            return;
                        }
                        pot.getGame().addMaterial(i);
                        p.closeInventory();
                    }
                }
                if (menu.getFuction(e.getRawSlot()).equals("cancel"))
                {
                    CookPot pot=CookPot.getCookPot(p);
                    if (pot == null || !pot.isInGame())
                    {
                        p.closeInventory();
                        return;
                    }
                    pot.getGame().cancel();
                    p.sendMessage(MessageManager.plugin+MessageManager.cook_cancel);
                    p.sendTitle(MessageManager.cook_cancel_title.split(";")[0],MessageManager.cook_cancel_title.split(";")[1]);
                    p.closeInventory();
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onMaterialPutMenuClose(InventoryCloseEvent e)
    {
        Player p= (Player) e.getPlayer();
        MaterialPutMenu menu=getMenu(e.getInventory());
        if (menu!=null)
        {
            CookPot pot=CookPot.getCookPot(p);
            if (pot!=null)
            {
                if (pot.isInGame())
                {
                    pot.getGame().getGame().setCanUpOrDown(true);
                }
            }
        }
    }

    public MaterialPutMenu getMenu(Inventory inv)
    {
        InventoryHolder holder=inv.getHolder();
        if (holder instanceof MaterialPutMenu)
        {
            return (MaterialPutMenu) holder;
        }
        return null;
    }
}
