package vn.sunext.adselvn.giveaways.functions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import vn.sunext.adselvn.giveaways.Giveaways;
import vn.sunext.adselvn.giveaways.constructors.GiveawayDetail;
import vn.sunext.adselvn.giveaways.managers.PathManager;
import vn.sunext.adselvn.giveaways.utils.Others;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatSystem {

    private final Giveaways plugin = Giveaways.getInstance();

    private final List<Player> typingPlayers = new ArrayList<>();

    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String message = event.getMessage();

        if (!isTypingPlayer(player))
            return;

        if (isCancel(message)) {
            cancelSetTime(player);

            return;
        }

        if (isWrongFormat(message)) {
            Others.sendMessage(player, PathManager.WRONG_TIME_FORMAT, null);

            return;
        }

        Integer resultTime = Others.replaceTime(message);

        Integer minTime = Others.replaceTime(PathManager.MIN_TIME_MANUALLY);
        Integer maxTime = Others.replaceTime(PathManager.MAX_TIME_MANUALLY);

        if (resultTime < minTime || resultTime > maxTime) {
            Others.sendMessage(player, PathManager.TIME_INCORRECT_SPECIFICATIONS, null);

            return;
        }

        GiveawayDetail giveawayDetail = plugin.getGiveawayManager().getGiveawaysDetail(playerName);
        giveawayDetail.setTimeLeft(resultTime);

        plugin.getGiveawayManager().createGiveaway(player, giveawayDetail);

        removePlayer(player);
    }

    private void cancelSetTime(Player player) {
        String playerName = player.getDisplayName();

        Others.sendMessage(player, PathManager.FAIL_SET_TIME_MESSAGE, null);

        Others.giveItems(player, plugin.getGiveawayManager().getGiveawaysDetail(playerName).getItems());

        removePlayer(player);
        plugin.getGiveawayManager().removeTemporaryGiveaway(playerName);
    }

    private Boolean isCancel(String text) {
        return text.contains("cancel");
    }

    private Boolean isWrongFormat(String text) {
        return !text.endsWith("d") ||
                !text.endsWith("h") ||
                !text.endsWith("m") ||
                !text.endsWith("s");
    }

    public Boolean isTypingPlayer(String playerName) {
        return typingPlayers.contains(Bukkit.getPlayer(playerName));
    }

    private Boolean isTypingPlayer(Player player) {
        return typingPlayers.contains(player);
    }

    public void addPlayer(Player player) {
        typingPlayers.add(player);
    }

    public void removePlayer(Player player) {
        typingPlayers.remove(player);
    }

}
