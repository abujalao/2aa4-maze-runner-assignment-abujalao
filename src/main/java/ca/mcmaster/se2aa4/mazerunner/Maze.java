package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Maze {
    public enum Types {
        wall,
        space,
        NotAvailable
    }
    private final static  Logger logger = LogManager.getLogger();
    private final LinkedHashMap<Position, Types> mazeGrid = new LinkedHashMap<>(); //keys: 2dVectorPosition values: 0 = wall , 1 = space
    private int columnSize=0;
    private Position entry;
    private Position exit;
    
    public Maze(String filePath){ //to create maze need a valid file path
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<String> lines = new ArrayList<>();
            String line;
            
            while ((line = reader.readLine()) != null) { 
                lines.add(line); //add lines in temporary array list to read again from it instead of reading the file over again
                columnSize = Math.max(columnSize, line.length()); //find the longest line length to fill empty lines (for example an empty line will be interpreted as a straight line with spaces. Amount of spaces = columSize)
            }
            reader.close();
            int row=0;
            for (String storedLine : lines) {
                for (int col = 0; col < columnSize; col++) {
                    Position pos = new Position(0,0);
                    pos.setPosition(row, col);

                    char lineCharacter;
                    if (col < storedLine.length()) { 
                        lineCharacter=storedLine.charAt(col);
                    } else { //This runs if the line is empty or incomplete in txt file. For example line with no characters means line.length()=0, so charAt wont work. The whole line will be filled with spaces instead.
                        lineCharacter=' ';
                    }
                    if (lineCharacter == '#') {
                        this.mazeGrid.put(pos,Types.wall);
                    } else if (lineCharacter == ' ') {
                        this.mazeGrid.put(pos,Types.space);
                        if(col==0) { //if any space found in the first column then its entry   
                            entry=new Position(row,col);
                        } else if(col==columnSize-1){//if any space found in the last column then its exit
                            exit=new Position(row,col);
                        }
                    }
                }
                row++;
            }
            lines.clear();//clear list as it is no longer needed.
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

    public Types getTypeAtPosition(Position position){ //Returns the value of the position key in the grid. Returns: -1=doesntExist , 0=wall , 1=space
        return mazeGrid.getOrDefault(position,Types.NotAvailable); 
    }
    
    public void printMaze(){
        int pastRow = 0; //To check if the current row has changed by tracking the previous row. Print a new line for a new row if row changed.
        String maze="\n";
        for(Map.Entry<Position, Types> mapEntry : mazeGrid.entrySet()) { //loop through maze and print it using (row,column) position
            Position pos = mapEntry.getKey();
            Types type = mapEntry.getValue();
            if (pastRow != pos.getPosition()[0]){ //compare previous row to current row
                pastRow = pos.getPosition()[0];
                maze+=System.lineSeparator();
            }
            if (type == Types.wall) {
               maze+="WALL ";
            } else if (type == Types.space) {
                maze+="PASS ";
            }
        }
        logger.info(maze);
    }

}