package ca.uwo.csd.cs2212.team10;
     
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataProvider {
    public static Collection<JavaBean> loadData() throws Exception {
        
        Collection<JavaBean> beans = new ArrayList<JavaBean>();
        
        Course math = new Course("Math", "1600", "Fall 2013");
        
        Student stud1 = new Student("Alex", "Mac", "250685603", "amacle45@uwo.ca");
        
        math.addStudent(stud1);
        
        Deliverable asn1 = new Deliverable("asn1", 0, 10);
        Deliverable asn2 = new Deliverable("asn2", 0, 10);
        Deliverable asn3 = new Deliverable("asn3", 0, 10);
        Deliverable midterm = new Deliverable("midterm", 1, 30);
        Deliverable finalExam = new Deliverable("final", 1, 40);

        math.addDeliverable(asn1);
        math.addDeliverable(asn2);
        math.addDeliverable(asn3);
        math.addDeliverable(midterm);
        math.addDeliverable(finalExam);
        
        stud1.setGrade(asn1, 75.0);
        stud1.setGrade(asn2, 80.0);
        stud1.setGrade(asn3, 85.0);
        stud1.setGrade(midterm, 90.0);
        stud1.setGrade(finalExam, 82.0);

        for (Deliverable d : math.getDeliverableList())
             beans.add(new JavaBean(d.getName(), stud1.getGrade(d), math.calcAverage(d)));
        
        return beans;
    }
}
