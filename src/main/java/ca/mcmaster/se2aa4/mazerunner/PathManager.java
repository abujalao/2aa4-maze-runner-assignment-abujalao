package ca.mcmaster.se2aa4.mazerunner;

interface Pathfinder {
    String findPath();
}

public class PathManager implements Pathfinder {
    private final MazeExplorer explorer;
    private String path="";
    
    public PathManager(MazeExplorer explorer) {
        this.explorer = explorer;
    }

    private String formatRunLength(char letter, int count) {
        return (count > 1) ? count + Character.toString(letter) : Character.toString(letter);
    }
    private String FactorizePath(String instructions){
        String factorized = "";
        char lastLetter=instructions.charAt(0);
        int count = 1;
        for (int i = 1; i < instructions.length(); i++) {
            char currentChar = instructions.charAt(i);
            if (currentChar==lastLetter) {
                count++;
            } else {
                factorized += formatRunLength(lastLetter,count);
                count = 1;
                lastLetter = currentChar;
            }
        }
        factorized += formatRunLength(lastLetter,count); //count what is left from last for loop iteration and add to factorized path
        return factorized;
    }

    private boolean pathInsturction(char instruction){
        boolean res = explorer.moveInstruction(instruction);
        if (res) {
            path+=instruction;
        }
        return res;
    }

    @Override
    public String findPath(){
        MazeStructure currentMaze = explorer.getMaze(); 
        while (!explorer.hasReachedExit()) {
            int orientation = explorer.getOrientation();
            switch(currentMaze.getTypeAtPosition(explorer.getForwardPosition(orientation+90))) { //check right hand of explorer.
                case Maze.CellType.Wall -> { //check right hand of explorer. If there is a wall keep moving forward
                    boolean result = pathInsturction('F');
                    if (!result && (currentMaze.getTypeAtPosition(explorer.getForwardPosition(orientation))==Maze.CellType.Wall)) { //if wall in front then turn left and move forward
                        pathInsturction('L');
                    }
                    break;
                } 
                case Maze.CellType.Space -> { //if there is space then turn right and move forward
                    pathInsturction('R');
                    pathInsturction('F');
                    break;
                }
            }
        } 
        return FactorizePath(path);
    } 

    public Boolean VerifyPath(String instructions){ //make the explorer go through path and if position of explorer = the exit then the path is true. After verifcation reset explorer position to original
        if (instructions.matches("^[FLR]+$")) { //to be a correct path must contain one of these letters at least once.
            explorer.moveInstructions(instructions);
            boolean isExit = explorer.hasReachedExit();
            return isExit;
        }
        return false;
    }
}