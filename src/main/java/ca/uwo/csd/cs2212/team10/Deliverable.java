package ca.uwo.csd.cs2212.team10;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a gradeable item to be completed by a student in a course
 * @author Team 10
 */
public class Deliverable implements Serializable{
	/* Constants */
    private static final long serialVersionUID = 1L; //for serializing

    /* Attributes */
    private String name;
    private String type;
    private int weight;
    

    /* Constructor */
    public Deliverable(String name, String type, int weight){
        this.name = name;
        this.type = type;
        this.weight = weight;
    }
    
    /* Public Methods */
    
    public String getName(){
    	return name;
    }
    
    public String getType(){
    	return type;
    }
    
    public int getWeight(){
    	return weight;
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public void setType(String type){
    	this.type = type;
    }
    
    public void setWeight(int weight){
    	this.weight = weight;
    }
}