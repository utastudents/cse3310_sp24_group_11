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

public class App extends WebSocketServer {

  private Statistics stats;
  private Vector<Game> ActiveGames;
  private int gameID;
  private int connectionID;
  public ArrayList<Integer> playerIDs;
  
  public App(int port) {
    super(new InetSocketAddress(port));
  }

  public App(InetSocketAddress address) {
    super(address);
  }

  public App(int port, Draft_6455 draft) {
    super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
  }
  
  public void displayMessageBox(){
    // Displays message chat box
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    // On new websocket connection, creates lobby if no lobby is made, shows game state, game tracking, creates new ServerEvent
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    // Handles closing of websocket connection
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    // Processes messages from players through the websocket connections including logs, run time, and game stats
    Gson gson = new Gson();
    message = "lol";
    String jsonString = gson.toJson(message);
    conn.send(jsonString);
  }

  @Override
  public void onMessage(WebSocket conn, ByteBuffer message) {
    // Handles binary messages
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    // Handle exceptions that occur for the websocket connection, error manangement and debugging
  }
   
  @Override
  public void onStart(){
    // Initial tasks when server starts up
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

    port = 9111;    //websocket port set to 9111 also because we are group 11   (9100+11)
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
