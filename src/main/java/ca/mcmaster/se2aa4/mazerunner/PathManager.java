package ca.mcmaster.se2aa4.mazerunner;

public class PathManager {
    private final  MazeExplorer explorer;

    public PathManager(MazeExplorer explorer) {
        this.explorer = explorer;
    }

    public Boolean VerifyPath(String instructions){ //make the explorer go through path and if position of explorer = the exit then the path is true. After verifcation reset explorer position to original
        Boolean isExit = false;
        if ((instructions.matches("^[FRL\s?0-9]+$"))) { //Check if path is valid
            String newpath = PathFormatter.ExpandPath(instructions);
            explorer.moveInstructions(newpath);
            isExit = explorer.hasReachedExit();
        } 
        return isExit;
    }
}