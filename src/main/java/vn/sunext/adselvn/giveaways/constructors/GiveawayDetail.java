package vn.sunext.adselvn.giveaways.constructors;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GiveawayDetail {

    private List<ItemStack> items;
    private PagesDetail pagesDetail;
    private List<String> joinPlayers = new ArrayList<>();
    private String wonPlayer;
    private Boolean giveItemsYet = false;
    private Integer timeLeft;

    public GiveawayDetail(List<ItemStack> items, PagesDetail pagesDetail, Integer timeLeft) {
        this.items = items;
        this.pagesDetail = pagesDetail;
        this.timeLeft = timeLeft;
    }

}
