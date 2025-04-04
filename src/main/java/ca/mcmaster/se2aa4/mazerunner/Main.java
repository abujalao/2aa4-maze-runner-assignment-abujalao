package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    private static String mazeFileLocation;
    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        //initialize cmdManager and add required flags
        CmdManager cmdManage = new CmdManager(args);    
        cmdManage.addFlag("i",true,"Provided maze file path");
        cmdManage.addFlag("p",true,"Provided path to verify");

        mazeFileLocation = cmdManage.getFlag("i",""); 
        if (mazeFileLocation == null) { //check if maze path file exists
            return; 
        } else if (mazeFileLocation.equals("")) { //check if returned default value which means that file wasn't provided
            return;
        }  
        logger.info("**** Reading the maze from file " + mazeFileLocation);
        
        Maze maze = new Maze(mazeFileLocation);
        MazeStructure mazeStruct = maze;

        int[] entry2D = maze.getEntry().getPosition();
        Explorer explorer = new Explorer(new Position(entry2D[0],entry2D[1]),mazeStruct);
        MazeExplorer exploreInterface = explorer;
        PathManager pathManage = PathManager.getInstance(exploreInterface);
        //get -p movement to verify if given
        String manualInstructions = cmdManage.getFlag("p",""); //instructions given by user to verify
        if (manualInstructions==null || manualInstructions.equals("")) {
            logger.info("**** Computing path");
            MazeSolver solver = new RightHandSolver(); // Use the RightHandSolver strategy
            String path = solver.solveMaze(mazeStruct,exploreInterface);
            System.out.println(path);
        } else {
            Boolean res = pathManage.VerifyPath(manualInstructions);
            String resultOut = res? "correct path" : "incorrect path";
            System.out.println(resultOut);
        }
        logger.info("** End of MazeRunner");
    }
}
