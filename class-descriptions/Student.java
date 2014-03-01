package ca.uwo.csd.cs2212.team10;

import java.util.ArrayList;

/**
 * Class representing a student in a course.
 * 
 * @author Team 10
 */
public class Student {

	/*****************************************************************
	 * Instance Variables
	 ****************************************************************/
	// Student's first name
	private String firstName;
	
	// Student's last name
	private String lastName;
	
	// Student number
	private int num;
	
	// Student's email address
	private String email;
	
	// Student's grades
	private ArrayList<Grade> grades;
	
	/**
	 * Create a new Student object with the specified attributes and initially no grades
	 * @param firstName Student's first name
	 * @param lastName Student's last name
	 * @param num Student number
	 * @param email Student's email address
	 */
	public Student(String firstName, String lastName, int num, String email)
	{
	}
	
	/*****************************************************************
	 * Public Methods
	 ****************************************************************/
	
	/**
	 * Sets student's first name
	 * @param firstName The first name to set
	 */
	public void setFirstName(String firstName)
	{
	}
	
	/**
	 * Sets student's last name
	 * @param lastName The last name to set
	 */
	public void setLastName(String lastName)
	{
	}
	
	/**
	 * Sets student number
	 * @param num The student number to set
	 */
	public void setNum(int num)
	{
	}
	
	/**
	 * Sets student's email
	 * @param email The email to set
	 */
	public void setEmail(String email)
	{
	}
	
	/**
	 * Gets student's first name
	 * @return Student's first name
	 */
	public String getFirstName()
	{
	}
	
	/**
	 * Gets student's last name
	 * @return Student's last name
	 */
	public String getLastName()
	{
	}
	
	/**
	 * Gets student number
	 * @return Student number
	 */
	public int getNum()
	{
	}
	
	/**
	 * Sets student's email
	 * @return Student's email address
	 */
	public String getEmail()
	{
	}
	
	/**
	 * Adds a grade to the student
	 * @param grade The grade to add
	 */
	private void addGrade(Grade grade)
	{
	}
	
	/**
	 * Gets the student's grade of specified deliverable
	 * @param deliverable the requested deliverable
	 * @return The grade of specified deliverable, or null if no grade is assigned
	 */
	private Grade getGrade(Deliverable deliverable)
	{
	}
	
	/**
	 * Calculates student's average
	 * @return The student's average
	 */
	private float calcAverage()
	{
	}
	
	/**
	 * Calculates average of student's assignments
	 * @return The average of student's assignments
	 */
	private float calcAssignmentAverage()
	{
	}
	
	/**
	 * Calculates average of student's exams
	 * @return The average of student's exams
	 */
	private float calcExamAverage()
	{
	}
}
