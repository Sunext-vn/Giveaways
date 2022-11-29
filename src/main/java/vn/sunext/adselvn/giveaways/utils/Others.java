package vn.sunext.adselvn.giveaways.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import vn.sunext.adselvn.giveaways.constructors.PlaceholderDetail;
import vn.sunext.adselvn.giveaways.managers.PathManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Others {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F\\d]{6}");

    public static String color(String text) {
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String color = text.substring(matcher.start(), matcher.end());
            text = text.replace(color, ChatColor.of(color) + "");
            matcher = pattern.matcher(text);
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> color(List<String> text) {
        List<String> result = new ArrayList<>();

        for (String s : text) {
            Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {
                String color = s.substring(matcher.start(), matcher.end());
                s = s.replace(color, ChatColor.of(color) + "");
                matcher = pattern.matcher(s);
            }
            result.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        return result;
    }

    private static void dropItemIfInventoryFull(Player player, List<ItemStack> items, Integer itemIndex) {
        if (itemIndex < items.size() - 1) {
            for (int i = itemIndex; i < items.size(); ++i) {
                World world = player.getWorld();

                world.dropItemNaturally(player.getLocation(), items.get(i));
            }
        }
    }

    public static void giveItems(Player player, List<ItemStack> items) {
        int itemIndex = 0;

        for (int i = 9; i <= 44; ++i) {
            if (Objects.requireNonNull(player.getInventory().getItem(i)).getType() == Material.AIR) {
                if (i > items.size() - 1)
                    break;

                player.getInventory().setItem(i, items.get(itemIndex));
                itemIndex++;
            }
        }

        dropItemIfInventoryFull(player, items, itemIndex);

        player.updateInventory();
    }

    public static Boolean isOnline(String playerName) {
        return Bukkit.getPlayer(playerName) != null;
    }

    public static String replacePlaceholder(String text, PlaceholderDetail placeholderDetail) {
        String finalText = text.replace("{PREFIX}", PathManager.PREFIX)
                .replace("{MIN}", PathManager.MIN_TIME_MANUALLY)
                .replace("{MAX}", PathManager.MAX_TIME_MANUALLY);

        if (placeholderDetail != null)
            finalText = finalText.replace("{PLAYER_NAME}", placeholderDetail.getPlayerName())
                    .replace("{COUNTDOWN}", placeholderDetail.getCountDown())
                    .replace("{WINNER}", placeholderDetail.getWinner())
                    .replace("JOIN_PLAYER", placeholderDetail.getJoinPlayer());

        return color(finalText);
    }

    public static void sendMessage(Player player, String message, PlaceholderDetail placeholderDetail) {
        player.sendMessage(replacePlaceholder(message, placeholderDetail));
    }

    public static Integer replaceTime(String time) {
        if (time.endsWith("d")) {
            return 86400 * Integer.parseInt(time.replace("d", ""));
        }
        if (time.endsWith("h")) {
            return 3600 * Integer.parseInt(time.replace("h", ""));
        }
        if (time.endsWith("m")) {
            return 60 * Integer.parseInt(time.replace("m", ""));
        }
        if (time.endsWith("s")) {
            return Integer.parseInt(time.replace("s", ""));
        }

        return 0;
    }

}
