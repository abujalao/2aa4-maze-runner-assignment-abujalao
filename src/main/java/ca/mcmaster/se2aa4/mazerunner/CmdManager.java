package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
public class CmdManager {
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
        } catch (Exception e) {
            return null;
        }
    }
}