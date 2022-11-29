package vn.sunext.adselvn.giveaways.schedulers;

import org.bukkit.Bukkit;
import vn.sunext.adselvn.giveaways.Giveaways;
import vn.sunext.adselvn.giveaways.constructors.PlaceholderDetail;
import vn.sunext.adselvn.giveaways.managers.GiveawayManager;
import vn.sunext.adselvn.giveaways.managers.PathManager;
import vn.sunext.adselvn.giveaways.utils.Others;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Loop {

    private final Giveaways plugin = Giveaways.getInstance();

    private final GiveawayManager giveawayManager = plugin.getGiveawayManager();

    private final List<Integer> countdownTimeList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 30, 60);

    public void register() {

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {

            giveawayManager.getGiveawayList().keySet().forEach(giveawayName -> {

                if (plugin.getChatSystem().isTypingPlayer(giveawayName))
                    return;

                giveawayManager.decreaseTimeLeft(giveawayName);

                countdownTimeList.forEach(integer -> {
                    if (integer.equals(giveawayManager.getGiveawaysDetail(giveawayName).getTimeLeft()))
                        plugin.getBroadcastSystem().countDownGiveaways(giveawayName, integer + "s");
                });

                if (giveawayManager.canRandomYet(giveawayName)) {
                    if (giveawayManager.isAnyOneJoined(giveawayName)) {
                        giveawayManager.endIfNoOneJoined(giveawayName);
                    } else {
                        if (giveawayManager.isRandomYet(giveawayName)) {

                            if (giveawayManager.getGiveawayList().get(giveawayName).getWonPlayer().equalsIgnoreCase(giveawayName)) {
                                if (Others.isOnline(giveawayName)) {
                                    giveawayManager.giveItemsToWinner(giveawayName);

                                    Others.sendMessage(Objects.requireNonNull(Bukkit.getPlayer(giveawayName)),
                                            PathManager.GIVE_OWN_GIVEAWAYS_MESSAGE, new PlaceholderDetail(giveawayName));
                                }

                            } else {
                                if (Others.isOnline(giveawayManager.getGiveawayList().get(giveawayName).getWonPlayer())) {
                                    giveawayManager.giveItemsToWinner(giveawayName);

                                    Others.sendMessage(Objects.requireNonNull(Bukkit.getPlayer(giveawayManager.getGiveawayList().get(giveawayName).getWonPlayer()))
                                            , PathManager.GIVE_WINNER_GIVEAWAYS_MESSAGE, new PlaceholderDetail(giveawayName, "", ""));
                                }
                            }

                        } else {
                            giveawayManager.setWonPlayer(giveawayName, giveawayManager.randomWonPlayer(giveawayName));

                            giveawayManager.announceWinner(giveawayName);
                        }
                    }
                }

                if (giveawayManager.getGiveawayList().get(giveawayName).getGiveItemsYet())
                    giveawayManager.addTempRemoveGiveaways(giveawayName);

            });

            giveawayManager.removeAllGiveaways();

            giveawayManager.saveGiveaways();

        }, 0L, 20L);

    }

}
