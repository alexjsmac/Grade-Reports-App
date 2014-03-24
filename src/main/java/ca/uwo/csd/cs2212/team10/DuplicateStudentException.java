package ca.uwo.csd.cs2212.team10;

public class DuplicateStudentException extends Exception{
    public static final int DUP_NUMBER = 1;
    public static final int DUP_EMAIL = 2;
    
    private int reason;
    
    public DuplicateStudentException(int reason){
        super();
        this.reason = reason;
    }
    
    public int getReason(){
        return reason;
    }
}
