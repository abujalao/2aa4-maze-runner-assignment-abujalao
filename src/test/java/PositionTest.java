import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.Position;

class PositionTest {
    private Position pos;
    private Position pos2;
    
    @BeforeEach
    void setUp() {
        pos = new Position(0,0);
        pos2 = new Position(0,0);
    }

    @Test
    void setTest() {
        pos.setPosition(22,5);

        assertEquals(22, pos.getPosition()[0],"set x mismatch");
        assertEquals(5, pos.getPosition()[1],"set y mismatch");
    }

    @Test
    void equalityTest() {
        pos.setPosition(11,4);
        pos2.setPosition(11,4);

        assertEquals(true, pos.equals(pos2));
        assertEquals(true, pos.equals(pos));
        assertEquals(true, pos2.equals(pos));
    }

    @Test
    void inequalityTest() {
        pos.setPosition(15,4);
        pos2.setPosition(11,4);

        assertEquals(false, pos.equals(pos2));
        assertEquals(false, pos2.equals(pos));
    }

}
