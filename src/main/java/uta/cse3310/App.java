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

    try {
      JsonObject jsonMessage = JsonParser.parseString(message).getAsJsonObject();
      if (jsonMessage.has("name")) {
        String username = jsonMessage.get("name").getAsString();
        boolean isUnique = Player.verifyUsername(username);
        if (isUnique) {
          lobby.players.add(new Player(username));
          JsonObject successMessage = new JsonObject();
          successMessage.addProperty("type", "success");
          successMessage.addProperty("msg", "Username is unique and added.");
          conn.send(successMessage.toString());
        } else {
          JsonObject errorMessage = new JsonObject();
          errorMessage.addProperty("type", "error");
          errorMessage.addProperty("msg", "Username is not unique. Please choose another.");
          conn.send(errorMessage.toString());
        }
      }
      if (jsonMessage.has("action") && jsonMessage.get("action").getAsString().equals("join2PlayerGame")) {
          Game G = conn.getAttachment();
          // G.startGame();
          
          // send the grid
          GsonBuilder builder = new GsonBuilder();
          Gson gson = builder.create();
          String jsonString = gson.toJson(G.grid);
          broadcast(jsonString);
          System.out.println("WordGrid sent to the client successfully");
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
