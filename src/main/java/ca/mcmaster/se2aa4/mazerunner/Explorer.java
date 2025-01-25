package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Explorer {
    private static final Logger logger = LogManager.getLogger();
    private final Maze maze;
    private final Position position; //position in form (row,column)
    private int orientation = 90; //(Default: facing east) | Directions: north=0, east=90, south 180, west=270

    public Explorer(Position start,Maze maze) {
        this.maze = maze; //need to access maze to make explorer check the maze walls
        this.position = start;
    }

    private Position getForwardPosition() {//use method overloading to make "checkOrien" parameter optional 
        return getForwardPosition(this.orientation);
    }
    
    private Position getForwardPosition(int checkOrien) {
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
        this.orientation = value;
        if (orientation>=360) {
            this.orientation -= 360;
        } else if (orientation<0) {
            this.orientation += 360;
        }
    }
    
    private void moveInstruction(char instruction,boolean verification, boolean isLastInstruction){
        switch(instruction) {
            case 'F'-> {
                Position forwardPos = getForwardPosition();
                String movementType = verification? "VERIFICATION MOVEMENT" : "NORMAL MOVEMENT";
                switch(maze.getTypeAtPosition(getForwardPosition())) {
                    case 1->{
                        int[] forwardPosition2D = forwardPos.getPosition();
                        this.position.setPosition(forwardPosition2D[0],forwardPosition2D[1]);
                        logger.info("["+movementType+"] Moved forward");
                        logger.info("CurrentPosition: "+this.position.getStringPosition());
                        if (!verification && position.equals(maze.getexit())&&isLastInstruction) {//win conditions to log that explorer has reached the exit
                            logger.info("reached the exit.");
                        }
                    }
                    
                    case -1 -> {
                        int[] forwardPosition2D = forwardPos.getPosition();
                        this.position.setPosition(forwardPosition2D[0],forwardPosition2D[1]);
                        logger.info("Moved out of boundry");
                    }

                    case 0 -> {
                        logger.info("cant move forward");
                    }
                }
            }
            case 'R'-> {
                if (maze.getTypeAtPosition(getForwardPosition(orientation+90))==1) { //if there is no wall to the right then rotate 90 deg
                    setOrientation(orientation+90);
                    logger.info("Turned right");
                    logger.info("CurrentOrientation: "+ this.orientation);
                } else {
                    logger.info("cant turn right");
                }
            }
            case 'L'-> {
                if (maze.getTypeAtPosition(getForwardPosition(orientation-90))==1) {
                    setOrientation(orientation-90);
                    logger.info("Turned left");
                    logger.info("CurrentOrientation: "+ this.orientation);
                    
                }else {
                    logger.info("cant turn left");
                }
            }
            default -> logger.error("Invalid movement instruction: "+instruction);
        }
    }

    public void moveInstructions(String instructions){
        moveInstructions(instructions,false);
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

    public Boolean VerifyPath(String instructions){ //make the explorer go through path and if position of explorer = the exit then the path is true. After verifcation reset explorer position to original
        if (instructions.matches("^[FLR]+$")) { //to be a correct path must contain one of these letters at least once.
            int[] originalPosition = position.getPosition();
            moveInstructions(instructions,true);
            boolean isExit = position.equals(maze.getexit());
            this.position.setPosition(originalPosition[0],originalPosition[1]);
            this.orientation = 90;
            return isExit;
        } else {
            logger.error("canonical path contains only F, R and L symbols. Given path is invalid.");
        }
        return false;
    }
}