package vn.sunext.adselvn.giveaways.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import vn.sunext.adselvn.giveaways.Giveaways;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PathManager {

    private final Giveaways plugin = Giveaways.getInstance();

    public static String PREFIX = "";

    public static Boolean SET_TIME_MANUALLY = false;
    public static String MIN_TIME_MANUALLY = "";
    public static String MAX_TIME_MANUALLY = "";
    public static String DEFAULT_TIME_MANUALLY = "";

    public static String PLEASE_SET_TIME_MESSAGE = "";
    public static String WRONG_TIME_FORMAT = "";
    public static String TIME_INCORRECT_SPECIFICATIONS = "";
    public static String FAIL_SET_TIME_MESSAGE = "";
    public static String GIVEAWAYS_IS_RUNNING = "";
    public static String CREATE_GIVEAWAYS_COMPLETE_MESSAGE = "";
    public static String CREATE_GIVEAWAYS_FAIL_MESSAGE = "";
    public static String ANNOUNCE_GIVEAWAYS_MESSAGE = "";
    public static String COUNTDOWN_GIVEAWAYS_MESSAGE = "";
    public static String ENTERED_GIVEAWAYS_MESSAGE = "";
    public static String HAS_ENTERED_GIVEAWAYS_MESSAGE = "";
    public static String GIVEAWAYS_NOT_EXISTS_MESSAGE = "";
    public static String ANNOUNCE_WINNER_GIVEAWAYS_MESSAGE = "";
    public static String GIVE_WINNER_GIVEAWAYS_MESSAGE = "";
    public static String ANNOUNCE_END_GIVEAWAYS_MESSAGE = "";
    public static String ANNOUNCE_ENTERED_GIVEAWAYS_MESSAGE = "";
    public static String GIVE_OWN_GIVEAWAYS_MESSAGE = "";
    public static String CANNOT_JOIN_OWN_GIVEAWAYS_MESSAGE = "";
    public static String GIVEAWAYS_HAS_ENDED_MESSAGE = "";

    public static String CREATOR_GUI_NAME = "";
    public static String JOIN_GUI_NAME = "";

    public static String CREATOR_INSTRUCTIONS_MATERIAL = "";
    public static Boolean CREATOR_INSTRUCTIONS_GLOW = false;
    public static String CREATOR_INSTRUCTIONS_NAME = "";
    public static List<String> CREATOR_INSTRUCTIONS_LORE = new ArrayList<>();

    public static String CREATOR_BACKGROUND_MATERIAL = "";
    public static Boolean CREATOR_BACKGROUND_GLOW = false;
    public static String CREATOR_BACKGROUND_NAME = "";
    public static List<String> CREATOR_BACKGROUND_LORE = new ArrayList<>();

    public static String JOIN_INSTRUCTIONS_MATERIAL = "";
    public static Boolean JOIN_INSTRUCTIONS_GLOW = false;
    public static String JOIN_INSTRUCTIONS_NAME = "";
    public static List<String> JOIN_INSTRUCTIONS_LORE = new ArrayList<>();

    public static String JOIN_NEXT_PAGE_MATERIAL = "";
    public static Boolean JOIN_NEXT_PAGE_GLOW = false;
    public static String JOIN_NEXT_PAGE_NAME = "";
    public static List<String> JOIN_NEXT_PAGE_LORE = new ArrayList<>();

    public void register() {
        File config_file = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(config_file);

        CREATOR_INSTRUCTIONS_LORE.clear();
        CREATOR_BACKGROUND_LORE.clear();
        JOIN_INSTRUCTIONS_LORE.clear();
        JOIN_NEXT_PAGE_LORE.clear();

        PREFIX = config.getString("prefix");

        SET_TIME_MANUALLY = config.getBoolean("time.set-time-manually.enabled");
        MIN_TIME_MANUALLY = config.getString("time.set-time-manually.min");
        MAX_TIME_MANUALLY = config.getString("time.set-time-manually.max");
        DEFAULT_TIME_MANUALLY = config.getString("time.default-time");

        PLEASE_SET_TIME_MESSAGE = config.getString("messages.please-set-time");
        WRONG_TIME_FORMAT = config.getString("messages.wrong-time-format");
        TIME_INCORRECT_SPECIFICATIONS = config.getString("messages.time-incorrect-specifications");
        FAIL_SET_TIME_MESSAGE = config.getString("messages.fail-set-time");
        GIVEAWAYS_IS_RUNNING = config.getString("messages.giveaways-is-running");
        CREATE_GIVEAWAYS_COMPLETE_MESSAGE = config.getString("messages.create-giveaways-complete");
        CREATE_GIVEAWAYS_FAIL_MESSAGE = config.getString("messages.fail-to-create-giveaways");
        ANNOUNCE_GIVEAWAYS_MESSAGE = config.getString("messages.announce-giveaways");
        COUNTDOWN_GIVEAWAYS_MESSAGE = config.getString("messages.countdown-giveaways");
        ENTERED_GIVEAWAYS_MESSAGE = config.getString("messages.entered-giveaways");
        HAS_ENTERED_GIVEAWAYS_MESSAGE = config.getString("messages.has-entered-giveaways");
        GIVEAWAYS_NOT_EXISTS_MESSAGE = config.getString("messages.giveaways-not-exists");
        ANNOUNCE_WINNER_GIVEAWAYS_MESSAGE = config.getString("messages.announce-winner-giveaways");
        GIVE_WINNER_GIVEAWAYS_MESSAGE = config.getString("messages.give-winner-giveaways");
        ANNOUNCE_END_GIVEAWAYS_MESSAGE = config.getString("messages.announce-end-giveaways");
        ANNOUNCE_ENTERED_GIVEAWAYS_MESSAGE = config.getString("messages.announce-entered-giveaways");
        GIVE_OWN_GIVEAWAYS_MESSAGE = config.getString("messages.give-own-giveaways");
        CANNOT_JOIN_OWN_GIVEAWAYS_MESSAGE = config.getString("messages.cannot-join-own-giveaways");
        GIVEAWAYS_HAS_ENDED_MESSAGE = config.getString("messages.giveaways-has-ended");

        CREATOR_GUI_NAME = config.getString("gui.creator.name");
        JOIN_GUI_NAME = config.getString("gui.join.name");

        CREATOR_INSTRUCTIONS_MATERIAL = config.getString("gui.creator.items.instructions.material");
        CREATOR_INSTRUCTIONS_GLOW = config.getBoolean("gui.creator.items.instructions.glow");
        CREATOR_INSTRUCTIONS_NAME = config.getString("gui.creator.items.instructions.name");
        CREATOR_INSTRUCTIONS_LORE = config.getStringList("gui.creator.items.instructions.lore");

        CREATOR_BACKGROUND_MATERIAL = config.getString("gui.creator.items.background.material");
        CREATOR_BACKGROUND_GLOW = config.getBoolean("gui.creator.items.background.glow");
        CREATOR_BACKGROUND_NAME = config.getString("gui.creator.items.background.name");
        CREATOR_BACKGROUND_LORE = config.getStringList("gui.creator.items.background.lore");

        JOIN_INSTRUCTIONS_MATERIAL = config.getString("gui.join.items.instructions.material");
        JOIN_INSTRUCTIONS_GLOW = config.getBoolean("gui.join.items.instructions.glow");
        JOIN_INSTRUCTIONS_NAME = config.getString("gui.join.items.instructions.name");
        JOIN_INSTRUCTIONS_LORE = config.getStringList("gui.join.items.instructions.lore");

        JOIN_NEXT_PAGE_MATERIAL = config.getString("gui.join.items.next-page.material");
        JOIN_NEXT_PAGE_GLOW = config.getBoolean("gui.join.items.next-page.glow");
        JOIN_NEXT_PAGE_NAME = config.getString("gui.join.items.next-page.name");
        JOIN_NEXT_PAGE_LORE = config.getStringList("gui.join.items.next-page.lore");
    }

}
