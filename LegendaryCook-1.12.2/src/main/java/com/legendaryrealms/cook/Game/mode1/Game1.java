package com.legendaryrealms.cook.Game.mode1;

import com.legendaryrealms.cook.Data.Cook.CookPot;
import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.Game.CookGame;
import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Manager.MessageManager;
import com.legendaryrealms.cook.Utils.MsgUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Game1 extends BukkitRunnable {

    private static HashMap<Player, Game1> cache=new HashMap<>();
    private Player p;
    private int duration;
    private int ingoreTick=0;

    private boolean canUpOrDown=true;
    private String cookId;
    private int addHotTick;
    private int maxHot;
    private int hot=1;
    private int shiftTick=0;
    private int currenTick=0;

    public Game1(Player p, int duration , String cookId, int addHotTick, int maxHot)
    {
        this.p=p;
        this.duration=duration;
        this.runTaskTimer(LegendaryCook.getInstance(),1,1);
        this.addHotTick=addHotTick;
        this.maxHot=maxHot;
        this.cookId=cookId;
        cache.put(p,this);
    }

    @Override
    public void run() {
        checkHasPot();
        if (ingoreTick==20) {
            ingoreTick = 0;
            duration--;
            doSomethingEverySecond();
            checkTimeOut();
        }
        if (canUpOrDown)
        {
            if (p.isSneaking())
            {
                currenTick=0;
                if (shiftTick == addHotTick-1)
                {
                    if (hot > 0)
                    {
                        hot--;
                        shiftTick=0;
                    }
                    else {
                        shiftTick++;
                    }
                }
                else {
                    shiftTick++;
                }
            }
            else {
                if (currenTick == addHotTick -1)
                {
                    shiftTick=0;
                    if (hot < maxHot)
                    {
                        hot++;
                        currenTick=0;
                    }
                    else {
                        currenTick++;
                    }
                }
                else {
                    currenTick++;
                }
            }

        }
        ingoreTick++;
    }

    public String getCookId()
    {
        return cookId;
    }

    public int getHot()
    {
        return hot;
    }
    public void setCanUpOrDown(boolean canUpOrDown)
    {
        this.canUpOrDown=canUpOrDown;
    }
    public String getHotString()
    {
        StringBuilder builder=new StringBuilder(LegendaryCook.getInstance().getConfigManager().hot_string.replace("%hot%",""));
        for (int a=1;a<=hot;a++)
        {
            builder.append(LegendaryCook.getInstance().getConfigManager().hot);
        }
        return builder.toString();
    }
    private void doSomethingEverySecond(){
        getPlayer().sendTitle(MsgUtils.msg("&a烹饪中.."),MsgUtils.msg("&f请在 &e"+getDuration()+"s &f内完成烹饪"));
        getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(getHotString()));
        Location loc = CookPot.getCookPot(getPlayer()).getLocation().clone();
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
                loc.getWorld().spawnParticle(Particle.valueOf(Config.effect_process), loc, 1);
                loc.subtract(x, y, z);
            }
        }
    }

    public void checkTimeOut()
    {
        if (duration<=0)
        {
            CookPot.getCookPot(p).getGame().CookFail();
            p.sendMessage(MessageManager.plugin+MessageManager.cook_timeout);
            p.sendTitle(MessageManager.cook_timeout_title.split(";")[0],MessageManager.cook_timeout_title.split(";")[1]);
            cancel();
            cache.remove(p);
            CookPot.getCookPot(p).updataHolo("无",0,0);
            return;
        }
    }

    public void checkHasPot()
    {
        if (CookPot.getCookPot(p)==null)
        {
            cancel();
            cache.remove(p);
            CookPot.getCookPot(p).getGame().backMaterial();
            return;
        }
    }

    public Player getPlayer()
    {
        return p;
    }

    public int getDuration()
    {
        return duration;
    }

    public static Game1 getGame(Player p)
    {
        return cache.get(p);
    }

    public void stop()
    {
        this.cancel();
        cache.remove(p);
    }


}
