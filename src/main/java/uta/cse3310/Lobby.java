package uta.cse3310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.ArrayList;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.time.Instant;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Lobby{
    // //arraylist of players
    // private ArrayList<Player> playerList;
    // //arraylist of games
    // private ArrayList<Game> gameList;
    public ArrayList<Game> games;
    public ArrayList<Player> players;
    public ArrayList<String> chatMessages;
    public ArrayList<String> rooms;

    public Lobby(){
        games = new ArrayList<Game>();
        players = new ArrayList<Player>();
        chatMessages = new ArrayList<String>();;
        rooms = new ArrayList<String>();
    }

    public void addPlayer(String playerName){
        // Adds player and stores their information for game and leaderboard purposes
        
        //add a new player to the lobby
        Player newPlayer = new Player(playerName);
        players.add(newPlayer);
        System.out.println("Player " + newPlayer.playerName + " added to lobby");
    }

    public void removePlayer(String playerName) {
        players.removeIf(player -> playerName.equals(player.getPlayerName()));
    }

    public void addRoom(String playerName) {
        rooms.add(playerName);
        System.out.println("Room " + playerName + " added to lobby");
    }

    public void removeRoom(String playerName) {
        rooms.remove(playerName);
        System.out.println("Room " + playerName + " removed from lobby");
    }

    public ArrayList<String> fetchRooms() {
        return rooms;
    }

    public ArrayList<String> getPlayerNames() {
        ArrayList<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            playerNames.add(player.getPlayerName());
        }
        return playerNames;
    }

    // public void display(){
    //     // Displays and handles functionality of lobby
    //     System.out.println("Welcome to the lobby\n
    //                         Below is a list of games available to join\n");
    //     listGames();
    //     System.out.println("\nBelow is a list of players waiting to join a game\n");
    //     listPlayersWaiting();
    // }

    // public void listGames(){
    //     // List current games available to join
    //     if(gameList.size() == 0){
    //         System.out.println("No games available to join");
    //     }
    //     else{
    //         for(Game game : gameList){
    //             System.out.println("Game ID: %s", game.getGameID());
    //         }
    //     }
    // }

    // public void listPlayersWaiting(){
    //     // List players waiting to join a game
    //     if(playerList.size() == 0){
    //         System.out.println("No players waiting to join a game");
    //     }
    //     else{
    //         for(Player player : playerList){
    //             System.out.println("%s", player.getPlayerName());
    //         }
    //     }
    // }
}
