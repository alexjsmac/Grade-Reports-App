package ca.uwo.csd.cs2212.team10;

import java.io.Serializable;

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
	private int studentNumber;
	
	/* Constructor */
	public Student(String firstName, String lastName, String email, int studentNumber){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.studentNumber = studentNumber;
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
	
	public void setStudentNumber(int studentNumber){
		this.studentNumber = studentNumber;
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
	
	public int getStudentNumber(){
		return studentNumber;
	}
}
