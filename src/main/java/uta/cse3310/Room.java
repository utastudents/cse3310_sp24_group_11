package uta.cse3310;

import java.util.ArrayList;

public class Room {
    private String name;
    private ArrayList<Player> players;
    private Game game;
    private static ArrayList<Room> rooms = new ArrayList<>();

    public Room(String name) {
        this.name = name;
        this.players = new ArrayList<>();
        this.game = new Game();
    }

    public static void addPlayer(Player player) {
        this.players.add(player);
        Game.addPlayer(player);
    }

    public static Room addRoom(String roomName) {
        Room newRoom = new Room(roomName);
        rooms.add(newRoom);
        return newRoom;
    }

    public static void removeRoom(String roomName) {
        rooms.removeIf(room -> room.getName().equals(roomName));
    }

    public static ArrayList<String> fetchRooms() {
        ArrayList<String> roomNames = new ArrayList<>();
        for (Room room : rooms) {
            roomNames.add(room.getName());
        }
        return roomNames;
    }

    public String getName() {
        return name;
    }

    public Game getGame() {
        return game;
    }
}

