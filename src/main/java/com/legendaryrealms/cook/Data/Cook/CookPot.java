package com.legendaryrealms.cook.Data.Cook;

import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.Data.Pot.PotBlock;
import com.legendaryrealms.cook.Data.Pot.PotHolo;
import com.legendaryrealms.cook.Game.CookGame;
import com.legendaryrealms.cook.Manager.MessageManager;
import com.legendaryrealms.cook.Utils.MsgUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CookPot {

    private static HashMap<Player,CookPot> cache=new HashMap<>();
    private Player p;
    private Location loc;

    private PotHolo holo;
    private PotBlock potBlock;
    private CookGame game=null;

    public CookPot(Player p, Location loc)
    {
        this.p=p;
        this.loc=loc;
        cache.put(p,this);
        List<String> texts= new ArrayList<>(Config.pot_text);
        texts.replaceAll(x -> x.replace("%cook%","无").replace("%progress%","0%"));
        this.holo=new PotHolo(loc,texts);
        this.potBlock=new PotBlock(p,loc);

    }


    public void updataHolo(String cook,int put,int amount)
    {
        double perc= (double) put / (double) amount;
        DecimalFormat df=new DecimalFormat("#.00");
        double pro= perc * 100.0;
        String progress=""+pro;
        if (pro > 0)
        {
            progress=df.format(pro);
        }
        List<String> texts= new ArrayList<>(Config.pot_text);
        String finalProgress = progress;
        texts.replaceAll(x -> x.replace("%cook%",cook).replace("%progress%", finalProgress +"%"));
        holo.update(texts);

    }

    public void startCook(String cookId)
    {
        if (isInGame())
        {
            p.sendMessage(MessageManager.plugin+"操作失败..");
            return;
        }
        game=new CookGame(p,cookId);
        String main=MessageManager.cook_begin_title.split(";")[0] != null ? MessageManager.cook_begin_title.split(";")[0] : "&#FFC125&l开始烹饪";
        String sub=MessageManager.cook_begin_title.split(";")[1] != null ? MessageManager.cook_begin_title.split(";")[1] : "%cook%";
        p.sendTitle(main.replace("%cook%",Cook.getCook(cookId).getName()),sub.replace("%cook%",Cook.getCook(cookId).getName()));
        p.playSound(p.getLocation(), Sound.valueOf(Config.sound_cook_begin),1,1);
        updataHolo(Cook.getCook(cookId).getName(),0,CookRecipe.getCookRecipe(cookId).getAmounts());

    }

    public CookGame getGame()
    {
        return game;
    }

    public boolean isInGame()
    {
        if (game == null)
        {
            return false;
        }
        return game.getGame()!=null;
    }

    public void StopGame()
    {
        if (isInGame())
        {
            game.cancel();
            game=null;
        }
    }

    public void removePot()
    {
        potBlock.remove();
        loc.getBlock().setType(Material.AIR);
        holo.removeHolo();
        cache.remove(p);

    }

    public static void removeAll()
    {
        if (cache.isEmpty())
        {
            return;
        }
        for (Player p:cache.keySet())
        {
            cache.get(p).removePot();
            cache.remove(p);
        }
    }

    public Player getPlayer()
    {
        return p;
    }

    public Location getLocation()
    {
        return loc;
    }

    public static CookPot getCookPot(Player p)
    {
        return cache.get(p);
    }

}
