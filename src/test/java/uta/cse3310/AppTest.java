package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.net.InetSocketAddress;
import org.java_websocket.WebSocketImpl;

public class AppTest extends TestCase {

    /**
     * Test creating an App instance with a specific port.
     */
    public void testAppCreationWithPort() {
        int testPort = 9111;
        App testApp = new App(testPort);
        assertNotNull("App instance should be created", testApp);
        InetSocketAddress address = testApp.getAddress();
        assertEquals("Port should match the one provided", testPort, address.getPort());
    }

    /**
     * Test setting reuseAddr flag.
     */
    public void testSetReuseAddr() {
        App testApp = new App(9111);
        assertFalse("reuseAddr should initially be false", WebSocketImpl.getReuseAddr());
        testApp.setReuseAddr(true);
        assertTrue("reuseAddr should be set to true", WebSocketImpl.getReuseAddr());
    }
}
