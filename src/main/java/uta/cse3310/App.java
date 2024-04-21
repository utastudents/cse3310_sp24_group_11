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

public class App extends WebSocketServer {

  private Statistics stats;
  private Vector<Game> ActiveGames = new Vector<Game>();
  private int gameID;
  private int connectionID;
  public ArrayList<Integer> playerIDs;
  public ArrayList<Player> players = new ArrayList<Player>();
  private Lobby lobby;
  
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
  
  public void displayMessageBox(){
    messageBox mb = new messageBox();
    mb.displayMessage("Your message here");
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    System.out.println("New connection at " + conn.getRemoteSocketAddress().getAddress().getHostAddress());

    UserEvent E = new UserEvent(0, PlayerType.NOPLAYER, 0);  

    //get the name passed by html
    

    // search for a game needing a player
    Game G = null;
    for (Game i : ActiveGames) {
      if (i.currentTurn == uta.cse3310.PlayerType.Blue) {
        G = i;
        System.out.println("found a match");
      }
    }

    // No matches? Create a new Game.
    if (G == null) {
      G = new Game();
      G.GameId = gameID;
      gameID++;
      // Add the first player
      G.currentTurn = uta.cse3310.PlayerType.Blue;
      ActiveGames.add(G);
      System.out.println("creating a new Game");
    } else {
      // join an existing game
      System.out.println("not a new game");
      G.currentTurn = uta.cse3310.PlayerType.Red;
      G.startGame();
    }
    System.out.println("G.currentTurn is " + G.currentTurn);
    // create an event to go to only the new player
    E.setPlayerType(G.currentTurn);
    E.gameIdx = G.GameId;
    // allows the websocket to give us the Game when a message arrives
    conn.setAttachment(G);

    // Gson gson = new Gson();
    // // Note only send to the single connection
    // conn.send(gson.toJson(E));
    // System.out.println(gson.toJson(E));

    // The state of the game has changed, so lets send it to everyone
    // String jsonString;
    // jsonString = gson.toJson(G);

    // System.out.println(jsonString);
    // broadcast(jsonString);
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    // Retrieve the game tied to the websocket connection
    Game G = conn.getAttachment();
    if (G != null) {
        // Perform necessary actions like notifying other players, updating game status, etc.
        System.out.println("Game ID " + G.GameId + " associated with this connection is now being handled due to disconnection.");
    }
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    System.out.println("Received message from the frontend: " + message);

    // start a game
    Game G = conn.getAttachment();
    G.startGame();
    
    // send the grid
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    String jsonString = gson.toJson(G.grid);
    broadcast(jsonString);
    // System.out.println("jsonString is: " + jsonString); 
    System.out.println("WordGrid sent to the client successfully");

    // JsonObject jsonMessage = JsonParser.parseString(message).getAsJsonObject();
    // if (jsonMessage.has("name")) {
    //     String username = jsonMessage.get("name").getAsString();
    //     lobby.players.add(new Player(username));
    // }
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
