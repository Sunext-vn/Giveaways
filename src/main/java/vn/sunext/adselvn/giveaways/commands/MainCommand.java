package vn.sunext.adselvn.giveaways.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import vn.sunext.adselvn.giveaways.Giveaways;
import vn.sunext.adselvn.giveaways.gui.GiveawayCreator;
import vn.sunext.adselvn.giveaways.gui.GiveawayJoin;
import vn.sunext.adselvn.giveaways.managers.PathManager;
import vn.sunext.adselvn.giveaways.utils.Others;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements TabExecutor {

    private final Giveaways plugin = Giveaways.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        switch (args.length) {
            case 0: {
                if (plugin.getGiveawayManager().isGiveawaysExists(player.getDisplayName())) {
                    plugin.getGiveawayManager().giveawayIsRunning(player);

                    break;
                }

                plugin.getGuiSystem().openInventory(new GiveawayCreator(), player);
                break;
            }
            case 1: {
                String giveawayName = args[0];
                if (!plugin.getGiveawayManager().isGiveawaysExists(giveawayName)) {

                    Others.sendMessage(player, PathManager.GIVEAWAYS_NOT_EXISTS_MESSAGE, null);

                    break;
                }

                plugin.getGuiSystem().openInventory(new GiveawayJoin(player, giveawayName), player);
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = new ArrayList<>();

        List<String> giveawaysNames = new ArrayList<>(plugin.getGiveawayManager().getGiveawayList().keySet());

        if (args.length == 1) {
            giveawaysNames.forEach(s -> {
                if (s.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(s);
            });
        }

        if (!result.isEmpty())
            return result;

        return null;
    }

}
