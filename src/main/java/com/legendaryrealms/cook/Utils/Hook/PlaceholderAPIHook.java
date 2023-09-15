package com.legendaryrealms.cook.Utils.Hook;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import pers.neige.neigeitems.hook.placeholderapi.PlaceholderExpansion;

public class PlaceholderAPIHook {

    public String getPlaceholder(Player p,String papi)
    {
      return   PlaceholderAPI.setPlaceholders(p,papi);
    }

}
