import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTypeTest {

    @Test
    public void testGetColor() {
        assertEquals("Blue", PlayerType.BLUE.getColor());
        assertEquals("Red", PlayerType.RED.getColor());
        assertEquals("Yellow", PlayerType.YELLOW.getColor());
        assertEquals("Green", PlayerType.GREEN.getColor());
        assertEquals("NoPlayer", PlayerType.NO_PLAYER.getColor());
    }
}