package com.legendaryrealms.cook.Utils.Hook;

import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.inventory.ItemStack;

public class MythicMobHoook {

    public boolean isExists(String id)
    {
        return MythicMobs.inst().getItemManager().getItemStack(id)!=null;
    }

    public ItemStack getItem(String id,int amount)
    {
        ItemStack i=MythicMobs.inst().getItemManager().getItemStack(id);
        i.setAmount(amount);
        return i;
    }

}
