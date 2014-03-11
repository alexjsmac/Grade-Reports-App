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
	private int studentNumber;
	private ArrayList<Grade> studentGrades;
	
	/* Constructor */
	public Student(String firstName, String lastName, String email, int studentNumber){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.studentNumber = studentNumber;
		studentGrades = new ArrayList<Grade>();
	}
	
	/* Public Methods */
	
	public void setFirstName(String newFirstName){
		firstName = newFirstName;
	}
	
	public void setLastName(String newLastName){
		lastName = newLastName;
	}
	
	public void setEmail(String newEmail){
		email = newEmail;
	}
	
	public void setStudentNumber(int newStudentNumber){
		studentNumber = newStudentNumber;
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
