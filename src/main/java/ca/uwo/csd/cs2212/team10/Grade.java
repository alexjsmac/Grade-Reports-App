package ca.uwo.csd.cs2212.team10;

import java.io.Serializable;

/**
 * Represents a student's grade for a particular deliverable
 * @author Team 10
 */
public class Grade implements Serializable{
    /* Constants */
    private static final long serialVersionUID = 1L; //for serializing
    
    /* Attributes */
    private float grade;
    private Deliverable deliverable;
    
    /* Constructor */
    public Grade(Deliverable deliverable, float grade){
    	this.deliverable = deliverable;
    	this.grade = grade;
    }
    
    /* Public Methods */
    
    public Deliverable getDeliverable(){
    	return deliverable;
    }
    
    public float getGrade(){
    	return grade;
    }
    
    public void setGrade(float grade){
    	this.grade = grade;
	}
	
	@Override
    public String toString() {
        return Float.toString(grade);
    }
}