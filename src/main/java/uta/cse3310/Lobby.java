import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Lobby{
    //arraylist of players
    private ArrayList<Player> playerList;
    //arraylist of games
    private ArrayList<Game> gameList;

    public Lobby(arraylist<Game> gameList){
        playerList = new ArrayList<Player>();
        //get game list from database
        this.gameList = gameList;
    }

    public void display(){
        // Displays and handles functionality of lobby
        System.out.println("Welcome to the lobby\n
                            Below is a list of games available to join\n");
        listGames();
        System.out.println("\nBelow is a list of players waiting to join a game\n");
        listPlayersWaiting();
    }

    public void listGames(){
        // List current games available to join
        if(gameList.size() == 0){
            System.out.println("No games available to join");
        }
        else{
            for(Game game : gameList){
                System.out.println("Game ID: %s", game.getGameID());
            }
        }
    }

    public void addPlayer(ArrayList<Integer> playerIDs){
        // Adds player and stores their information for game and leaderboard purposes
        
        //add a new player to the lobby
        Player newPlayer = new Player(playerIDs);
        playerList.add(newPlayer);
        System.out.println("Player %s added to lobby", newPlayer.getPlayerName());
    }

    public void listPlayersWaiting(){
        // List players waiting to join a game
        if(playerList.size() == 0){
            System.out.println("No players waiting to join a game");
        }
        else{
            for(Player player : playerList){
                System.out.println("%s", player.getPlayerName());
            }
        }
    }
}
