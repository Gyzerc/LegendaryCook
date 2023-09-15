package com.legendaryrealms.cook.Menu.MaterialPut;

import com.legendaryrealms.cook.Data.Cook.CookPot;
import com.legendaryrealms.cook.Game.CookGame;
import com.legendaryrealms.cook.Menu.LMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MaterialPutMenu implements InventoryHolder {
    private Inventory inv;
    private Player p;
    private final LMenu read=LMenu.getLMenu("MaterialPut");
    private CookGame game;
    private HashMap<Integer,String> slotToFuction=new HashMap<>();
    private int put_slot;
    public MaterialPutMenu(Player p)
    {
        this.p=p;
        this.game= CookPot.getCookPot(p).getGame();
        this.inv= Bukkit.createInventory(this,read.getSize(),read.getTitle().replace("%hot%",""+game.getHot()));
        for (String key:read.getKeys())
        {
            String fuction=read.getFuction(key);
            for (int slot:read.getSlot(key))
            {
                inv.setItem(slot,read.getItem(key));
                slotToFuction.put(slot,key);
            }
        }
        this.put_slot=read.getYml().getInt("put_slot");
    }

    public int getPut_slot()
    {
        return put_slot;
    }
    public String getFuction(int slot)
    {
        return slotToFuction.containsKey(slot) ? slotToFuction.get(slot) : "null";
    }
    public void open()
    {
        p.openInventory(inv);
        game.getGame().setCanUpOrDown(false);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inv;
    }
}
