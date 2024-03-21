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
  
  public App(){

  }

  public void displayMessageBox(){

  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {

  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    
  }

  @Override
  public void onMessage(WebSocket conn, ByteBuffer message) {

  }

  @Override
  public void onError(WebSocket conn, Exception ex) {

  }
  
  @Override
  public void onStart(){

  }
}
