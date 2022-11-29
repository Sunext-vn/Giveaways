package vn.sunext.adselvn.giveaways.managers;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import vn.sunext.adselvn.giveaways.Giveaways;
import vn.sunext.adselvn.giveaways.constructors.GiveawayDetail;
import vn.sunext.adselvn.giveaways.utils.ItemStackSerializer;

import java.io.File;
import java.util.List;

public class FileManager {

    private final Giveaways plugin = Giveaways.getInstance();

    public void register() {
        createDataFolder();
    }

    public void removeGiveaways(String playerName) {
        File dataPlayer = getFileDataPlayer(playerName);
        dataPlayer.delete();

        plugin.getGiveawayManager().removeTemporaryGiveaway(playerName);
    }

    public void saveGiveaways(String playerName, GiveawayDetail giveawayDetail) {
        YamlConfiguration dataPlayer = getDataPlayer(playerName);

        dataPlayer.set("items", ItemStackSerializer.serializeItemStack(giveawayDetail.getItems()));
        dataPlayer.set("joinPLayers", giveawayDetail.getJoinPlayers());
        dataPlayer.set("wonPlayer", giveawayDetail.getWonPlayer());
        dataPlayer.set("giveItemsYet", giveawayDetail.getGiveItemsYet());
        dataPlayer.set("timeLeft", giveawayDetail.getTimeLeft());

        saveDataPlayer(playerName, dataPlayer);
    }

    public void loadData() {
        File data_folder = getDataFolder();

        File[] files = data_folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            String playerName = file.getName().replace(".yml", "");
            YamlConfiguration dataPlayer = getDataPlayer(playerName);

            List<ItemStack> items = ItemStackSerializer.deserializeItemStack(dataPlayer.getStringList("items"));
            List<String> joinPLayers = dataPlayer.getStringList("joinPLayers");
            String wonPlayer = dataPlayer.getString("wonPlayer");
            Boolean giveItemsYet = dataPlayer.getBoolean("giveItemsYet");
            Integer timeLeft = dataPlayer.getInt("timeLeft");

            GiveawayDetail giveawayDetail = new GiveawayDetail(items, null, timeLeft);
            giveawayDetail.setWonPlayer(wonPlayer);
            giveawayDetail.setJoinPlayers(joinPLayers);
            giveawayDetail.setGiveItemsYet(giveItemsYet);

            plugin.getGiveawayManager().addTemporaryGiveaways(playerName, giveawayDetail);
        }
    }

    @SneakyThrows
    private void saveDataPlayer(String playerName, YamlConfiguration yamlConfiguration) {
        File data_player = getFileDataPlayer(playerName);

        yamlConfiguration.save(data_player);
    }

    @SneakyThrows
    private YamlConfiguration getDataPlayer(String playerName) {
        File data_player = getFileDataPlayer(playerName);
        if (!data_player.exists()) data_player.createNewFile();

        return YamlConfiguration.loadConfiguration(data_player);
    }

    private File getFileDataPlayer(String playerName) {
        return new File(plugin.getDataFolder(), "/Data/" + playerName + ".yml");
    }

    private File getDataFolder() {
        return new File(plugin.getDataFolder(), "/Data/");
    }

    private void createDataFolder() {
        File data_folder = getDataFolder();
        if (!data_folder.exists()) data_folder.mkdirs();
    }

}
