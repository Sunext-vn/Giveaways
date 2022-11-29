package vn.sunext.adselvn.giveaways.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import vn.sunext.adselvn.giveaways.Giveaways;

public class CloseEvent implements Listener {

    private final Giveaways plugin = Giveaways.getInstance();

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        plugin.getGuiSystem().onCloseInventory(event);
    }

}
