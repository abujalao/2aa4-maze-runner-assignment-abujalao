package ca.mcmaster.se2aa4.mazerunner;

public class Explorer implements MazeExplorer {
    private final MazeStructure maze;
    private final Position position; //position in form (row,column)
    private int orientation = 90; //(Default: facing east) | Directions: north=0, east=90, south=180, west=270
    
    public Explorer(Position start,MazeStructure maze) {
        this.maze = maze; //need to access maze to make explorer check the maze walls
        this.position = start;
    }

    @Override
    public MazeStructure getMaze() {
        return maze;
    }

    @Override
    public int getOrientation() {
        return orientation;
    }

    @Override
    public Boolean hasReachedExit() {
        return position.equals(maze.getExit());
    }
    
    @Override
    public Position getForwardPosition() {//use method overloading to make "checkOrien" parameter optional
        return getForwardPosition(this.orientation);
    }

    @Override
    public Position getForwardPosition(int checkOrien) {
        checkOrien = ValidateOrientation(checkOrien);
        int[] position2D = position.getPosition();
        Position returnPos = new Position(position2D[0],position2D[1]);
        switch (checkOrien) {
            case 0 -> returnPos.setPosition(position2D[0]-1, position2D[1]);
            case 90 -> returnPos.setPosition(position2D[0], position2D[1]+1);
            case 180 -> returnPos.setPosition(position2D[0]+1, position2D[1]);
            case 270 -> returnPos.setPosition(position2D[0], position2D[1]-1);
        }
        return returnPos;
    } 

    private void setOrientation(int value) {
        this.orientation = ValidateOrientation(value);
    }

    private int ValidateOrientation(int value) { //check if orientation is out of 0-360 range
        if (value>=360) {
            value -= 360;
        } else if (value<0) {
            value += 360;
        }
        return value;
    }
    
    @Override
    public void moveInstructions(String instructions){ //used for path verification given string of instructions
        for (int i = 0; i < instructions.length(); i++) {
            moveInstruction(instructions.charAt(i));
        }
    }
    
    @Override
    public boolean moveInstruction(char instruction){ //return if move is successful or not
        switch(instruction) {
            case 'F'-> {
                Position forwardPos = getForwardPosition();
                switch(maze.getTypeAtPosition(getForwardPosition())) {
                    case Maze.CellType.Space->{
                        int[] forwardPosition2D = forwardPos.getPosition();
                        this.position.setPosition(forwardPosition2D[0],forwardPosition2D[1]);
                        return true;
                    }
                    
                    case Maze.CellType.NotAvailable -> { //in path verification if given path is moving out boundry
                        //dont walk out of boundary
                        return false;
                    }

                    case Maze.CellType.Wall -> { //Wall blocking the explorer
                        return false;
                    }
                }
            }

            case 'R'-> {
                setOrientation(orientation+90);
                return true;
            }

            case 'L'-> {
                setOrientation(orientation-90);
                return true;
            }
            default -> {}
        }
        return false;
    }
}