package vn.sunext.adselvn.giveaways.managers;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import vn.sunext.adselvn.giveaways.Giveaways;
import vn.sunext.adselvn.giveaways.constructors.GiveawayDetail;
import vn.sunext.adselvn.giveaways.constructors.PlaceholderDetail;
import vn.sunext.adselvn.giveaways.utils.Others;

import java.util.*;

@Getter
@Setter
public class GiveawayManager {

    private final Giveaways plugin = Giveaways.getInstance();

    private Map<String, GiveawayDetail> giveawayList = new HashMap<>();
    private List<String> removeGiveaways = new ArrayList<>();

    public void register() {
        plugin.getFileManager().loadData();
    }

    public void endIfNoOneJoined(String giveawayName) {
        plugin.getBroadcastSystem().announceEndGiveaways(giveawayName);
        getGiveawaysDetail(giveawayName).setWonPlayer(giveawayName);
    }

    public Boolean isAnyOneJoined(String giveawayName) {
        return !getGiveawaysDetail(giveawayName).getJoinPlayers().isEmpty();
    }

    public void giveawayIsRunning(Player player) {
        Others.sendMessage(player, PathManager.GIVEAWAYS_IS_RUNNING, null);
    }

    public void saveGiveaways() {
        giveawayList.forEach((s, giveawayDetail) -> plugin.getFileManager().saveGiveaways(s, giveawayDetail));
    }

    public void announceWinner(String giveawayName) {
        plugin.getBroadcastSystem().announceWinnerGiveaways(giveawayName, getGiveawaysDetail(giveawayName).getWonPlayer());
    }

    public void giveItemsToWinner(String giveawayName) {
        Player winner = Bukkit.getPlayer(giveawayList.get(giveawayName).getWonPlayer());
        List<ItemStack> items = giveawayList.get(giveawayName).getItems();

        Others.giveItems(winner, items);

        giveawayList.get(giveawayName).setGiveItemsYet(true);
    }

    public void removeAllGiveaways() {
        if (!removeGiveaways.isEmpty())
            removeGiveaways.forEach(s -> plugin.getFileManager().removeGiveaways(s));

        removeGiveaways.clear();
    }

    public void addTempRemoveGiveaways(String giveawayName) {
        removeGiveaways.add(giveawayName);
    }

    public void decreaseTimeLeft(String giveawayName) {
        GiveawayDetail giveawayDetail = giveawayList.get(giveawayName);

        if (giveawayDetail.getTimeLeft() == 0)
            return;

        giveawayDetail.setTimeLeft(giveawayDetail.getTimeLeft() - 1);
    }

    public Boolean isRandomYet(String giveawayName) {
        return !(giveawayList.get(giveawayName).getWonPlayer() == null);
    }

    public Boolean canRandomYet(String giveawayName) {
        return giveawayList.get(giveawayName).getTimeLeft() == 0;
    }

    public void setWonPlayer(String giveawayName, String wonPlayer) {
        giveawayList.get(giveawayName).setWonPlayer(wonPlayer);
    }

    public String randomWonPlayer(String giveawayName) {
        List<String> joinPlayers = giveawayList.get(giveawayName).getJoinPlayers();

        Random random = new Random();
        int finalIndex = random.nextInt(joinPlayers.size());

        return joinPlayers.get(finalIndex);
    }

    public Boolean canJoinGiveaways(String giveawayName, String joinPlayer) {
        Player player = Bukkit.getPlayer(joinPlayer);

        if (giveawayName.equalsIgnoreCase(joinPlayer)) {

            if (player != null)
                Others.sendMessage(player, PathManager.CANNOT_JOIN_OWN_GIVEAWAYS_MESSAGE, null);

            return false;
        }

        if (isRandomYet(giveawayName)) {
            if (player != null)
                Others.sendMessage(player, PathManager.GIVEAWAYS_HAS_ENDED_MESSAGE, null);

            return false;
        }

        if (isJoined(giveawayName, joinPlayer)) {

            if (player != null)
                Others.sendMessage(player, PathManager.HAS_ENTERED_GIVEAWAYS_MESSAGE, new PlaceholderDetail(giveawayName, "", ""));

            return false;
        }

        return true;
    }

    public void addJoinPlayer(String giveawayName, String joinPlayer) {
        Player player = Bukkit.getPlayer(joinPlayer);

        plugin.getBroadcastSystem().announceJoinPlayer(giveawayName, joinPlayer);

        if (player != null)
            Others.sendMessage(player, PathManager.ENTERED_GIVEAWAYS_MESSAGE, new PlaceholderDetail(giveawayName));

        GiveawayDetail giveawayDetail = giveawayList.get(giveawayName);

        giveawayDetail.getJoinPlayers().add(joinPlayer);
    }

    public Boolean isJoined(String giveawayName, String joinPlayer) {
        GiveawayDetail giveawayDetail = giveawayList.get(giveawayName);

        return giveawayDetail.getJoinPlayers().contains(joinPlayer);
    }

    public Boolean isGiveawaysExists(String playerName) {
        return giveawayList.containsKey(playerName);
    }

    public void createGiveaway(Player whoCreateGiveaways, GiveawayDetail giveawayDetail) {
        String giveawayName = whoCreateGiveaways.getDisplayName();

        plugin.getFileManager().saveGiveaways(giveawayName, giveawayDetail);

        getGiveawaysDetail(giveawayName).setPagesDetail(plugin.getGuiSystem().getPagesDetail(giveawayName));

        Others.sendMessage(whoCreateGiveaways, PathManager.CREATE_GIVEAWAYS_COMPLETE_MESSAGE, null);

        plugin.getBroadcastSystem().announceGiveaways(giveawayName);
    }

    public GiveawayDetail getGiveawaysDetail(String playerName) {
        return giveawayList.get(playerName);
    }

    public void removeTemporaryGiveaway(String playerName) {
        giveawayList.remove(playerName);
    }

    public void addTemporaryGiveaways(String playerName, GiveawayDetail giveawayDetail) {
        giveawayList.put(playerName, giveawayDetail);
    }

}
