package com.legendaryrealms.cook.Menu.Main;

import com.legendaryrealms.cook.Menu.LMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MainMenu implements InventoryHolder {
    private Inventory inv;
    private Player p;

    private LMenu read;
    private HashMap<Integer,String> slotToFuction=new HashMap<>();
    private HashMap<Integer,String> slotToKey=new HashMap<>();
    public MainMenu(Player p) {

        this.p = p;
        this.read=LMenu.getLMenu("MainMenu");
        this.inv= Bukkit.createInventory(this,read.getSize(),read.getTitle());

        for (String key:read.getKeys())
        {
            for (int slot: read.getSlot(key))
            {
                inv.setItem(slot,read.getItem(key));
                slotToFuction.put(slot,read.getFuction(key));
                slotToKey.put(slot,key);
            }
        }
    }

    public String getFuction(int slot)
    {
        return slotToFuction.get(slot)!=null ? slotToFuction.get(slot) : "null";
    }
    public String getKey(int slot)
    {
        return slotToKey.get(slot);
    }

    public void open()
    {p.openInventory(inv);}


    @NotNull
    @Override
    public Inventory getInventory() {
        return inv;
    }
}
