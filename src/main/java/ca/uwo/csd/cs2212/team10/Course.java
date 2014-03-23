package ca.uwo.csd.cs2212.team10;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import au.com.bytecode.opencsv.*;
import java.io.*;

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
    private List<Student> students;
    private List<Deliverable> deliverables;

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

    public List<Student> getStudentList() {
        return students;
    }

    public List<Deliverable> getDeliverableList() {
        return deliverables;
    }

    public boolean isUnique(Student oldStudent, String newEmail, String newNum){
        for (Student student : students)
            if (student != oldStudent)
                if (newEmail.equals(student.getEmail()) || newNum.equals(student.getNum()))
                    return false;
        return true;
    }
    
    public void addStudent(Student student) throws DuplicateStudentException{
        //Check that new student has unique student number and email
        if (!isUnique(null, student.getEmail(), student.getNum()))
            throw new DuplicateStudentException("Student info not unique");
       
        students.add(student);

        //Add each deliverable to the grade list
        for (Deliverable deliverable : deliverables)
            student.setGrade(deliverable, 0.0);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void addDeliverable(Deliverable deliverable) {
        deliverables.add(deliverable);
        
        //Add the deliverable to the grade list of each student
        for (Student student : students)
            student.setGrade(deliverable, 0.0);
    }

    public void removeDeliverable(Deliverable deliverable) {
        deliverables.remove(deliverable);
        
        //Remove the deliverable from the grade list of each student
        for (Student student : students)
            student.removeGrade(deliverable);
    }
    
    public void importStudents(CSVReader reader) throws IOException{
    	String[] line;
    	while ((line = reader.readNext()) != null){
    		Student toAdd = new Student(line[10],line[9],line[13],line[8]);
    		students.add(toAdd);
    	}
    }
    
    //TODO: Finish this
    public void importGrades(CSVReader reader) throws IOException{
    	String[] line;
    	if((line = reader.readNext()) != null){
    		if(!(line[0].equalsIgnoreCase("Student Number"))){
    			throw new IOException("Student Number column not present");
    		}
    	}
    }
    
    
    public void exportGrades(CSVWriter writer) throws IOException{
    	int size = deliverables.size()+4;
    	String[] header = new String[size];
    	header[0] = "First Name";
    	header[1] = "Last Name";
    	header[2] = "Student Number";
    	header[3] = "Email";
        for(int i=0;i<deliverables.size();i++){
        	header[i+4] = deliverables.get(i).getName();
        }
        writer.writeNext(header);
        
        String[] student = new String[size];
        for (int i=0;i<getStudentList().size();i++){
        	student[0] = getStudentList().get(i).getFirstName();
        	student[1] = getStudentList().get(i).getLastName();
        	student[2] = getStudentList().get(i).getNum();
        	student[3] = getStudentList().get(i).getEmail();
        	
        	for(int j=0;j<deliverables.size();j++){
        		student[j+4] = Double.toString(getStudentList().get(i).getGrade(deliverables.get(j)));
        	}
        	writer.writeNext(student);
        }
    	writer.close();    	
    }

    @Override
    public String toString() {
        return code + " - " + term + " - " + title;
    }
}
