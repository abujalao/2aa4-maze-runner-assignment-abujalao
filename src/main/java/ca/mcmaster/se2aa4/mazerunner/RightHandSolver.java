package ca.mcmaster.se2aa4.mazerunner;

public class RightHandSolver implements MazeSolver {
    @Override
    public String solveMaze(MazeStructure currentMaze, MazeExplorer exploreInterface) {
        StringBuilder solvedPath = new StringBuilder();
        while (!exploreInterface.hasReachedExit()) {
           int orientation = exploreInterface.getOrientation();
            switch(currentMaze.getTypeAtPosition(exploreInterface.getForwardPosition(orientation+90))) { //check right hand of explorer.
                case Maze.CellType.Wall -> { //check right hand of explorer. If there is a wall keep moving forward
                    boolean result = exploreInterface.moveInstruction('F');
                    if (!result && (currentMaze.getTypeAtPosition(exploreInterface.getForwardPosition(orientation))==Maze.CellType.Wall)) { //if wall in front then turn left and move forward
                        exploreInterface.moveInstruction('L');
                        solvedPath.append('L');
                    } else if (result) {
                        solvedPath.append('F');
                    }
                    break;
                } 
                case Maze.CellType.Space -> { //if there is space then turn right and move forward
                    exploreInterface.moveInstruction('R');
                    exploreInterface.moveInstruction('F');
                    solvedPath.append("RF");
                    break;
                }
            }
        }
        return PathFormatter.FactorizePath(solvedPath.toString());
    }
} 