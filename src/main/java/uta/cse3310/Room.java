package uta.cse3310;

import org.java_websocket.WebSocket;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Map;

public class Room {
    private String roomName;
    private ArrayList<Player> players = new ArrayList<>();
    private Game game;
    private static ArrayList<Room> rooms = new ArrayList<>();

    public Room(String name) {
        this.roomName = name;
        this.players = new ArrayList<>();
        this.game = new Game();
    }

    public void addPlayer(Player player) {
        System.out.println("Adding player " + player.getPlayerName() + " to room " + this.roomName);
        this.players.add(player);
        this.game.playerList.add(player);
        PlayerType[] colors = {PlayerType.Blue, PlayerType.Red, PlayerType.Yellow, PlayerType.Green};
        player.setType(colors[players.size() - 1]);
        System.out.println("Player " + player.getPlayerName() + " added to room " + this.roomName + " with color " + player.getType());
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        this.game.playerList.remove(player);
        this.game.removePlayerFromScores(player);
        System.out.println("Player " + player.getPlayerName() + " removed from room " + this.roomName);
    }

    public static Room addRoom(String roomName) {
        Room newRoom = new Room(roomName);
        rooms.add(newRoom);
        System.out.println("Room " + newRoom.getRoomName() + " created");
        return newRoom;
    }

    public static void removeRoom(String roomName) {
        rooms.removeIf(room -> room.getRoomName().equals(roomName) && room.players.size() == 0);
        System.out.println("Room " + roomName + " removed");
    }

    public static ArrayList<JsonObject> fetchRoomsInfo() {
        ArrayList<JsonObject> roomInfoList = new ArrayList<>();
        for (Room room : rooms) {
            JsonObject roomInfo = new JsonObject();
            roomInfo.addProperty("name", room.getRoomName());
            roomInfo.addProperty("playerCount", room.players.size());
            roomInfo.addProperty("isFull", room.players.size() >= 4);
            roomInfoList.add(roomInfo);
        }
        return roomInfoList;
    }

    public void broadcastToRoom(String message) {
        for (Player player : players) {
            for (Map.Entry<WebSocket, Player> entry : App.connectionPlayerMap.entrySet()) {
                if (entry.getValue().equals(player)) {
                    WebSocket conn = entry.getKey();
                    if (conn != null) {
                        conn.send(message);
                    }
                }
            }
        }
    }

    public void broadcastScores() {
        ArrayList<JsonObject> scores = game.getPlayerScores();
        Gson gson = new Gson();
        JsonObject scoreMessage = new JsonObject();
        scoreMessage.addProperty("type", "updateScores");
        scoreMessage.add("scores", gson.toJsonTree(scores));
        broadcastToRoom(scoreMessage.toString());
    }

    public String getRoomName() {
        return roomName;
    }

    public static Room getRoomByName(String roomName) {
        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName)) {
                System.out.println("Room " + roomName + " found");
                return room;
            }
        }
        return null;
    }

    public static Room getRoomByPlayer(Player player) {
        for (Room room : rooms) {
            if (room.players.contains(player)) {
                System.out.println("Player " + player.getPlayerName() + " is in room " + room.getRoomName());
                return room;
            }
        }
        return null;
    }

    public Game getGame() {
        return game;
    }
}
