package com.legendaryrealms.cook.ListenerManager;

import com.legendaryrealms.cook.Data.PlayerData;
import com.legendaryrealms.cook.LegendaryCook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p=e.getPlayer();
        new BukkitRunnable()
        {
            @Override
            public void run() {
                if (PlayerData.hasData(p.getName()))
                {
                    PlayerData data=new PlayerData(p);
                }
                else {
                    Map<String,Object> stats=new HashMap<>();
                    stats.put("level",0);
                    stats.put("exp",0.0);
                    stats.put("unlock",new ArrayList<String>());
                    stats.put("success",new ArrayList<Integer>());
                    PlayerData data=new PlayerData(p,stats);
                    data.save();
                }
            }
        }.runTaskAsynchronously(LegendaryCook.getInstance());

    }

}
