package uta.cse3310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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

public class App extends WebSocketServer {

  private Statistics stats;
  private Vector<Game> ActiveGames = new Vector<Game>();
  private int gameID;
  private int connectionID;
  public ArrayList<Integer> playerIDs;
  public ArrayList<Player> players = new ArrayList<Player>();
  private Lobby lobby;
  public static Map<WebSocket, Player> connectionPlayerMap = new HashMap<>();

  public App(int port) {
    super(new InetSocketAddress(port));
    lobby = new Lobby();
  }

  public App(InetSocketAddress address) {
    super(address);
  }

  public App(int port, Draft_6455 draft) {
    super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
  }
  

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    // System.out.println("New connection at " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    // UserEvent E = new UserEvent(0, PlayerType.NOPLAYER, 0);  

    // //get the name passed by html


    // // search for a game needing a player
    // Game G = null;
    // for (Game i : ActiveGames) {
    //   if (i.currentTurn == uta.cse3310.PlayerType.Blue) {
    //     G = i;
    //     System.out.println("found a match");
    //   }
    // }

    // // No matches? Create a new Game.
    // if (G == null) {
    //   G = new Game();
    //   G.GameId = gameID;
    //   gameID++;
    //   // Add the first player
    //   G.currentTurn = uta.cse3310.PlayerType.Blue;
    //   ActiveGames.add(G);
    //   System.out.println("creating a new Game");
    // } else {
    //   // join an existing game
    //   System.out.println("not a new game");
    //   G.currentTurn = uta.cse3310.PlayerType.Red;
    //   G.startGame();
    // }
    // System.out.println("G.currentTurn is " + G.currentTurn);
    // // create an event to go to only the new player
    // E.setPlayerType(G.currentTurn);
    // E.gameIdx = G.GameId;
    // // allows the websocket to give us the Game when a message arrives
    // conn.setAttachment(G);
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    Player player = connectionPlayerMap.get(conn);
    if (player != null) {
        // Remove player from the global list, lobby, and the map
        connectionPlayerMap.remove(conn);
        Player.removePlayer(player.playerID);
        lobby.removePlayer(player.getPlayerName());
        Room.removeRoom(player.getPlayerName());

        System.out.println("Player " + player.getPlayerName() + " removed due to disconnection.");

        // Update all clients with the new player list
        ArrayList<String> playerNames = lobby.getPlayerNames();
        Gson gson = new Gson();
        JsonObject updatePlayers = new JsonObject();
        updatePlayers.addProperty("type", "fetchPlayerList");
        updatePlayers.add("players", gson.toJsonTree(playerNames));
        broadcast(updatePlayers.toString());

        // Update all clients with the new rooms list
        ArrayList<JsonObject> roomsInfo = Room.fetchRoomsInfo();
        JsonObject roomsMessage = new JsonObject();
        roomsMessage.addProperty("type", "roomList");
        roomsMessage.add("rooms", gson.toJsonTree(roomsInfo));
        broadcast(roomsMessage.toString());
    }
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    System.out.println("Message from frontend: " + message);

