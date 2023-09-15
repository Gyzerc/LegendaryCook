package com.legendaryrealms.cook.ListenerManager;

import com.legendaryrealms.cook.Data.Cook.CookPot;
import com.legendaryrealms.cook.Manager.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class ChangeWorldEvent implements Listener {
    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent e)
    {
        Player p=e.getPlayer();
        if (CookPot.getCookPot(p)!=null)
        {
            p.sendMessage(MessageManager.plugin+MessageManager.pot_remove);
            CookPot.getCookPot(p).removePot();
        }
    }
}
