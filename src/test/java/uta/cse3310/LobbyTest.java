package uta.cse3310;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LobbyTest extends TestCase {

    public LobbyTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(LobbyTest.class);
    }

    public void testAddPlayer() {
        Lobby lobby = new Lobby();
        lobby.addPlayer("Alice");
        assertTrue(lobby.getPlayerNames().contains("Alice"));
    }

    public void testRemovePlayer() {
        Lobby lobby = new Lobby();
        lobby.addPlayer("Bob");
        lobby.removePlayer("Bob");
        assertFalse(lobby.getPlayerNames().contains("Bob"));
    }

    public void testAddChatMessage() {
        Lobby lobby = new Lobby();
        lobby.addChatMessage("Hello World");
        assertEquals("Hello World", lobby.getAllMessages().get(0));
    }

    public void testGetAllMessagesLimit() {
        Lobby lobby = new Lobby();
        for (int i = 0; i < 25; i++) {
            lobby.addChatMessage("Message " + i);
        }
        assertEquals(20, lobby.getAllMessages().size());
    }
}