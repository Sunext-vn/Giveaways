package vn.sunext.adselvn.giveaways.functions;

import org.bukkit.Bukkit;
import vn.sunext.adselvn.giveaways.constructors.PlaceholderDetail;
import vn.sunext.adselvn.giveaways.managers.PathManager;
import vn.sunext.adselvn.giveaways.utils.Others;

public class BroadcastSystem {

    public void announceEndGiveaways(String whoCreateGiveaways) {
        broadcast(PathManager.ANNOUNCE_END_GIVEAWAYS_MESSAGE, new PlaceholderDetail(whoCreateGiveaways));
    }

    public void announceJoinPlayer(String whoCreateGiveaways, String joinPlayer) {
        broadcast(PathManager.ANNOUNCE_ENTERED_GIVEAWAYS_MESSAGE, new PlaceholderDetail(whoCreateGiveaways, joinPlayer));
    }

    public void announceWinnerGiveaways(String whoCreateGiveaways, String winner) {
        broadcast(PathManager.ANNOUNCE_WINNER_GIVEAWAYS_MESSAGE, new PlaceholderDetail(whoCreateGiveaways, winner, ""));
    }

    public void countDownGiveaways(String whoCreateGiveaways, String countDownTime) {
        broadcast(PathManager.COUNTDOWN_GIVEAWAYS_MESSAGE, new PlaceholderDetail(whoCreateGiveaways, "", countDownTime));
    }

    public void announceGiveaways(String whoCreateGiveaways) {
        broadcast(PathManager.ANNOUNCE_GIVEAWAYS_MESSAGE, new PlaceholderDetail(whoCreateGiveaways, "", ""));
    }

    private void broadcast(String message, PlaceholderDetail placeholderDetail) {
        Bukkit.getOnlinePlayers().forEach(player -> Others.sendMessage(player, message, placeholderDetail));
    }

}
