package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

interface Pathfinder {
    String findPath();
}

public class Explorer implements Pathfinder {
    private static final Logger logger = LogManager.getLogger();
    private final Maze maze;
    private final Position position; //position in form (row,column)
    private int orientation = 90; //(Default: facing east) | Directions: north=0, east=90, south=180, west=270
    private boolean reachedExit = false;
    private String path="";

    public Explorer(Position start,Maze maze) {
        this.maze = maze; //need to access maze to make explorer check the maze walls
        this.position = start;
    }

    private Position getForwardPosition() {//use method overloading to make "checkOrien" parameter optional 
        return getForwardPosition(this.orientation);
    }
    
    private Position getForwardPosition(int checkOrien) {
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

    private int ValidateOrientation(int value) { //check if orientation is out of 0-360 range
        if (value>=360) {
            value -= 360;
        } else if (value<0) {
            value += 360;
        }
        return value;
    }

    private void setOrientation(int value) {
        this.orientation = ValidateOrientation(value);
    }
    
    private boolean moveInstruction(char instruction) { //used for path finding
        boolean res = moveInstruction(instruction,false,false);
        if (res) {
            path+=instruction;
        }
        return res;
    }

    private boolean moveInstruction(char instruction,boolean verification, boolean isLastInstruction){ //return if move is successfull or not
        switch(instruction) {
            case 'F'-> {
                Position forwardPos = getForwardPosition();
                String movementType = verification? "VERIFICATION MOVEMENT" : "NORMAL MOVEMENT";
                switch(maze.getTypeAtPosition(getForwardPosition())) {
                    case Maze.CellType.Space->{
                        int[] forwardPosition2D = forwardPos.getPosition();
                        this.position.setPosition(forwardPosition2D[0],forwardPosition2D[1]);
                        logger.info("["+movementType+"] Moved forward");
                        logger.info("CurrentPosition: "+this.position.getStringPosition());
                        if (!verification && position.equals(maze.getExit())&&(isLastInstruction||!verification)) {//win condition
                            logger.info("reached end");
                            reachedExit = true;
                        }
                        return true;
                    }
                    
                    case Maze.CellType.NotAvailable -> { //in path verification if given path is moving out boundry
                        int[] forwardPosition2D = forwardPos.getPosition();
                        this.position.setPosition(forwardPosition2D[0],forwardPosition2D[1]);
                        logger.info("Moved out of boundry");
                        return false;
                    }

                    case Maze.CellType.Wall -> { //Wall blocking the explorer
                        logger.info("cant move forward");
                        return false;
                    }
                }
            }
            case 'R'-> {
                setOrientation(orientation+90);
                logger.info("Turned right");
                logger.info("CurrentOrientation: "+ this.orientation);
                return true;
            }
            case 'L'-> {
                setOrientation(orientation-90);
                logger.info("Turned left");
                return true;
            }
            default -> logger.error("Invalid movement instruction: "+instruction);
        }
        return false;
    }

    private void moveInstructions(String instructions,boolean verification){
        for (int i = 0; i < instructions.length(); i++) {
            if (i==instructions.length()-1){
                moveInstruction(instructions.charAt(i),verification,true);
            } else {
                moveInstruction(instructions.charAt(i),verification,false);
            }
        }
    }

    private String formatRunLength(char letter, int count) {
        return (count > 1) ? count + Character.toString(letter) : Character.toString(letter);
    }

    private String FactorizeForm(String instructions){
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

    @Override
    public String findPath(){
        while (!reachedExit) {
            switch(maze.getTypeAtPosition(getForwardPosition(orientation+90))) { //check right hand of explorer.
                case Maze.CellType.Wall -> { //check right hand of explorer. If there is a wall keep moving forward
                    boolean result = moveInstruction('F');
                    if (!result && (maze.getTypeAtPosition(getForwardPosition(orientation))==Maze.CellType.Wall)) { //if wall in front then turn left and move forward
                        moveInstruction('L');
                    }
                    break;
                } 
                case Maze.CellType.Space -> { //if there is space then turn right and move forward
                    //logger.info("Turn right and front");
                    moveInstruction('R');
                    moveInstruction('F');
                    break;
                }
            }
        } 
        return FactorizeForm(path);
    } 

    public Boolean VerifyPath(String instructions){ //make the explorer go through path and if position of explorer = the exit then the path is true. After verifcation reset explorer position to original
        if (instructions.matches("^[FLR]+$")) { //to be a correct path must contain one of these letters at least once.
            int[] originalPosition = position.getPosition();
            moveInstructions(instructions,true);
            boolean isExit = position.equals(maze.getExit());
            this.position.setPosition(originalPosition[0],originalPosition[1]);
            setOrientation(90);
            return isExit;
        } else {
            logger.error("canonical path contains only F, R and L symbols. Given path is invalid.");
        }
        return false;
    }
}