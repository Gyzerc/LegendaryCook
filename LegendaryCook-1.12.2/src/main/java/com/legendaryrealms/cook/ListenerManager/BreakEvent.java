package com.legendaryrealms.cook.ListenerManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakEvent implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent e)
    {
        Player p=e.getPlayer();
        if (!e.getBlock().getMetadata("LegendaryCook").isEmpty())
        {
            e.setCancelled(true);
        }
    }
}
