package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Maze {
    private Position entry;
    private Position exit;
    private static final Logger logger = LogManager.getLogger();
    private final LinkedHashMap<Position, Integer> mazeGrid = new LinkedHashMap<>(); //keys: 2dVectorPosition values: 0 = wall , 1 = space

    public Maze(String filePath){ //to create maze need a valid file path
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int row=0;
            while ((line = reader.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    Position pos = new Position(0,0);
                    pos.setPosition(row, col);
                    if (line.charAt(col) == '#') {
                        this.mazeGrid.put(pos,0);
                    } else if (line.charAt(col) == ' ') {
                        this.mazeGrid.put(pos,1);
                        if(col==0) { //if any space found in the first column then its entry 
                            entry=new Position(row,col);
                        } else if(col==line.length()-1){//if any space found in the last column then its exit
                            exit=new Position(row,col);
                        }
                    }
                }
                row++;
            }
        } catch (Exception e) {
            logger.error("/!\\ An error has occured /!\\  ("+e.getMessage()+")");
        }  
        logger.info("*Entry: "+entry.getStringPosition()+"  *Exit: "+exit.getStringPosition());
    }
    
    public Position getEntry(){
        return entry;
    }

    public Position getexit(){
        return exit;
    }

    public int getTypeAtPosition(Position position){ //Returns the value of the position key in the grid. Returns: -1=doesntExist , 0=wall , 1=space
        return mazeGrid.getOrDefault(position,-1); 
    }
    public void printMaze(){
        int pastX = 0;
        for(Map.Entry<Position, Integer> mapEntry : mazeGrid.entrySet()) {
            Position pos = mapEntry.getKey();
            int type = mapEntry.getValue();
            if (pastX != pos.getPosition()[0]){
                pastX = pos.getPosition()[0];
                System.out.print(System.lineSeparator());
            }
            if (type == 0) {
                System.out.print("WALL ");
            } else if (type == 1) {
                System.out.print("PASS ");
            }
        }
        System.out.print("\n");
    }

}