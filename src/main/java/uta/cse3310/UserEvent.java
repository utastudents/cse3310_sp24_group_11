package uta.cse3310;
// User events are sent from the webpage to the server

public class UserEvent {
    int gameIdx;
    PlayerType playerType;
    int button;
    
    //int GameId; // the game ID on the server
    //PlayerType PlayerIdx; // either an XPLAYER or an OPLAYER
    //int Button; // button number from 0 to 8

    // UserEvent() {

    // }

    UserEvent(int gameIdx, PlayerType PlayerType, int button) {
        // GameId = _GameId;
        // PlayerIdx = _PlayerIdx;
        // Button = _Button;
    }
}
