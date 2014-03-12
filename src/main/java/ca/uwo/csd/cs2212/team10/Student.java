package ca.uwo.csd.cs2212.team10;

import java.io.Serializable;
import java.util.*;

/**
 * Class representing a student in the course
 * @author Team10
 */

public class Student implements Serializable {
	/* Constants */
	private static final long serialVersionUID = 1L; //for serializing
	
	/* Attributes */
	private String firstName;
	private String lastName;
	private String email;
	private String num;
	private ArrayList<Grade> grades;
	
	/* Constructor */
	public Student(String firstName, String lastName, String email, String num){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.num = num;
		
		grades = new ArrayList<Grade>();
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
	
	public void addGrade(Grade grade){
		grades.add(grade);
	}
	
	public void removeGrade(Deliverable deliverable){
		for (int i = 0; i < grades.size(); i++){
			if (grades.get(i).getDeliverable() == deliverable)
				grades.remove(i);
		}
	}
	
	public Grade getGrade(Deliverable deliverable){
		for (Grade grade : grades){
			if (grade.getDeliverable() == deliverable)
				return grade;
		}
		
		return null;
	}
	
	public float calcAverage(){	
		float total = 0;
		int weights = 0;
		
		for (Grade grade : grades){
			total += grade.getGrade()*grade.getDeliverable().getWeight();
			weights += grade.getDeliverable().getWeight();
		}
		
		if (weights == 0)
			return 0;
		else
			return total/weights;
	}
	
	public float calcAssignmentAverage(){
		float total = 0;
		int weights = 0;
		
		for (Grade grade : grades){
			if (grade.getDeliverable().getType().equals("Assignment")){
				total += grade.getGrade()*grade.getDeliverable().getWeight();
				weights += grade.getDeliverable().getWeight();
			}
		}
		
		if (weights == 0)
			return 0;
		else
			return total/weights;
	}
	
	public float calcExamAverage(){
		float total = 0;
		int weights = 0;
		
		for (Grade grade : grades){
			if (grade.getDeliverable().getType().equals("Exam")){
				total += grade.getGrade()*grade.getDeliverable().getWeight();
				weights += grade.getDeliverable().getWeight();
			}
		}
		
		if (weights == 0)
			return 0;
		else
			return total/weights;
	}
}
