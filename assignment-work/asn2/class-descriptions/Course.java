package ca.uwo.csd.cs2212.team10;

import java.util.ArrayList;
import java.io.IOException;

/**
 * Class representing a course with students and deliverables
 * 
 * @author Team 10
 */
public class Course {

	/*****************************************************************
	 * Instance Variables
	 ****************************************************************/
	
	// Course code
	private String code;
	
	// Title of course
	private String title;
		
	// Term in which course is taught
	private String term;
	
	// List of students in the course
	private ArrayList<Student> students;
	
	// List of deliverables in the course
	private ArrayList<Deliverable> deliverables;
	
	/**
	 * Create a new course with the specified code, title, and term, and initially no students or deliverables
	 * @param code Course code
	 * @param title Title of course
	 * @param term Term in which course is taught
	 */
	public Course(String code, String title, String term)
	{
	}
	
	/*****************************************************************
	 * Public Methods
	 ****************************************************************/
	
	/**
	 * Sets course code
	 * @param code The code to set
	 */
	public void setCode(String code)
	{
	}
	
	/**
	 * Sets term
	 * @param term The term to set
	 */
	public void setTerm(String term)
	{
	}
	
	/**
	 * Sets course title
	 * @param title The title to set
	 */
	public void setTitle(String title)
	{
	}
	
	/**
	 * Gets course code
	 * @return Course code
	 */
	public String getCode()
	{
	}
	
	/**
	 * Gets term
	 * @return Term in which course is taught
	 */
	public String getTerm()
	{
	}
	
	/**
	 * Gets course title
	 * @return Title of course
	 */
	public String getTitle()
	{
	}
	
	/**
	 * Gets the list of students in the course
	 * @return List of students in the course
	 */
	public ArrayList<Student> getStudentList()
	{
	}

	/**
	 * Gets the list of deliverables in the course
	 * @return List of deliverables in the course
	 */
	public ArrayList<Deliverable> getDeliverableList()
	{
	}

	/**
	 * Adds a student to the course
	 * @param student The student to add
	 */
	public void addStudent(Student student)
	{
	}
	
	/**
	 * Removes a student from the course
	 * @param student The student to remove
	 */
	public void removeStudent(Student student)
	{
	}
	
	/**
	 * Adds a deliverable to the course
	 * @param deliverable The deliverable to add
	 */
	public void addDeliverable(Deliverable deliverable)
	{
	}
	
	/**
	 * Removes a deliverable from the course
	 * @param deliverable The deliverable to remove
	 */
	public void removeDeliverable(Deliverable deliverable)
	{
	}
	
	/**
	 * Imports list of students from a CSV file. Throws an IOException if the file could not be read.
	 * @param fileName The name of the file to read
	 */
	public void importStudents(String fileName) throws IOException
	{
	}
	
	/**
	 * Imports list of grades from a CSV file. Throws an IOException if the file could not be read.
	 * @param fileName The name of the file to read
	 */
	public void importGrades(String fileName) throws IOException
	{
	}
	
	/**
	 * Exports list of grades to a CSV file. Throws an IOException if the file could not be written.
	 * @param fileName The name of the file to write
	 */
	public void exportGrades(String fileName) throws IOException
	{
	}
}
