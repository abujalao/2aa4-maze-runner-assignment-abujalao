package ca.mcmaster.se2aa4.mazerunner;

public interface MazeStructure  {
    public Position getExit();
    public Position getEntry();
    public Maze.CellType getTypeAtPosition(Position position);
}