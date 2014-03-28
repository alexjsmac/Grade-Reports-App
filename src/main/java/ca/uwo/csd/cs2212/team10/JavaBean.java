package ca.uwo.csd.cs2212.team10;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class JavaBean {
    
    /* Attributes */
    private String courseTitle;
    private String courseTerm;
    private String courseCode;

    private Student student;
    private String firstName;
    private String lastName;
    private String email;
    private String number;
    private Double average;

    private Double deliverableGrade;
    private String deliverableName;
    
    public JavaBean(String studentFirstName, String studentLastName, String studentNumber, String studentEmail,
            String courseTitle, String courseCode, String courseTerm, String deliverableName, Double grade, Double average) {

        this.courseTitle = courseTitle;
        this.courseCode = courseCode;
        this.courseTerm = courseTerm;
        
        this.firstName = studentFirstName;
        this.lastName = studentLastName;
        this.email = studentEmail;
        this.number = studentNumber;

        this.deliverableName = deliverableName;
        this.deliverableGrade = grade;
        this.average = average;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getDeliverableName() {
        return deliverableName;
    }

    public Double getDeliverableGrade() {
        return deliverableGrade;
    }

    public Double getAverage() {
        return average;
    }
}
