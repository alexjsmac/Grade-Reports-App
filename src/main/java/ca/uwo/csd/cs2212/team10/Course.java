package ca.uwo.csd.cs2212.team10;

/**
 * Represents a course in the Gradebook
 * @author Team 10
 */
public class Course {

    /* Attributes */
    private String code;
    private String term;
    private String title;

    /* Constructor */
    public void Course(String title, String code, String term){
        this.title = title;
        this.code = code;
        this.term = term;
    }

    /* Public Methods */

    public void setTitle(String newTitle){
        title = newTitle;
    }

    public void setCode(String newCode){
        code = newCode;
    }

    public void setTerm(String newTerm){
        term = newTerm;
    }
}
