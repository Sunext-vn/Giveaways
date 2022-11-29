package vn.sunext.adselvn.giveaways.constructors;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PagesDetail {

    private Map<Integer, List<ItemStack>> itemPages;

    public PagesDetail(Map<Integer, List<ItemStack>> itemPages) {
        this.itemPages = itemPages;
    }

}
