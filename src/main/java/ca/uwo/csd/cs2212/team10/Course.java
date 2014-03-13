package ca.uwo.csd.cs2212.team10;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a course in the Gradebook
 *
 * @author Team 10
 */
public class Course implements Serializable {
    /* Constants */

    private static final long serialVersionUID = 1L; //for serializing

    /* Attributes */
    private String code;
    private String term;
    private String title;
    private ArrayList<Student> students;
    private ArrayList<Deliverable> deliverables;

    /* Constructor */
    public Course(String title, String code, String term) {
        this.title = title;
        this.code = code;
        this.term = term;

        students = new ArrayList<Student>();
        deliverables = new ArrayList<Deliverable>();
    }

    /* Public Methods */
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getTerm() {
        return term;
    }

    public ArrayList<Student> getStudentList() {
        return students;
    }

    public ArrayList<Deliverable> getDeliverableList() {
        return deliverables;
    }

    public void addStudent(Student student) {
        students.add(student);
		
		//Add each deliverable to the grade list
        for (Deliverable deliverable : deliverables)
            student.addGrade(deliverable, 0.0);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void addDeliverable(Deliverable deliverable) {
        deliverables.add(deliverable);
		
        //Add the deliverable to the grade list of each student
        for (Student student : students)
            student.addGrade(deliverable, 0.0);
    }

    public void removeDeliverable(Deliverable deliverable) {
		deliverables.remove(deliverable);
		
        //Remove the deliverable from the grade list of each student
        for (Student student : students)
            student.removeGrade(deliverable);
    }

    @Override
    public String toString() {
        return code + " - " + title;
    }
}
