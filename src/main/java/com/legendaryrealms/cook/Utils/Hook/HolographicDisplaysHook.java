package com.legendaryrealms.cook.Utils.Hook;

import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.LegendaryCook;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.Position;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Location;

import java.util.List;

public class HolographicDisplaysHook {

    public Hologram createHolo(Location loc, List<String> text)
    {
        Hologram ho= HolographicDisplaysAPI.get(LegendaryCook.getInstance()).createHologram(loc);
        for (String msg:text)
        {
            ho.getLines().appendText(msg);
        }
        Position po=ho.getPosition().add(Config.hologram_offset_x,Config.hologram_offset_y,Config.hologram_offset_z);
        ho.setPosition(po);
        return ho;
    }

    public void removeHolo(Hologram holo)
    {
        holo.delete();
    }

}
