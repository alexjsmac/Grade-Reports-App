package ca.uwo.csd.cs2212.team10;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Represents the main Gradebook of the project
 * @author Team 10
 */
public class Gradebook implements Serializable{
    /* Constants */
    private static final long serialVersionUID = 1L; //for serializing

    /* Attributes */
    private Course activeCourse;
    private ArrayList<Course> courses; //the courses in the Gradebook

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
        activeCourse = course;
    }
}
