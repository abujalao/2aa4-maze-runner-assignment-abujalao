package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class CmdManager {
    private final static Logger logger = LogManager.getLogger();
    private final static Options options = new Options();
    private final static CommandLineParser parser = new DefaultParser();
    private final String [] argumentVector;

    public CmdManager(String[] args) { //need argument vector to initialize cmd
        this.argumentVector = args;
    }

    public void addFlag(String flag, Boolean required ,String description){
        options.addOption(flag, required, description);
    }

    public String getFlag(String flag, String defaultValue){
        try {
            CommandLine cmd = parser.parse(options, argumentVector);
            String Result = cmd.getOptionValue(flag, defaultValue);
            return Result;
        } catch (ParseException e) {
            logger.error("/!\\ Parsing error has occured /!\\  " + e.getMessage());
        } catch (Exception e) {
            logger.error("/!\\ An error has occured /!\\  ("+e.getMessage()+")");
        }
        return null;
    }
}