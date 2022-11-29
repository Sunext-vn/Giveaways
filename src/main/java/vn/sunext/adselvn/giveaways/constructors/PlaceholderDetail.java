package vn.sunext.adselvn.giveaways.constructors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceholderDetail {

    private String playerName;
    private String countDown = "";
    private String winner = "";
    private String joinPlayer = "";

    public PlaceholderDetail(String playerName, String winner, String countDown) {
        this.playerName = playerName;
        this.countDown = countDown;
        this.winner = winner;
    }

    public PlaceholderDetail(String playerName, String joinPlayer) {
        this.playerName = playerName;
        this.joinPlayer = joinPlayer;
    }

    public PlaceholderDetail(String playerName) {
        this.playerName = playerName;
    }

}
