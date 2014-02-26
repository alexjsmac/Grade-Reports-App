package ca.uwo.csd.cs2212.team10;

import java.util.ArrayList;

/**
 * Represents the main Gradebook of the project
 * @author Team 10
 */
public class Gradebook {

    /* Attributes */
    private Course activeCourse;
    private ArrayList<Course> courses; //The courses in the Gradebook

    /* Constructor */
    public Gradebook(){
        courses = new ArrayList<Course>();
    }

    /* Public Methods */

    public void addCourse(Course course){
        courses.add(course);
    }

    public void removeCourse(Course course){
        courses.remove(course);
    }
    
    public ArrayList<Course> getCourseList(){
        return courses;
    }
    
    public Course getActiveCourse(){
        return activeCourse;
    }

    public void setActiveCourse(Course course){
		if (courses.contains(course))
			activeCourse = course;
		else
			throw new IllegalArgumentException("Course not in gradebook");
    }
}
