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
    }

    public void removePlayer(String playerName) {
        players.removeIf(player -> playerName.equals(player.getPlayerName()));
        removeRoom(playerName);
    }

    public void addRoom(String playerName) {
        rooms.add(playerName);
    }

    public void removeRoom(String playerName) {
        rooms.removeIf(room -> room.equals(playerName));
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

    public void addChatMessage(String message) {
        chatMessages.add(message);
    }

    public ArrayList<String> getAllMessages() {
        // Only the last 20 messages are returned
        final int MAX_MESSAGES = 20;
        if (chatMessages.size() <= MAX_MESSAGES) {
            return chatMessages;
        } else {
            return new ArrayList<>(chatMessages.subList(chatMessages.size() - MAX_MESSAGES, chatMessages.size()));
        }
    }
}
