package com.legendaryrealms.cook.Data.Pot;

import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Manager.MessageManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PotBlock {

    private static HashMap<Block,PotBlock> cache_default=new HashMap<>();
    private Block b=null;
    private Block cb=null;
    private Player p;
    private Location loc;
    private UUID uuid;
    public PotBlock(Player p, Location loc)
    {
        this.p=p;
        this.loc=loc;
        String type=Config.pot_block_type;
        switch (type)
        {
            case "default":
                Block b=loc.getBlock();
                b.setType(Material.getMaterial(Config.pot_block_value));
                this.b=b;
                cache_default.put(b,this);
                break;
            default:
                p.sendMessage(MessageManager.plugin+"出现错误！请联系管理员");
                return;
        }
    }
    public void remove()
    {
        if (b != null)
        {
            b.setType(Material.AIR);
            cache_default.remove(b);
            b=null;
        }
    }


    public static boolean canPut(Player p)
    {
        List<String> worlds=Config.pot_block_limit_worlds;
        if (Config.pot_block_limit_world_whitelist)
        {
            if (!worlds.contains(p.getWorld().getName()))
            {
                p.sendMessage(MessageManager.plugin+MessageManager.pot_cant_world);
                return false;
            }
        }else{
            if (worlds.contains(p.getWorld().getName()))
            {
                p.sendMessage(MessageManager.plugin+MessageManager.pot_cant_world);
                return false;
            }
        }
        int maxput=Config.pot_block_max_put_distance;
        if (p.getTargetBlock(null,maxput).getLocation().distance(p.getLocation()) <= maxput)
        {
            if (!p.getTargetBlock(null, 3).isLiquid() && !p.getTargetBlock(null, 3).isEmpty()) {
                Location loc = p.getTargetBlock(null, maxput).getLocation();
                if (p.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ()).getType().equals(Material.AIR) && p.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + 2, loc.getBlockZ()).getType().equals(Material.AIR) && p.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + 3, loc.getBlockZ()).getType().equals(Material.AIR)) {
                    return true;
                }
            }
        }
        p.sendMessage(MessageManager.plugin+MessageManager.pot_cant);
        return false;

    }


    public static PotBlock getPotBlock(Block b)
    {
        if (cache_default.containsKey(b))
        {
            return cache_default.get(b);
        }
        return null;
    }
    public Player getPlayer()
    {
        return p;
    }
    public Location getLocation()
    {
        return loc;
    }

}
