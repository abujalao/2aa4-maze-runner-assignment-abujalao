import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.Main;

class MainTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(out));
    }

    @Test
    void testStraight() {
        String[] in = {"-i", "./examples/straight.maz.txt"};
        Main.main(in); // Run main

        assertEquals("4F\n", out.toString());
    }

    @Test
    void testDirect() {
        String[] in = {"-i", "./examples/direct.maz.txt"};
        Main.main(in); // Run main

        assertEquals("F R 2F L 3F R F L F R F L 2F\n", out.toString());
    }

    @Test
    void testHuge() {
        String[] in = {"-i", "./examples/tiny.maz.txt"};
        Main.main(in); // Run main

        assertEquals("5F 2L 2F R 2F R 2F 2L 2F R 2F R 3F\n", out.toString());
    }

    @Test
    void testPathStraight() {
        String[] in = {"-i", "./examples/straight.maz.txt","-p","FFF"};
        Main.main(in); // Run main

        assertEquals("incorrect path\n", out.toString());
    }

    @Test
    void testPathStraight2() {
        String[] in = {"-i", "./examples/straight.maz.txt","-p","FFFF"};
        Main.main(in); // Run main

        assertEquals("correct path\n", out.toString());
    }


}
