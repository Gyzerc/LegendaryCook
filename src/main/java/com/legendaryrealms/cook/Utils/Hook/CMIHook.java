package com.legendaryrealms.cook.Utils.Hook;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.legendaryrealms.cook.Data.Database.Config;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class CMIHook {

    public CMIHologram createHolo(List<String> texts, Location loc)
    {
        Location location=loc.clone().add(Config.hologram_offset_x,Config.hologram_offset_y,Config.hologram_offset_z);
        CMIHologram holo = new CMIHologram(UUID.randomUUID().toString(), location);
        holo.setLines(texts);
        CMI.getInstance().getHologramManager().addHologram(holo);
        holo.update();
        return holo;
    }


    public void remove(CMIHologram holo)
    {
        holo.remove();
    }
}
