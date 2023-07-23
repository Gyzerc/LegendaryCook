package com.legendaryrealms.cook.ListenerManager;

import com.legendaryrealms.cook.Data.PlayerData;
import com.legendaryrealms.cook.Data.Cook.CookPot;
import com.legendaryrealms.cook.Manager.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        Player p=e.getPlayer();
        PlayerData data=PlayerData.getPlayerData(p);
        if (data!=null)
        {
            data.save();
        }
        if (CookPot.getCookPot(p)!=null)
        {
            Bukkit.getConsoleSender().sendMessage(MessageManager.plugin+"已移除玩家 "+p.getName()+" 的烹饪锅");
            CookPot.getCookPot(p).removePot();
        }
    }
}
