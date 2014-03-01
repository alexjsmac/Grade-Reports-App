package ca.uwo.csd.cs2212.team10;

import java.util.ArrayList;

/**
 * Class representing a gradebook with several courses
 * 
 * @author Team 10
 */
public class Gradebook {

	/*****************************************************************
	 * Instance Variables
	 ****************************************************************/
	
	// Course with which user is currently working
	private Course activeCourse;
	
	// List of all courses
	private ArrayList<Course> courses;
	
	/**
	 * Create a new empty gradebook
	 */
	public Gradebook()
	{
	}
	
	/*****************************************************************
	 * Public Methods
	 ****************************************************************/
	
	/**
	 * Sets the active course. Throws an IllegalArgumentException if the given course is not in the gradebook.
	 * @param course The course to set as active
	 */
	public void setActiveCourse(Course course)
	{
	}
	
	/**
	 * Gets the active course
	 * @return Course with which user is currently working
	 */
	public Course getActiveCourse()
	{
	}
	
	/**
	 * Gets the list of courses in the gradebook
	 * @return List of all courses
	 */
	public ArrayList<Course> getCourseList()
	{
	}

	/**
	 * Adds a course to the gradebook
	 * @param course The course to add
	 */
	public void addCourse(Course course)
	{
	}
	
	/**
	 * Removes a course from the gradebook
	 * @param course The course to add
	 */
	public void removeCourse(Course course)
	{
	}
}
