package vn.sunext.adselvn.giveaways.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import vn.sunext.adselvn.giveaways.Giveaways;
import vn.sunext.adselvn.giveaways.containers.GUIContainer;
import vn.sunext.adselvn.giveaways.managers.PathManager;
import vn.sunext.adselvn.giveaways.utils.Others;

public class GiveawayCreator implements InventoryHolder, GUIContainer {

    private final Giveaways plugin = Giveaways.getInstance();

    Inventory inventory;

    public GiveawayCreator() {
        inventory = Bukkit.createInventory(this, 27, Others.color(PathManager.CREATOR_GUI_NAME));

        loadInventoryItem();
    }

    private void loadInventoryItem() {
        ItemStack instructions = plugin.getGuiSystem()
                .getItemFromConfig(PathManager.CREATOR_INSTRUCTIONS_MATERIAL,
                        PathManager.CREATOR_INSTRUCTIONS_GLOW,
                        PathManager.CREATOR_INSTRUCTIONS_NAME,
                        PathManager.CREATOR_INSTRUCTIONS_LORE);

        ItemStack background = plugin.getGuiSystem()
                .getItemFromConfig(PathManager.CREATOR_BACKGROUND_MATERIAL,
                        PathManager.CREATOR_BACKGROUND_GLOW,
                        PathManager.CREATOR_BACKGROUND_NAME,
                        PathManager.CREATOR_BACKGROUND_LORE);

        for (int i = 18; i < 27; ++i)
            inventory.setItem(i, background);

        inventory.setItem(22, instructions);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void openInventory(Player player) {
        player.openInventory(inventory);
    }
}
