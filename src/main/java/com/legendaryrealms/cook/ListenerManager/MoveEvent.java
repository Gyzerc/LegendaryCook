package com.legendaryrealms.cook.ListenerManager;

import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.Data.Cook.CookPot;
import com.legendaryrealms.cook.Manager.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        Player p=e.getPlayer();
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ())
        {
            return;
        }
        if (CookPot.getCookPot(p)!=null)
        {
            if (e.getTo().distance(CookPot.getCookPot(p).getLocation()) >= Config.pot_block_break_distance)
            {
                p.sendMessage(MessageManager.plugin+MessageManager.pot_remove);
                CookPot.getCookPot(p).removePot();
            }
        }

    }
}
