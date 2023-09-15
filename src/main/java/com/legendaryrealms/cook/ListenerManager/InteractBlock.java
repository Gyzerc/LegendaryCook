package com.legendaryrealms.cook.ListenerManager;

import com.legendaryrealms.cook.Data.Cook.CookPot;
import com.legendaryrealms.cook.Data.Database.Config;
import com.legendaryrealms.cook.Data.Pot.PotBlock;
import com.legendaryrealms.cook.Menu.Categorize.CategorizesMenu;
import com.legendaryrealms.cook.Menu.Main.MainMenu;
import com.legendaryrealms.cook.Menu.MaterialPut.MaterialPutMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.UUID;

public class InteractBlock implements Listener {
    @EventHandler
    public void onBlockIn(PlayerInteractEvent e)
    {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            if (Config.pot_block_value.equals(e.getClickedBlock().getType().name()))
            {
                    PotBlock potBlock=PotBlock.getPotBlock(e.getClickedBlock());
                    if (potBlock!=null)
                    {
                        e.setCancelled(true);
                        if (potBlock.getPlayer().getName().equals(e.getPlayer().getName()))
                        {
                            if (CookPot.getCookPot(e.getPlayer())!=null)
                            {
                                CookPot cookPot=CookPot.getCookPot(e.getPlayer());
                                if (cookPot.isInGame())
                                {
                                    MaterialPutMenu menu=new MaterialPutMenu(e.getPlayer());
                                    menu.open();
                                }
                                else {
                                    MainMenu menu=new MainMenu(e.getPlayer());
                                    menu.open();
                                }
                            }
                        }

                }
            }
        }
    }

    @EventHandler
    public void onpreview(InventoryClickEvent e){
        if (e.getInventory().getTitle().equalsIgnoreCase("预览")){
            e.setCancelled(true);
        }
    }
}
