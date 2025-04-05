import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.Maze;
import ca.mcmaster.se2aa4.mazerunner.Maze.CellType;
import ca.mcmaster.se2aa4.mazerunner.Position;
class MazeTest {
    private Maze maze;
    
    @BeforeEach
    void setUp() {
        maze = new Maze("./examples/straight.maz.txt"); //test Maze class with straight maze
    }

    @Test
    void testTypeAtPosEntry() {
        int[] entry = maze.getEntry().getPosition();

        assertEquals(CellType.Space, maze.getTypeAtPosition(new Position(entry[0],entry[1])));// Entry must be space and not wall
    }

    @Test
    void testTypeAtPosExit() {
        int[] entry = maze.getExit().getPosition();

        assertEquals(CellType.Space, maze.getTypeAtPosition(new Position(entry[0],entry[1])));// Exit must be space and not wall
    }

    @Test
    void testTypeAtPosWall() {
        int[] entry = maze.getEntry().getPosition();

        assertEquals(CellType.Wall, maze.getTypeAtPosition(new Position(entry[0]+1,entry[1])));// The row above entry point in the straight map must be a wall
    }
}
