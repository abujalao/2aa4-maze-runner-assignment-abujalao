package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Explorer {
    private static final Logger logger = LogManager.getLogger();
    private Maze maze;
    private Position position; //position in form (row,column)
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
    
    public void moveInstruction(char instruction){
        switch(instruction) {
            case 'F'-> {
                Position forwardPos = getForwardPosition();
                if (maze.getTypeAtPosition(getForwardPosition())==1) { //if there is no wall in front then move
                    int[] forwardPosition2D = forwardPos.getPosition();
                    this.position.setPosition(forwardPosition2D[0],forwardPosition2D[1]);
                    logger.info("Moved forward");
                    logger.info("CurrentPosition: "+this.position.getStringPosition());
                } else {
                    logger.info("cant move forward");
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
        }
    }
}