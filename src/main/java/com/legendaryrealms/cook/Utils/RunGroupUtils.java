package com.legendaryrealms.cook.Utils;

import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Manager.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.swing.plaf.PanelUI;
import java.util.List;

public class RunGroupUtils {

    public void Run(Player p,List<String> cmds)
    {
        for (String cmd:cmds)
        {
            String tag=getTag(cmd);
            if (tag!=null)
            {
                String deal=cmd.replace("["+tag+"]","").replace("%player%",p.getName());
                switch (tag) {
                    case "message":
                        p.sendMessage(MsgUtils.msg(deal));
                        break;
                    case "title":
                        sendTitle(p,MsgUtils.msg(deal));
                        break;
                    case "player_command":
                        p.performCommand(deal);
                        break;
                    case "op_command":
                        performCommandAsOp(p,deal);
                        break;
                    case "console_command":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),deal);
                        break;
                    case "sound":
                        playSound(p,deal);
                        break;
                 }
            }
        }

    }

    public String getTag(String arg)
    {
        char[] chars=arg.toCharArray();
        String args="";
        boolean begin=false;
        for (char c:chars)
        {
            if (c=='[')
            {
                begin=true;
            }
            else if (c==']')
            {
                return args;
            }
            else {
                if (begin)
                {
                    args+=c;
                }
            }
        }
        return null;
    }

    public void sendTitle(Player p,String title)
    {
        String[] titles=title.split(";");
        if (titles.length <= 2)
        {
            p.sendTitle(titles[0],titles[1]);
            return;
        }
        int in=titles[2] != null ? Integer.parseInt(titles[2]) : 20;
        int stay=titles[3] != null ? Integer.parseInt(titles[3]) : 20;
        int out=titles[4] != null ? Integer.parseInt(titles[4]) : 20;
        p.sendTitle(titles[0],titles[1],in,stay,out);
        return;
    }


    public void performCommandAsOp(Player p,String cmd)
    {
        if (p.isOp())
        {
            p.performCommand(cmd);
        }
        else {
            p.setOp(true);
            p.performCommand(cmd);
            p.setOp(false);
        }
    }

    public void playSound(Player p,String deal)
    {
        String sound=deal.split(";")[0];
        try {
            int pitch=Integer.parseInt(deal.split(";")[1]);
            int volume=Integer.parseInt(deal.split(";")[2]);
            p.playSound(p.getLocation(),Sound.valueOf(sound),pitch,volume);
        } catch (Exception ex)
        {
            Bukkit.getConsoleSender().sendMessage(MessageManager.plugin+MsgUtils.msg("&4出现错误："+deal));
            p.playSound(p.getLocation(),Sound.valueOf(sound), 1F,1F);
            return;
        }
    }
}
