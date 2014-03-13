package ca.uwo.csd.cs2212.team10;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Class representing a student in the course
 * @author Team10
 */

public class Student implements Serializable 
	/* Constants */
	private static final long serialVersionUID = 1L; //for serializing
	
	/* Attributes */
	private String firstName;
	private String lastName;
	private String email;
	private String num;
	private HashMap<Deliverable, Double> grades;
	
	/* Constructor */
	public Student(String firstName, String lastName, String email, String num){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.num = num;
		
		grades = new HashMap<Deliverable, Double>();
	}
	
	/* Public Methods */
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setNum(String num){
		this.num = num;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getNum(){
		return num;
	}
	
	public void addGrade(Deliverable deliverable, Double grade){
		grades.put(deliverable, grade);
	}
	
	public void removeGrade(Deliverable deliverable){
		grades.remove(deliverable);
	}
	
	public Double getGrade(Deliverable deliverable){
		return grades.get(deliverable);
	}
	
	public void setGrade(Deliverable deliverable, Double grade){
		grades.put(deliverable, grade);
	}
	
	public double calcAverage(){	
		double total = 0;
		int weights = 0;
		
		for (Entry<Deliverable, Double> grade : grades.entrySet()){
			total += grade.getValue() * grade.getKey().getWeight();
			weights += grade.getKey().getWeight();
		}
		
		if (weights == 0)
			return 0;
		else
			return total/weights;
	}
	
	public double calcAverage(int type){
		double total = 0;
		int weights = 0;
		
		for (Entry<Deliverable, Double> grade : grades.entrySet()){
			if (grade.getKey().getType() == type){
				total += grade.getValue() * grade.getKey().getWeight();
				weights += grade.getKey().getWeight();
			}
		}
		
		if (weights == 0)
			return 0;
		else
			return total/weights;
	}
}
