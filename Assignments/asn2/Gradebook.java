package gradebook;
import java.util.*;

/**
 * Represents the main Gradebook of the project
 * @author alexmaclean
 */
public class Gradebook {

    /* Attributes */
    Course activeCourse;
    ArrayList<Course> courses;      // Sequence of all courses Gradebook manages
    
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
