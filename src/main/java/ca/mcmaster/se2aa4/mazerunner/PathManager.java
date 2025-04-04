package ca.mcmaster.se2aa4.mazerunner;

public class PathManager {
    private MazeExplorer explorer;
    private static PathManager instance = null;
    private PathManager(MazeExplorer explorer) {
        this.explorer = explorer;
    }

    public static PathManager getInstance(MazeExplorer explorer) {
        if (instance == null) {
            instance = new PathManager(explorer);
        }
        instance.setExplorer(explorer);
        return instance;
    }

    private void setExplorer(MazeExplorer explorer) {
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