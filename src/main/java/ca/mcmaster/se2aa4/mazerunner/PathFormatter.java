package ca.mcmaster.se2aa4.mazerunner;

public class PathFormatter { //path in String related functions

    private PathFormatter() {} //private constructor to prevent instantiation
    //This class is not meant to be instantiated, only used as a utility class


    private static String formatRunLength(char letter, int count) {
        return (count > 1) ? count + Character.toString(letter) : Character.toString(letter);
    }
    
    public static String FactorizePath(String rawPath) {
        String factorized = "";
        char lastLetter=rawPath.charAt(0);
        int count = 1;
        for (int i = 1; i < rawPath.length(); i++) {
            char currentChar = rawPath.charAt(i);
            if (currentChar==lastLetter) {
                count++;
            } else {
                factorized += formatRunLength(lastLetter,count);
                factorized += " ";
                count = 1;
                lastLetter = currentChar;
            }
        }
        factorized += formatRunLength(lastLetter,count); //count what is left from last for loop iteration and add to factorized path
        return factorized;
    }

    public static String ExpandPath(String factorizedPath) {
        String expanded = "";
        for (int i = 0; i < factorizedPath.length(); i ++) {
            if (Character.isDigit(factorizedPath.charAt(i))) {
                String countString=""+factorizedPath.charAt(i);
                for (int v = i+1; v < factorizedPath.length(); v++) { //Loop to check if there is more numbers (ie. bigger than one digit) like 22F
                    if (Character.isDigit(factorizedPath.charAt(v))) {
                        countString+=factorizedPath.charAt(v);
                        i = v;
                    } else {
                        break;
                    }
                }
                int count=Integer.parseInt(countString); 
                char direction = factorizedPath.charAt(i + 1);
                for (int j = 0; j < count-1; j++) {
                    expanded+=direction;
                }
            } else {
                if (Character.isWhitespace(factorizedPath.charAt(i))) continue;
                expanded+=factorizedPath.charAt(i);
            }
        }
        
        return expanded;
    }
} 