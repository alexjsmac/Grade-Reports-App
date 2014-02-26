package ca.uwo.csd.cs2212.team10;

import java.util.ArrayList;

/**
 * Represents the main Gradebook of the project
 * @author Team 10
 */
public class Gradebook {

    /* Attributes */
    Course activeCourse;
    ArrayList<Course> courses; //The courses in the Gradebook

    /* Constructor */
    public void Gradebook(){
        courses = new ArrayList();
    }

    /* Public Methods */

    public void addCourse(Course course){
        courses.add(course);
    }

    public void removeCourse(Course course){
        courses.remove(course);
    }

    public void setActiveCourse(Course course){
        activeCourse = course;
    }
}
