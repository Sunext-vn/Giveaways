package vn.sunext.adselvn.giveaways;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import vn.sunext.adselvn.giveaways.commands.MainCommand;
import vn.sunext.adselvn.giveaways.functions.BroadcastSystem;
import vn.sunext.adselvn.giveaways.functions.ChatSystem;
import vn.sunext.adselvn.giveaways.functions.GUISystem;
import vn.sunext.adselvn.giveaways.listeners.ChatEvent;
import vn.sunext.adselvn.giveaways.listeners.ClickEvent;
import vn.sunext.adselvn.giveaways.listeners.CloseEvent;
import vn.sunext.adselvn.giveaways.managers.FileManager;
import vn.sunext.adselvn.giveaways.managers.GiveawayManager;
import vn.sunext.adselvn.giveaways.managers.PathManager;
import vn.sunext.adselvn.giveaways.schedulers.Loop;

import java.util.Objects;

@Getter
public final class Giveaways extends JavaPlugin {

    private static Giveaways plugin;

    private PathManager pathManager;
    private FileManager fileManager;
    private GiveawayManager giveawayManager;

    private GUISystem guiSystem;
    private ChatSystem chatSystem;
    private BroadcastSystem broadcastSystem;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        pathManager = new PathManager();
        pathManager.register();

        fileManager = new FileManager();
        fileManager.register();

        giveawayManager = new GiveawayManager();
        giveawayManager.register();

        guiSystem = new GUISystem();
        chatSystem = new ChatSystem();
        broadcastSystem = new BroadcastSystem();

        new Loop().register();

        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        getServer().getPluginManager().registerEvents(new CloseEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);

        Objects.requireNonNull(getCommand("giveaways")).setExecutor(new MainCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Giveaways getInstance() {
        return plugin;
    }

}
