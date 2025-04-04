package ca.mcmaster.se2aa4.mazerunner;
public interface MazeExplorer {
    public MazeStructure getMaze();
    public Boolean hasReachedExit();
    public int getOrientation();
    public Position getForwardPosition(int checkOrien);
    public void moveInstructions(String instructions);
    public boolean moveInstruction(char instruction);
    public Position getForwardPosition();
}