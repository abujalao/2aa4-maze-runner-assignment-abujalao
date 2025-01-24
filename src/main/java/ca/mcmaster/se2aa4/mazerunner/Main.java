package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    private static String mazeFileLocation;
    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        CmdManager cmdManage = new CmdManager(args);
        cmdManage.addFlag("i",true,"Provided maze file path");
        mazeFileLocation = cmdManage.getFlag("i","");
        if (mazeFileLocation == null) {
            logger.error("Error getting the flag");
            return;
        }

        logger.info("**** Reading the maze from file " + mazeFileLocation);
        Maze maze = new Maze(mazeFileLocation);
        int[] entry2D = maze.getEntry().getPosition();
        Explorer explorer = new Explorer(new Position(entry2D[0],entry2D[1]),maze);
        //demo test movement
        explorer.moveInstruction('F');
        explorer.moveInstruction('F');
        explorer.moveInstruction('L');
        explorer.moveInstruction('L');
        explorer.moveInstruction('R');
        explorer.moveInstruction('R');
        explorer.moveInstruction('R');


        maze.printMaze();
        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}
