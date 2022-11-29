package vn.sunext.adselvn.giveaways.functions;

import com.cryptomorin.xseries.XMaterial;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vn.sunext.adselvn.giveaways.Giveaways;
import vn.sunext.adselvn.giveaways.constructors.GiveawayDetail;
import vn.sunext.adselvn.giveaways.constructors.PagesDetail;
import vn.sunext.adselvn.giveaways.containers.GUIContainer;
import vn.sunext.adselvn.giveaways.gui.GiveawayCreator;
import vn.sunext.adselvn.giveaways.gui.GiveawayJoin;
import vn.sunext.adselvn.giveaways.managers.PathManager;
import vn.sunext.adselvn.giveaways.utils.Others;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
public class GUISystem {

    private final Giveaways plugin = Giveaways.getInstance();
    private Map<Player, String> joinGUIGiveawaysNames = new HashMap<>();
    private Map<Player, Integer> joinGUIPages = new HashMap<>();

    public void openInventory(GUIContainer guiContainer, Player player) {
        guiContainer.openInventory(player);
    }

    public void onCloseInventory(InventoryCloseEvent event) {
        Inventory closedInventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        String playerName = player.getDisplayName();

        if (closedInventory.getHolder() instanceof GiveawayCreator) {
            List<ItemStack> items = new ArrayList<>();

            for (int i = 0; i <= 17; ++i) {
                items.add(closedInventory.getItem(i));
            }

            if (!isGiveawayItemsExists(player, items)) return;

            int timeLeft = 0;

            if (PathManager.SET_TIME_MANUALLY) {
                plugin.getChatSystem().addPlayer(player);
            } else {
                timeLeft = Others.replaceTime(PathManager.DEFAULT_TIME_MANUALLY);
            }

            GiveawayDetail giveawayDetail = new GiveawayDetail(items, null, timeLeft);

            plugin.getGiveawayManager().addTemporaryGiveaways(playerName, giveawayDetail);

            if (timeLeft != 0) {
                plugin.getGiveawayManager().createGiveaway(player, giveawayDetail);
            }
        }

        if (closedInventory.getHolder() instanceof GiveawayJoin) {
            removeJoinGUIPage(player);
        }
    }

    public void onClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        if (event.getView().getTopInventory().getHolder() instanceof GiveawayCreator)
            if (slot >= 18 && slot <= 26)
                event.setCancelled(true);

        if (event.getView().getTopInventory().getHolder() instanceof GiveawayJoin) {
            event.setCancelled(true);

            if (slot == 7)
                changePage(player);

            if (slot == 8)
                if (plugin.getGiveawayManager().canJoinGiveaways(joinGUIGiveawaysNames.get(player), player.getDisplayName()))
                    plugin.getGiveawayManager().addJoinPlayer(joinGUIGiveawaysNames.get(player), player.getDisplayName());
        }

    }

    public void setPageItems(Integer pageNumber, Player player) {
        PagesDetail pagesDetail = plugin.getGiveawayManager().getGiveawaysDetail(joinGUIGiveawaysNames.get(player)).getPagesDetail();

        for (int i = 0; i <= 6; ++i)
            player.getOpenInventory().getTopInventory().setItem(i, pagesDetail.getItemPages().get(pageNumber).get(i));
    }

    private void changePage(Player player) {
        int maxPage = plugin.getGiveawayManager().getGiveawaysDetail(joinGUIGiveawaysNames.get(player)).getPagesDetail().getItemPages().size();

        Integer currentPage = joinGUIPages.get(player);

        if (maxPage > currentPage)
            currentPage++;

        if (maxPage == currentPage)
            currentPage = 1;

        setPageItems(currentPage, player);
    }

    public void removeJoinGUIPage(Player player) {
        joinGUIPages.remove(player);
    }

    public void addJoinGUIPage(Player player) {
        joinGUIPages.put(player, 1);
    }

    public PagesDetail getPagesDetail(String giveawayName) {
        List<ItemStack> items = plugin.getGiveawayManager().getGiveawaysDetail(giveawayName).getItems();

        Map<Integer, List<ItemStack>> itemPages = new HashMap<>();

        for (int i = 0; i <= 17; i++) {
            if (i < 7) {
                setPages(itemPages, items, 1, i);
            } else if (i < 14) {
                setPages(itemPages, items, 2, i);
            } else {
                setPages(itemPages, items, 3, i);
            }
        }

        return new PagesDetail(itemPages);
    }

    private void setPages(Map<Integer, List<ItemStack>> itemPages, List<ItemStack> items, Integer pageNumber, Integer itemIndex) {
        if (!itemPages.containsKey(pageNumber))
            itemPages.put(pageNumber, new ArrayList<>());

        itemPages.get(pageNumber).add(items.get(itemIndex));
    }

    public void addPlayerJoinGiveaway(Player player, String giveawayName) {
        joinGUIGiveawaysNames.put(player, giveawayName);
    }

    private Boolean isGiveawayItemsExists(Player player, List<ItemStack> items) {
        if (isItemsListEmpty(items)) {
            Others.sendMessage(player, PathManager.CREATE_GIVEAWAYS_FAIL_MESSAGE, null);

            return false;
        }

        if (!PathManager.SET_TIME_MANUALLY)
            Others.sendMessage(player, PathManager.PLEASE_SET_TIME_MESSAGE, null);

        return true;
    }

    private Boolean isItemsListEmpty(List<ItemStack> items) {
        boolean empty = true;

        for (ItemStack item : items) {
            if (item != null) {
                empty = false;
                break;
            }
        }

        return empty;
    }

    public ItemStack getItemFromConfig(String material, Boolean glow, String name, List<String> lore) {
        Material finalMaterial = XMaterial.matchXMaterial(material).get().parseMaterial();
        String finalName = Others.color(name);
        List<String> finalLore = Others.color(lore);

        assert finalMaterial != null;
        ItemStack itemStack = new ItemStack(finalMaterial);
        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;
        itemMeta.setDisplayName(finalName);
        itemMeta.setLore(finalLore);

        if (glow) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
