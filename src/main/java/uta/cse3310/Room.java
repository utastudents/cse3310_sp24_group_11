package uta.cse3310;
import org.java_websocket.WebSocket;

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
        System.out.println("Player " + player.getPlayerName() + " added to room " + this.roomName);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        this.game.playerList.remove(player);
        System.out.println("Player " + player.getPlayerName() + " removed from room " + this.roomName);
    }

    public static Room addRoom(String roomName) {
        Room newRoom = new Room(roomName);
        rooms.add(newRoom);
        System.out.println("Room " + newRoom.getRoomName() + " created");
        return newRoom;
    }

    public static void removeRoom(String roomName) {
        rooms.removeIf(room -> room.getRoomName().equals(roomName));
        System.out.println("Room " + roomName + " removed");
    }

    public static ArrayList<String> fetchRooms() {
        ArrayList<String> roomNames = new ArrayList<>();
        for (Room room : rooms) {
            roomNames.add(room.getRoomName());
        }
        return roomNames;
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
