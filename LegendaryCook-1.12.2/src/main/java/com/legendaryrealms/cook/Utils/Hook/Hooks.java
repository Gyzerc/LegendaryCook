package com.legendaryrealms.cook.Utils.Hook;

import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.legendaryrealms.cook.Manager.MessageManager;
import com.legendaryrealms.cook.Utils.MsgUtils;
import org.bukkit.Bukkit;

public class Hooks {

    private MythicMobHoook mythicMobHoook=null;

    private PlaceholderAPIHook placeholderAPIHook;
    private HolographicDisplaysHook holographicDisplaysHook=null;
    private DecentHologramsHook decentHologramsHook=null;
    private CMIHook cmiHook=null;
    private NeigeItemsHook neigeItemsHook=null;
    public Hooks()
    {
        if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs"))
        {
            Bukkit.getConsoleSender().sendMessage("[LegendaryCook] 已检测到 "+ MsgUtils.msg("&BMythicMobs"));
            mythicMobHoook=new MythicMobHoook();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays"))
        {
            Bukkit.getConsoleSender().sendMessage("[LegendaryCook] 已检测到 "+ MsgUtils.msg("&bHolographicDisplays"));
            holographicDisplaysHook=new HolographicDisplaysHook();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("DecentHolograms"))
        {
            Bukkit.getConsoleSender().sendMessage("[LegendaryCook] 已检测到 "+ MsgUtils.msg("&bDecentHolograms"));
            decentHologramsHook=new DecentHologramsHook();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("CMI"))
        {
            Bukkit.getConsoleSender().sendMessage("[LegendaryCook] 已检测到 "+ MsgUtils.msg("&bCMI"));
            cmiHook=new CMIHook();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("NeigeItems"))
        {
            Bukkit.getConsoleSender().sendMessage("[LegendaryCook] 已检测到 "+ MsgUtils.msg("&bNeigeItems"));
            neigeItemsHook=new NeigeItemsHook();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
        {
            Bukkit.getConsoleSender().sendMessage("[LegendaryCook] 已检测到 "+ MsgUtils.msg("&bPlaceholderAPI"));
            placeholderAPIHook=new PlaceholderAPIHook();
        }
    }


    public MythicMobHoook getMythicMobHook(){
        return mythicMobHoook;
    }

    public HolographicDisplaysHook getHolographicDisplaysHook() {return holographicDisplaysHook;}
    public DecentHologramsHook getDecentHologramsHook(){return decentHologramsHook;}
    public CMIHook getCmiHook(){return cmiHook;}

    public PlaceholderAPIHook getPlaceholderAPIHook() {
        return placeholderAPIHook;
    }

    public NeigeItemsHook getNeigeItemsHook(){return neigeItemsHook;}
}
