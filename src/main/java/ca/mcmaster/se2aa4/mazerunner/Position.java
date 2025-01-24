package ca.mcmaster.se2aa4.mazerunner;

public class Position {
    private int x;
    private int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int[] getPosition(){
        return new int[] {x, y};
    }
    public String getStringPosition(){
        return "("+String.valueOf(x) +","+ String.valueOf(y)+")";
    }
}