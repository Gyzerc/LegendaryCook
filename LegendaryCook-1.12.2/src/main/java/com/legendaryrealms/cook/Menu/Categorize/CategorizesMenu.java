package com.legendaryrealms.cook.Menu.Categorize;

import com.legendaryrealms.cook.Data.Cook.Cook;
import com.legendaryrealms.cook.Data.PlayerData;
import com.legendaryrealms.cook.Manager.CategorizesManager;
import com.legendaryrealms.cook.Menu.LMenu;
import com.legendaryrealms.cook.Utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategorizesMenu implements InventoryHolder {
    private Inventory inv;

    private Player player;
    private int page;
    private String cat;

    private LMenu read;

    public CategorizesMenu(Player player, int page, String cat) {
        this.read=LMenu.getLMenu("CategorizesMenu");
        this.player = player;
        this.page = page;
        this.cat = cat;
        this.inv = Bukkit.createInventory(this,read.getSize(),read.getTitle().replace("%cat%", CategorizesManager.getDisplay(cat)));

    }
    private HashMap<Integer,String> slotToFuctiom=new HashMap<>();
    private HashMap<Integer,String> slotToCookId=new HashMap<>();
    public void load()
    {
        for (String key:read.getKeys())
        {
            for (int slot: read.getSlot(key))
            {
                inv.setItem(slot,read.getItem(key));
                slotToFuctiom.put(slot,read.getFuction(key));
            }
        }
        int in=0;
        for (String cook:getCooks(page))
        {
            if (Cook.getCook(cook)!=null)
            {
                Cook c=Cook.getCook(cook);
                ItemStack i =new ItemStack(c.getMaterial(),1,(short)c.getData());
                ItemMeta id=i.getItemMeta();
                id.setDisplayName(c.getName());
                ArrayList l=new ArrayList();
                for (String msg:read.getLore_format())
                {
                    if (msg.equals("%lore%"))
                    {
                        l.addAll(c.getLore());
                    }
                    else if (msg.equals("%lock%"))
                    {
                        if (!PlayerData.getPlayerData(player).getUnlocks().contains(cook)) {
                            l.add(read.getHolder("lock"));
                        }
                    }
                    else {
                        l.add(MsgUtils.msg(msg));
                    }
                }
                id.setLore(l);
                i.setItemMeta(id);
                inv.setItem(read.getLayout().get(in),i);
                slotToFuctiom.put(read.getLayout().get(in),"cook");
                slotToCookId.put(read.getLayout().get(in),cook);
            }
            in++;
        }
    }

    public String getCookId(int slot)
    {
        return slotToCookId.get(slot) ;
    }
    public String getFuction(int slot)
    {
        return slotToFuctiom.get(slot)!=null ? slotToFuctiom.get(slot) : "null";
    }
    public List<String> getCooks(int page)
    {
        int start=0 + (page-1)*read.getLayout().size();
        int end= read.getLayout().size() * page;
        List<String> cooks=CategorizesManager.getCooks(cat);
        List<String> list=new ArrayList<>();
        for (int get=start;get < end ; get++)
        {
            if (!cooks.isEmpty())
            {
                if (cooks.size() > get)
                {
                    list.add(cooks.get(get));
                }
            }
        }
        return list;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPage() {
        return page;
    }

    public String getCat() {
        return cat;
    }


    @NotNull
    @Override
    public Inventory getInventory() {
        return inv;
    }
}
