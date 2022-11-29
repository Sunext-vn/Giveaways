package vn.sunext.adselvn.giveaways.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import vn.sunext.adselvn.giveaways.Giveaways;

public class ChatEvent implements Listener {

    private final Giveaways plugin = Giveaways.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        plugin.getChatSystem().onChat(event);
    }

}
