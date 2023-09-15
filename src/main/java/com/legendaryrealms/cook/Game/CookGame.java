package com.legendaryrealms.cook.Game;

import com.legendaryrealms.cook.API.Events.LegendaryCookSuccessEvent;
import com.legendaryrealms.cook.Data.Cook.Cook;
import com.legendaryrealms.cook.Data.Cook.CookPot;
import com.legendaryrealms.cook.Data.Cook.CookRecipe;
import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.Data.PlayerData;
import com.legendaryrealms.cook.Game.mode1.Game1;
import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Manager.MessageManager;
import com.legendaryrealms.cook.Utils.RunGroupUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CookGame {

    private int amount=0;
    private HashMap<Integer, ItemStack> Material=new HashMap<>();
    private HashMap<Integer, Integer> Hot=new HashMap<>();
    private String id;
    private Game1 game1;
    private Player p;
    public CookGame(Player p,String cookId)
    {
        this.p=p;
        this.id=cookId;
        if (Cook.getCook(cookId)!=null)
        {
            Cook cook=Cook.getCook(cookId);
            game1=new Game1(p,cook.getTime(),cookId,cook.getAddHotTick(),cook.getHot());
            for (String msg:MessageManager.cook_message)
            {
                p.sendMessage(msg.replace("%name%",Cook.getCook(cookId).getName()));
            }
            return;
        }
        p.sendMessage(MessageManager.plugin+"配方不存在！请联系管理员...");
    }

    public void addMaterial(ItemStack i)
    {
        p.playSound(p.getLocation(), Sound.valueOf(Config.sound_cook_put),1,1);
        Material.put(amount,i);
        Hot.put(amount, game1.getHot());
        amount++;
        int cookRecipe=CookRecipe.getCookRecipe(id).getAmounts();
        CookPot.getCookPot(p).updataHolo(Cook.getCook(id).getName(),amount,cookRecipe);
        check(i);
    }

    public int getHot()
    {
       return game1.getHot();
    }

    public void cancel()
    {
        if (!Material.isEmpty())
        {
            Material.forEach((integer, itemStack) ->
            {
                p.getInventory().addItem(itemStack);
            });
        }
        CookPot.getCookPot(p).updataHolo("无",0,0);
        game1.stop();
    }
    public void backMaterial()
    {
        if (!Material.isEmpty())
        {
            Material.forEach((integer, itemStack) ->
            {
                p.getInventory().addItem(itemStack);
            });
        }
    }

    public void check(ItemStack i)
    {

            String id= game1.getCookId();
            Cook cook=Cook.getCook(id);
            CookRecipe recipe=CookRecipe.getCookRecipe(id);
            int less=recipe.getAmounts()-amount;
            if (amount == recipe.getAmounts())
            {
                game1.stop();
                CookPot.getCookPot(p).updataHolo("无",0,0);
                for (int in=0;in<amount;in++)
                {
                    ItemStack tar=recipe.getItem(in).clone();
                    tar.setAmount(recipe.getNeed(in));
                    if (!tar.equals(Material.get(in)))
                    {
                        CookFail();
                        return;
                    }
                }
                CookSuccess();
                return;
            }
            String name=i.getType().name();
            if (i.getItemMeta().hasDisplayName())
            {
                name=i.getItemMeta().getDisplayName();
            }
            p.sendMessage(MessageManager.plugin+MessageManager.cook_put.replace("%item%",name).replace("%amount%",""+i.getAmount()).replace("%less%",""+less).replace("%hot%",""+game1.getHot()));
            return;

    }

    public Game1 getGame()
    {
        return Game1.getGame(p);
    }
    public void CookFail()
    {
        Cook cook=Cook.getCook(id);
        for (ItemStack i:getRemoveBack())
        {
            p.getInventory().addItem(i);
        }
        LegendaryCook.getInstance().getItemUtils().giveItemArg(p,id,cook.getFail_item());
        PlayerData data=PlayerData.getPlayerData(p) != null ? PlayerData.getPlayerData(p) : new PlayerData(p);
        if (cook.getFail_add_cook_experience() > 0) {
            data.addCookExp(cook.getFail_add_cook_experience());
        }
        if (cook.getFail_add_reicpes_experience() > 0){
            data.addRecipe_Experience(cook.getId(), cook.getFail_add_reicpes_experience());

        }
        new RunGroupUtils().Run(p,cook.getFail_run());
        if (CookPot.getCookPot(p) != null) {
            Location loc = CookPot.getCookPot(p).getLocation().clone();
            loc.add(0.5D, 0.5D, 0.5D);
            for (double i = 0.0D; i < 180.0D; i += 30.0D) {
                double radians = Math.toRadians(i);
                double radius = Math.sin(radians);
                double y = Math.cos(radians);
                double j;
                for (j = 0.0D; j < 360.0D; j += 30.0D) {
                    double radiansCircle = Math.toRadians(j);
                    double x = Math.cos(radiansCircle) * radius;
                    double z = Math.sin(radiansCircle) * radius;
                    loc.add(x, y, z);
                    loc.getWorld().spawnParticle(Particle.valueOf(Config.effect_fail),loc, 1);
                    loc.subtract(x, y, z);
                }
            }
        }
    }

    public List<ItemStack> getRemoveBack()
    {
        List<ItemStack> list=new ArrayList<>();
        Material.forEach((integer, itemStack) -> {
            list.add(itemStack);
        });
        if (!list.isEmpty()) {
            for (int i = Cook.getCook(id).getRemoveBack(); i > 0; i--) {
                int roll = (new Random()).nextInt(list.size());
                if (list.size() > roll) {
                    list.remove(roll);
                }
            }
        }
        return list;
    }

    public void CookSuccess()
    {
        Cook cook=Cook.getCook(id);
        LegendaryCook.getInstance().getItemUtils().giveItemArg(p,id,cook.getResult_item());
        PlayerData data=PlayerData.getPlayerData(p) != null ? PlayerData.getPlayerData(p) : new PlayerData(p);
        if (cook.getResult_add_cook_experience() > 0) {
            data.addCookExp(cook.getResult_add_cook_experience());
        }
        if (cook.getResult_add_reicpes_experience() > 0){
            data.addRecipe_Experience(cook.getId(), cook.getResult_add_reicpes_experience());
        }
        new RunGroupUtils().Run(p,cook.getResult_run());
        Bukkit.getPluginManager().callEvent(new LegendaryCookSuccessEvent(p, cook.getId()));
        if (CookPot.getCookPot(p) != null) {
            Location loc=CookPot.getCookPot(p).getLocation().clone();
            loc.add(0.5D, 0.5D, 0.5D);
            for (double d = 0.0D; d < 180.0D; d += 30.0D) {
                double radians = Math.toRadians(d);
                double radius = Math.sin(radians);
                double y = Math.cos(radians);
                double j;
                for (j = 0.0D; j < 360.0D; j += 30.0D) {
                    double radiansCircle = Math.toRadians(j);
                    double x = Math.cos(radiansCircle) * radius;
                    double z = Math.sin(radiansCircle) * radius;
                    loc.add(x, y, z);
                    loc.getWorld().spawnParticle(Particle.valueOf(Config.effect_success), loc, 1);
                    loc.subtract(x, y, z);
                }
            }
        }
    }
}
