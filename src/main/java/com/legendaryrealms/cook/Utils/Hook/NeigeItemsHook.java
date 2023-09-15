package com.legendaryrealms.cook.Utils.Hook;

import org.bukkit.inventory.ItemStack;
import pers.neige.neigeitems.NeigeItems;
import pers.neige.neigeitems.manager.ItemManager;

public class NeigeItemsHook {

    public ItemStack getItem(String id)
    {
      return ItemManager.INSTANCE.getItemStack(id);
    }

    public boolean hasItem(String id)
    {
        return ItemManager.INSTANCE.hasItem(id);
    }

}