    try {
      JsonObject jsonMessage = JsonParser.parseString(message).getAsJsonObject();
      if (jsonMessage.has("name")) {
        String username = jsonMessage.get("name").getAsString();
        boolean isUnique = Player.verifyUsername(username);
        if (isUnique) {
          Player newPlayer = new Player(username);
          connectionPlayerMap.put(conn, newPlayer);
          lobby.players.add(newPlayer);
          JsonObject successMessage = new JsonObject();
          successMessage.addProperty("type", "UsernameSuccess");
          successMessage.addProperty("msg", "Username is unique and added.");
          conn.send(successMessage.toString());
        } else {
          JsonObject errorMessage = new JsonObject();
          errorMessage.addProperty("type", "UsernameError");
          errorMessage.addProperty("msg", "Username is not unique. Please choose another.");
          conn.send(errorMessage.toString());
        }
      }
      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("fetchPlayersList")) {
          ArrayList<String> playerNames = lobby.getPlayerNames();
          Gson gson = new Gson();
          JsonObject playerListMessage = new JsonObject();
          playerListMessage.addProperty("type", "fetchPlayerList");
          playerListMessage.add("players", gson.toJsonTree(playerNames));
          broadcast(playerListMessage.toString());
      }

      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("fetchRooms")) {
          ArrayList<JsonObject> roomsInfo = Room.fetchRoomsInfo();
          Gson gson = new Gson();
          JsonObject roomsMessage = new JsonObject();
          roomsMessage.addProperty("type", "roomList");
          roomsMessage.add("rooms", gson.toJsonTree(roomsInfo));
          broadcast(roomsMessage.toString());
      }

      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("addRoom")) {
          String playerName = jsonMessage.get("playerName").getAsString();
          Room newRoom = Room.addRoom(playerName);
          conn.setAttachment(newRoom.getGame());
          ArrayList<JsonObject> roomsInfo = Room.fetchRoomsInfo();
          Gson gson = new Gson();
          JsonObject roomsMessage = new JsonObject();
          roomsMessage.addProperty("type", "roomList");
          roomsMessage.add("rooms", gson.toJsonTree(roomsInfo));
          broadcast(roomsMessage.toString());
      }

      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("removeRoom")) {
        String playerName = jsonMessage.get("playerName").getAsString();
        Room.removeRoom(playerName);
        ArrayList<JsonObject> roomsInfo = Room.fetchRoomsInfo();
        Gson gson = new Gson();
        JsonObject roomsMessage = new JsonObject();
        roomsMessage.addProperty("type", "roomList");
        roomsMessage.add("rooms", gson.toJsonTree(roomsInfo));
        broadcast(roomsMessage.toString());
      }

      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("joinRoom")) {
        String playerName = jsonMessage.get("playerName").getAsString();
        String roomName = jsonMessage.get("roomName").getAsString();
        System.out.println("Player " + playerName + " is trying to join room " + roomName);
        Room room = Room.getRoomByName(roomName);
        Player player = connectionPlayerMap.get(conn);
        if (player == null) {
            player = new Player(playerName);
            connectionPlayerMap.put(conn, player);
        }
        room.addPlayer(player);

        // Fetch the current game state
        Game G = room.getGame();
        conn.setAttachment(G);
        if (G.gameStarted) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JsonObject gridMessage = new JsonObject();
            gridMessage.addProperty("type", "wordGrid");
            gridMessage.add("grid", gson.toJsonTree(G.grid));
            conn.send(gridMessage.toString()); // Send only to the new player

            // Send button colors to the new player
            PlayerType[] buttonColors = G.getButtonColorArray();
            JsonObject buttonColorsMessage = new JsonObject();
            buttonColorsMessage.addProperty("type", "buttonColors");
            buttonColorsMessage.add("colors", gson.toJsonTree(buttonColors));
            conn.send(buttonColorsMessage.toString()); // Send only to the new player
        }
        room.broadcastScores(); // Broadcast updated scores
      }

      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("fetchGrid")) {
          Game G = Room.getRoomByName(jsonMessage.get("roomName").getAsString()).getGame();
          conn.setAttachment(G);
          if (G.playerList.size() == 2 && !G.gameStarted) {
              G.startGame();
              G.gameStarted = true;
          }
          if (G.playerList.size() >= 2 && G.playerList.size() <=4 && G.gameStarted) {
              GsonBuilder builder = new GsonBuilder();
              Gson gson = builder.create();
              String jsonString = gson.toJson(G.grid);
              JsonObject gridMessage = new JsonObject();
              gridMessage.addProperty("type", "wordGrid");
              gridMessage.add("grid", gson.toJsonTree(G.grid));
              for (String word : G.grid.wordsBank) {
                  System.out.println(word);
              }
              Room room = Room.getRoomByPlayer(connectionPlayerMap.get(conn));
              room.broadcastToRoom(gridMessage.toString());
              System.out.println("Game grid sent to the client successfully");
          }
          // else if (G.playerList.size() >= 2 && !G.gameStarted) {
          //     G.startGame();
          //     G.gameStarted = true;
          // }
      }

      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("fetchChat")) {
          ArrayList<String> allMessages = lobby.getAllMessages();
          Gson gson = new Gson();
          JsonObject chatMessagesMessage = new JsonObject();
          chatMessagesMessage.addProperty("type", "chatMessages");
          chatMessagesMessage.add("messages", gson.toJsonTree(allMessages));
          Room room = Room.getRoomByPlayer(connectionPlayerMap.get(conn));
          room.broadcastToRoom(chatMessagesMessage.toString());
      }

      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("saveAllChats")) {
          String chatMessage = jsonMessage.get("message").getAsString();
          lobby.addChatMessage(chatMessage);
          ArrayList<String> allMessages = lobby.getAllMessages();
          Gson gson = new Gson();
          JsonObject chatMessagesMessage = new JsonObject();
          chatMessagesMessage.addProperty("type", "chatMessages");
          chatMessagesMessage.add("messages", gson.toJsonTree(allMessages));
          broadcast(chatMessagesMessage.toString());  
      }
      else if(jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("buttonClicked")){
          Game G = conn.getAttachment();
          int row = jsonMessage.get("row").getAsInt();
          int column = jsonMessage.get("column").getAsInt();
          int buttonNumber = row * 20 + column;
          UserEvent E = new UserEvent(G.GameId, connectionPlayerMap.get(conn).getType(), buttonNumber);
          G.update(E);
          // Send updated button colors
          PlayerType[] buttonColors = G.getButtonColorArray();
          Gson gson = new Gson();
          JsonObject buttonColorsMessage = new JsonObject();
          buttonColorsMessage.addProperty("type", "buttonColors");
          buttonColorsMessage.add("colors", gson.toJsonTree(buttonColors));
          Room room = Room.getRoomByPlayer(connectionPlayerMap.get(conn));
          room.broadcastToRoom(buttonColorsMessage.toString());
          room.broadcastScores(); // Broadcast updated scores
      }
      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("fetchButtonColors")) {
          Game G = conn.getAttachment();
          PlayerType[] buttonColors = G.getButtonColorArray();
          Gson gson = new Gson();
          JsonObject buttonColorsMessage = new JsonObject();
          buttonColorsMessage.addProperty("type", "buttonColors");
          buttonColorsMessage.add("colors", gson.toJsonTree(buttonColors));
          Room room = Room.getRoomByPlayer(connectionPlayerMap.get(conn));
          room.broadcastToRoom(buttonColorsMessage.toString());
      }
      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("fetchChat")) {
          ArrayList<String> allMessages = lobby.getAllMessages();
          Gson gson = new Gson();
          JsonObject chatMessagesMessage = new JsonObject();
          chatMessagesMessage.addProperty("type", "chatMessages");
          chatMessagesMessage.add("messages", gson.toJsonTree(allMessages));
          broadcast(chatMessagesMessage.toString());
      }
      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("fetchGridStatistics")) {
          Game G = conn.getAttachment();
          if (G.playerList.size() >= 2) {
            List<Object> gridStats = G.wordGrid.getGridStatistics(G.grid);
            Gson gson = new Gson();
            JsonObject gridStatsMessage = new JsonObject();
            gridStatsMessage.addProperty("type", "gridStatistics");
            gridStatsMessage.add("statistics", gson.toJsonTree(gridStats));
            Room room = Room.getRoomByPlayer(connectionPlayerMap.get(conn));
            room.broadcastToRoom(gridStatsMessage.toString());
          }
      }
      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("fetchWordBank")) {
        Game G = conn.getAttachment();
        if (G.playerList.size() >= 2) {
            List<String> wordBank = G.grid.wordsBank;
            ArrayList<Boolean> foundWords = G.getFoundWordsAsList();
            Gson gson = new Gson();
            JsonObject wordBankMessage = new JsonObject();
            wordBankMessage.addProperty("type", "wordBank");
            wordBankMessage.add("words", gson.toJsonTree(wordBank));
            wordBankMessage.add("foundWords", gson.toJsonTree(foundWords)); 
            Room room = Room.getRoomByPlayer(connectionPlayerMap.get(conn));
            room.broadcastToRoom(wordBankMessage.toString());
        }
      }
      else if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("leaveRoom")) {
        Room room = Room.getRoomByPlayer(connectionPlayerMap.get(conn));
        room.removePlayer(connectionPlayerMap.get(conn));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onMessage(WebSocket conn, ByteBuffer message) {
    System.out.println(conn + ": " + message);
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
      // Log the error for debugging purposes
      if(conn != null){
        System.err.println("An error occurred on connection " + conn.getRemoteSocketAddress() + ": " + ex.getMessage());
          conn.close(1006, "Unexpected error occurred");
      }
  }
   
  @Override
  public void onStart(){
    System.out.println("Server started!");
    setConnectionLostTimeout(0);
  }

  public static void main(String[] args) {

    String HttpPort = System.getenv("HTTP_PORT");
    int port = 9011;    //set http port to 9011 because we are group 11  (9000+11)
    if (HttpPort!=null) {
      port = Integer.valueOf(HttpPort);
    }

    // Set up the http server
    HttpServer H = new HttpServer(port, "./html");
    H.start();
    System.out.println("http Server started on port: " + port);

    // create and start the websocket server
    port = 9111;    //websocket port set to 9111 also because we are group 11 (9000+100+11)
    String WSPort = System.getenv("WEBSOCKET_PORT");
    if (WSPort!=null) {
      port = Integer.valueOf(WSPort);
    }

    App A = new App(port);
    A.setReuseAddr(true);
    A.start();
    System.out.println("websocket Server started on port: " + port);

  }
}
