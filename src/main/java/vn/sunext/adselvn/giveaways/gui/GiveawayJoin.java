package vn.sunext.adselvn.giveaways.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import vn.sunext.adselvn.giveaways.Giveaways;
import vn.sunext.adselvn.giveaways.constructors.PlaceholderDetail;
import vn.sunext.adselvn.giveaways.containers.GUIContainer;
import vn.sunext.adselvn.giveaways.managers.PathManager;
import vn.sunext.adselvn.giveaways.utils.Others;

public class GiveawayJoin implements InventoryHolder, GUIContainer {

    private final Giveaways plugin = Giveaways.getInstance();

    Inventory inventory;

    public GiveawayJoin(Player player, String whoCreateGiveaways) {
        inventory = Bukkit.createInventory(this, 9, Others.replacePlaceholder(PathManager.JOIN_GUI_NAME, new PlaceholderDetail(whoCreateGiveaways, "", "")));

        plugin.getGuiSystem().addPlayerJoinGiveaway(player, whoCreateGiveaways);
        plugin.getGuiSystem().addJoinGUIPage(player);
        plugin.getGuiSystem().setPageItems(1, player);

        loadInventoryItem();
    }

    private void loadInventoryItem() {
        ItemStack instructions = plugin.getGuiSystem()
                .getItemFromConfig(PathManager.JOIN_INSTRUCTIONS_MATERIAL,
                        PathManager.JOIN_INSTRUCTIONS_GLOW,
                        PathManager.JOIN_INSTRUCTIONS_NAME,
                        PathManager.JOIN_INSTRUCTIONS_LORE);

        ItemStack next_page = plugin.getGuiSystem()
                .getItemFromConfig(PathManager.JOIN_NEXT_PAGE_MATERIAL,
                        PathManager.JOIN_NEXT_PAGE_GLOW,
                        PathManager.JOIN_NEXT_PAGE_NAME,
                        PathManager.JOIN_NEXT_PAGE_LORE);

        inventory.setItem(7, next_page);

        inventory.setItem(8, instructions);
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
