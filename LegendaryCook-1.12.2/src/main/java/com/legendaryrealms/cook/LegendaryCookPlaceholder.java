package com.legendaryrealms.cook;

import com.legendaryrealms.cook.Data.Cook.Cook;
import com.legendaryrealms.cook.Data.PlayerData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class LegendaryCookPlaceholder extends PlaceholderExpansion {

    public String getIdentifier() {
        return "LegendaryCook";
    }

    public String getAuthor() {
        return "Gyzer";
    }

    public String getVersion() {
        return "2.0.1";
    }

    public String onRequest(OfflinePlayer p, String params) {
       if (params.contains("experience_"))
       {

           PlayerData data=PlayerData.getPlayerData(p.getPlayer());           String cook=params.replace("experience_", "");
           if (Cook.getCook(cook)!=null)
           {
               return data.getRecipe_Experience(cook)+"";
           }
           return "0.0";
       }
       else if (params.contains("amount_"))
        {

            PlayerData data=PlayerData.getPlayerData(p.getPlayer());            String cook=params.replace("amount_", "");
            if (Cook.getCook(cook)!=null)
            {
                return data.getCookAmount(cook)+"";
            }
            return "0";
        }
       else if (params.equalsIgnoreCase("experience")){

           PlayerData data=PlayerData.getPlayerData(p.getPlayer());           return ""+data.getCookExp();
        }
       else if (params.equalsIgnoreCase("level")){
           PlayerData data=PlayerData.getPlayerData(p.getPlayer());
           return ""+data.getCookLevel();
       }
        return "null";
    }
}
