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
        maze.printMaze();
        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}
