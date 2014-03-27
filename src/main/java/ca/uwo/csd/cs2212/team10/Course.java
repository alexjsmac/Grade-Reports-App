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

    public void validateStudentModification(Student student, String newEmail, String newNum) throws DuplicateStudentException{
        for (Student s : students)
            if (s != student){
                if (newEmail.equals(s.getEmail()))
                    throw new DuplicateStudentException(DuplicateStudentException.DUP_EMAIL);
                else if (newNum.equals(s.getNum()))
                    throw new DuplicateStudentException(DuplicateStudentException.DUP_NUMBER);
            }
    }
    
    public void addStudent(Student student){
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
    
    public void importStudents(CSVReader reader) throws IOException, CSVException{
        String[] line;
        String firstName, lastName, num, email;
        int invalidLines = 0;
        
        while ((line = reader.readNext()) != null){
            if (line.length != 14){
                invalidLines++;
                continue;
            }
            
            firstName = line[10];
            lastName = line[9];
            num = line[8];
            email = line[13];
            
            try{
                validateStudentModification(null, email, num);
            } catch (DuplicateStudentException e){
                invalidLines++;
                continue;
            }
            
            addStudent(new Student(firstName, lastName, num, email));
        }
        
        if (invalidLines > 0)
            throw new CSVException(invalidLines);
    }
    
    public void importGrades(CSVReader reader) throws IOException, CSVException{
        String[] line;
        ArrayList<String> names = new ArrayList<String>();
        if((line = reader.readNext()) != null){
            if(!(line[0].equalsIgnoreCase("Student Number"))){
                throw new IOException("Student Number column not present");
            }
            for (int i=0;i<line.length;i++){
                names.add(line[i+1]);
            }
        }
        
        while((line = reader.readNext()) != null){
            for (int i=0;i<getStudentList().size();i++){
                if(line[0] == null){
                    throw new IOException("Student Number must be present");
                }
                else if(line[0].equals(getStudentList().get(i).getNum())){
                    for(int j=0;j<names.size();j++){
                        for(int k=0;k<deliverables.size();k++){
                            if(deliverables.get(k).getName().equalsIgnoreCase(names.get(j))){
                                getStudentList().get(i).setGrade(deliverables.get(k), Double.parseDouble(line[j]));
                            }
                        }
                    }
                }
                else{
                    throw new IOException("Student does not exist in the course");
                }
            }
        }
    }
    
    public void exportGrades(CSVWriter writer){
        ArrayList<String> currLine;
        
        //write the header
        currLine = new ArrayList<String>();
        
        currLine.add("First Name");
        currLine.add("Last Name");
        currLine.add("Student Number");
        currLine.add("Email");
        for(Deliverable d : deliverables)
            currLine.add(d.getName());
        
        writer.writeNext(currLine.toArray(new String[0]));
        
        //write each student
        for (Student s : students){
            currLine = new ArrayList<String>();
            
            currLine.add(s.getFirstName());
            currLine.add(s.getLastName());
            currLine.add(s.getNum());
            currLine.add(s.getEmail());
            for(Deliverable d : deliverables)
                currLine.add(s.getGrade(d).toString());
            
            writer.writeNext(currLine.toArray(new String[0]));
        }
    }

    @Override
    public String toString() {
        return title + " " + code + " - " + term;
    }
}
