package com.legendaryrealms.cook.Data.Pot;

import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.LegendaryCook;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

public class PotHolo {

    private Location loc;
    public PotHolo(Location loc,List<String> texts)
    {
        this.loc=loc;
        if (texts!=null)
        {
            setHolo(texts);
        }
    }

    private Hologram hd=null;
    private eu.decentsoftware.holograms.api.holograms.Hologram d_hd=null;
    private CMIHologram c_hd=null;
    public void setHolo(List<String> texts)
    {
        String plugin=Config.hologram_plugin;
        switch (plugin) {
            case "holographicdisplays":
                if (LegendaryCook.getInstance().getHooks().getHolographicDisplaysHook()!=null) {
                    hd = LegendaryCook.getInstance().getHooks().getHolographicDisplaysHook().createHolo(loc, texts);
                }
                break;
            case "decentholograms":
                if (LegendaryCook.getInstance().getHooks().getDecentHologramsHook()!=null)
                {
                    d_hd= LegendaryCook.getInstance().getHooks().getDecentHologramsHook().createHolo(texts,loc);
                    break;
                }
            case "cmi":
                if (LegendaryCook.getInstance().getHooks().getCmiHook()!=null)
                {
                    c_hd= LegendaryCook.getInstance().getHooks().getCmiHook().createHolo(texts,loc);
                    break;
                }

        }

    }

    public void removeHolo()
    {
        String plugin=Config.hologram_plugin;
        switch (plugin) {
            case "holographicdisplays":
                if (LegendaryCook.getInstance().getHooks().getHolographicDisplaysHook() != null) {
                    hd.delete();
                    hd=null;
                }
                break;
            case "decentholograms":
                if (LegendaryCook.getInstance().getHooks().getDecentHologramsHook()!=null)
                {
                    d_hd.delete();
                    d_hd=null;
                }
                break;
            case "cmi":
                if (LegendaryCook.getInstance().getHooks().getCmiHook()!=null)
                {
                    c_hd.remove();
                    c_hd=null;
                }
                break;
        }
    }

    public void update(List<String> texts)
    {
        removeHolo();
        setHolo(texts);
    }




}
