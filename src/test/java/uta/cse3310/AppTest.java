package uta.cse3310;
import junit.framework.TestCase;
import java.net.InetSocketAddress;
import org.java_websocket.WebSocketImpl;

public class AppTest extends TestCase {

    public void testAppCreationWithPort() {
        int testPort = 9111;
        App testApp = new App(testPort);
        assertNotNull("App instance should be created", testApp);
        InetSocketAddress address = testApp.getAddress();
        assertEquals("Port should match the one provided", testPort, address.getPort());
    }
    
}
