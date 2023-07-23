package com.legendaryrealms.cook.Data;

import com.legendaryrealms.cook.Data.Cook.Cook;
import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.Data.Database.MysqlManager;
import com.legendaryrealms.cook.Data.Database.PlayerDataYamlManager;
import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Manager.FileManager;
import com.legendaryrealms.cook.Manager.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerData implements Serializable {

    private static HashMap<Player, PlayerData> cache=new HashMap<>();

    public static PlayerData getPlayerData(Player p)
    {
        return cache.get(p)!=null ? cache.get(p) : new PlayerData(p);
    }

    private Player p;
    private Map<String,Object> data=new HashMap<>();
    public PlayerData(Player p, Map<String,Object> data)
    {
        this.p=p;
        this.data=data;
        cache.put(p,this);
    }

    public PlayerData(Player p)
    {
        this.p=p;
        this.data=Config.toCookData( FileManager.getData(p.getName()));
        cache.put(p,this);
    }



    public double getRecipe_Experience(String cookId)
    {
        HashMap<String,Double> experience=data.get("recipes_experience")!=null ? (HashMap<String, Double>) data.get("recipes_experience") :new HashMap<>();
        return experience.containsKey(cookId) ? experience.get(cookId) : 0.0;
    }

    public void addRecipe_Experience(String cookId,double add)
    {
        HashMap<String,Double> experience=data.get("recipes_experience")!=null ? (HashMap<String, Double>) data.get("recipes_experience") :new HashMap<>();
        double now=getRecipe_Experience(cookId);
        double max=Cook.getCook(cookId).getMax_recipes_experience();
        if (max == -1)
        {
            return;
        }
        double amount=now+add >= max ?  (now+add)-max : add;
        experience.put(cookId,getRecipe_Experience(cookId)+add);
        p.sendMessage(MessageManager.plugin+MessageManager.cook_add_recipes_experience.replace("%cook%",Cook.getCook(cookId).getName()).replace("%amount%",""+amount));
    }
    public void takeRecipe_Experience(String cookId,double take)
    {
        HashMap<String,Double> experience=data.get("recipes_experience")!=null ? (HashMap<String, Double>) data.get("recipes_experience") :new HashMap<>();
        double now=getRecipe_Experience(cookId);
        double amount=now-take >= 0 ? take : now;
        experience.put(cookId,getRecipe_Experience(cookId)-amount);
        p.sendMessage(MessageManager.plugin+MessageManager.cook_take_recipes_experience.replace("%cook%",Cook.getCook(cookId).getName()).replace("%amount%",""+amount));
    }

    public static boolean hasData(String player)
    {
        if (Config.useMysql)
        {
            return MysqlManager.hasPlayerData(player);
        }
        return PlayerDataYamlManager.hasPlayerData(player);
    }

    public static void create(String p,Map<String,Object> data)
    {
        FileManager.setData(p,Config.toString(data));
    }

    public void save()
    {
        FileManager.setData(p.getName(),Config.toString(this.data));
    }


    public void addCookLevel(int amount)
    {
        int old=getCookLevel();
        int newlevel=old+amount <= LegendaryCook.getInstance().getConfigManager().maxlevel ? old+amount : LegendaryCook.getInstance().getConfigManager().maxlevel;
        data.put("level",newlevel);
        p.sendMessage(MessageManager.plugin+MessageManager.level_up.replace("%amount%",(newlevel-old)+"").replace("%level%",newlevel+""));

    }
    public void takeCookLevel(int amount)
    {
        int old=getCookLevel();
        int newlevel=old-amount >= 0 ? old-amount : 0;
        data.put("level",newlevel);
        p.sendMessage(MessageManager.plugin+MessageManager.level_take.replace("%amount%",(old-newlevel)+"").replace("%level%",newlevel+""));

    }

    public void takeCookExp(double amount)
    {

        double old=getCookExp();
        double newexp=old-amount >= 0.0 ? old-amount : 0.0;
        data.put("exp",newexp);
        p.sendMessage(MessageManager.plugin+MessageManager.exp_lost.replace("%amount%",""+(old-newexp)));

    }

    public void addCookExp(double amount)
    {
        double old=getCookExp();
        data.put("exp",(old+amount));

        p.sendMessage(MessageManager.plugin+MessageManager.exp_gain.replace("%amount%",""+amount));

        if (getCookLevel() < LegendaryCook.getInstance().getConfigManager().maxlevel) {
            for (int level = getCookLevel(); level < (LegendaryCook.getInstance().getConfigManager().maxlevel); level++) {
                if (old + amount >= LegendaryCook.getInstance().getConfigManager().exp.get(level)) {
                    addCookLevel(1);
                }
            }
        }

    }

    public int getCookLevel()
    {
        return this.data.containsKey("level") ? (int) this.data.get("level") : 0;
    }

    public double getCookExp()
    {
        return this.data.containsKey("exp") ? (double) this.data.get("exp") : 0.0;
    }

    public List<String> getUnlocks()
    {
        return this.data.containsKey("unlock") ? (List<String>) this.data.get("unlock") : new ArrayList<String>();
    }
    public HashMap<String,Integer> getCookAmounts()
    {
        return this.data.containsKey("success") ? (HashMap<String, Integer>) this.data.get("success") : new HashMap<>();
    }

    public void unlock(String cook)
    {
        List<String> list=getUnlocks();
        list.add(cook);
        data.put("unlock",list);
    }
    public int getCookAmount(String cook)
    {
        HashMap<String,Integer> map=getCookAmounts();
        return  map.containsKey(cook) ? map.get(cook) : 0;
    }

    public void addAmount(String cook,int amount)
    {
        int old=getCookAmount(cook);
        HashMap<String,Integer> map=getCookAmounts();
        map.put(cook,(old+amount));
        data.put("success",map);
    }

    public static void saveAllOnline()
    {
        for (Player p: Bukkit.getOnlinePlayers())
        {
            PlayerData.getPlayerData(p).save();
        }
    }
}
