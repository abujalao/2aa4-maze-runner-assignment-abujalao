package ca.mcmaster.se2aa4.mazerunner;

public interface MazeSolver { //To apply the strategy pattern, we need to define an interface for the maze solver.
    String solveMaze(MazeStructure maze, MazeExplorer exploreInterface);
} 