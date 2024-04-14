package uta.cse3310;

public class UserEvent {
    int gameIdx;
    PlayerType playerType;
    int button;
    
    public UserEvent(int gameIdx, PlayerType playerType, int button) {
        this.gameIdx = gameIdx;
        this.playerType = playerType;
        this.button = button;
    }
    public int getGameIdx(){
        return gameIdx;
    }
    public PlayerType getPlayerType(){
        return playerType;
    }
    public int getButton(){
        return button;
    }
}
