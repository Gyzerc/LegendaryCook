package com.legendaryrealms.cook.API.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LegendaryCookSuccessEvent extends Event {
    private static boolean cancel;

    private static Player p;

    private static String id;

    private static final HandlerList handlers = new HandlerList();

    public LegendaryCookSuccessEvent(Player p, String paperId) {
        this.p = p;
        this.id = paperId;
    }

    public HandlerList getHandlers() {
        return handlers;
    }


    public Player getPlayer() {
        return p;
    }

    public String getPaperId() {
        return id;
    }

}
