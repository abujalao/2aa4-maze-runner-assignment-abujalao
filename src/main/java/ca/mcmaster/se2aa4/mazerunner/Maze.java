package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Maze {
    
    public enum CellType {
        Wall,
        Space,
        NotAvailable
    }

    private final static  Logger logger = LogManager.getLogger();
    private final LinkedHashMap<Position, CellType> mazeGrid = new LinkedHashMap<>(); // Keys: Position class | Values: enum CellType
    private int columnSize=0;
    private Position entry;
    private Position exit;
    
    public Maze(String filePath){ //to create maze need a valid file path
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row=0;
            while ((line = reader.readLine()) != null) { 
                columnSize = Math.max(columnSize, line.length()); //the first line is the longest and this will be used to fill the empty lines with space by checking columnSize and line.length()
                for (int col = 0; col < columnSize; col++) {
                    Position pos = new Position(0,0);
                    pos.setPosition(row, col);

                    char lineCharacter;
                    if (col < line.length()) { 
                        lineCharacter=line.charAt(col);
                    } else { //This runs if the line is empty or incomplete in txt file. For example line with no characters means line.length()=0, so charAt wont work. The whole line will be filled with spaces instead.
                        lineCharacter=' ';
                    }
                    if (lineCharacter == '#') {
                        this.mazeGrid.put(pos,CellType.Wall);
                    } else if (lineCharacter == ' ') {
                        this.mazeGrid.put(pos,CellType.Space);
                        if(col==0) { //if any space found in the first column then its entry   
                            entry=new Position(row,col);
                        } else if(col==columnSize-1){//if any space found in the last column then its exit
                            exit=new Position(row,col);
                        }
                    }
                }
                row++;
            }
            reader.close();
        } catch (Exception e) {
            logger.error("/!\\ An error has occured /!\\");
            System.exit(1);
        }  
    }
    
    public Position getEntry(){
        return entry;
    }

    public Position getExit(){
        return exit;
    }

    public CellType getTypeAtPosition(Position position){ //Returns the value of the position key in the grid. Returns: -1=doesntExist , 0=wall , 1=space
        return mazeGrid.getOrDefault(position,CellType.NotAvailable); 
    }
    
    public void printMaze(){
        int pastRow = 0; //To check if the current row has changed by tracking the previous row. Print a new line for a new row if row changed.
        String maze="\n";
        for(Map.Entry<Position, CellType> mapEntry : mazeGrid.entrySet()) { //loop through maze and print it using (row,column) position
            Position pos = mapEntry.getKey();
            CellType type = mapEntry.getValue();
            if (pastRow != pos.getPosition()[0]){ //compare previous row to current row
                pastRow = pos.getPosition()[0];
                maze+=System.lineSeparator();
            }
            if (type == CellType.Wall) {
               maze+="WALL ";
            } else if (type == CellType.Space) {
                maze+="PASS ";
            }
        }
        logger.info(maze);
    }

}