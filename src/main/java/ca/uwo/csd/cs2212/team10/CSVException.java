package ca.uwo.csd.cs2212.team10;

public class CSVException extends Exception{
    public static final int BAD_FORMAT = -1;
    
    private int badLines;
    
    public CSVException(int badLines){
        super();
        this.badLines = badLines;
    }
    
    public int getNumBadLines(){
        return badLines;
    }
}
