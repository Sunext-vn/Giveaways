package vn.sunext.adselvn.giveaways.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import vn.sunext.adselvn.giveaways.Giveaways;

public class ClickEvent implements Listener {

    private final Giveaways plugin = Giveaways.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        plugin.getGuiSystem().onClickInventory(event);
    }

}
