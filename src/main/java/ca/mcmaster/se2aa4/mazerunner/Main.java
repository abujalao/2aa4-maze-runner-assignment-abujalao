package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    private static String mazeFileLocation;
    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        try {
            
            Options options = new Options();
            options.addOption("i", true, "Provided maze text file");
            CommandLineParser parser = new DefaultParser();
            try {
                CommandLine cmd = parser.parse(options, args);
                mazeFileLocation = cmd.getOptionValue("i","");
            } catch (ParseException pe) {
                logger.error("An error has occurred");
            }
            if (mazeFileLocation.equals("")) {
                throw new Exception("No file provided");
            }
            logger.info("**** Reading the maze from file " + mazeFileLocation);
            BufferedReader reader = new BufferedReader(new FileReader(mazeFileLocation));
            String line;
            while ((line = reader.readLine()) != null) {
                for (int idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        System.out.print("WALL ");
                    } else if (line.charAt(idx) == ' ') {
                        System.out.print("PASS ");
                    }
                }
                System.out.print(System.lineSeparator());
            }
        } catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\  ("+e.getMessage()+")");
        }
        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}
