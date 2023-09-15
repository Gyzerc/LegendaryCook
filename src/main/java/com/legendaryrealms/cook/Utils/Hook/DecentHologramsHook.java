package com.legendaryrealms.cook.Utils.Hook;

import com.legendaryrealms.cook.Data.Database.Config;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.utils.scheduler.S;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class DecentHologramsHook {


    public Hologram createHolo(List<String> texts, Location loc)
    {
        Location location=loc.clone().add(Config.hologram_offset_x,Config.hologram_offset_y,Config.hologram_offset_z);
        return DHAPI.createHologram(UUID.randomUUID().toString(), location, texts);
    }

    public void remove(Hologram holo)
    {
        holo.delete();
    }
}
