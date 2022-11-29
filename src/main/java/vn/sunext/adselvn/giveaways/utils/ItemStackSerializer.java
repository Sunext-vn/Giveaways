package vn.sunext.adselvn.giveaways.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public final class ItemStackSerializer {

    public static ItemStack deserializeItemStack(String data) {
        if (data == null || data.isEmpty())
            return new ItemStack(Material.AIR);

        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return config.getItemStack("item", null);
    }

    public static List<ItemStack> deserializeItemStack(List<String> data) {
        List<org.bukkit.inventory.ItemStack> l = new ArrayList<>(data.size());
        for (String s : data)
            l.add(deserializeItemStack(s));
        return l;
    }

    public static String serializeItemStack(ItemStack item) {
        if (item == null || item.getType() == Material.AIR)
            return "";

        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("item", item);

        return yaml.saveToString();
    }

    public static List<String> serializeItemStack(List<org.bukkit.inventory.ItemStack> items) {
        List<String> l = new ArrayList<>(items.size());
        for (org.bukkit.inventory.ItemStack s : items)
            l.add(serializeItemStack(s));
        return l;
    }

}
