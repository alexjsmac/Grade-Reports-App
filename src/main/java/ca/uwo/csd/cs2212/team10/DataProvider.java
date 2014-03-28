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

        JavaBean bean1 = new JavaBean(stud1.getFirstName(), stud1.getLastName(), stud1.getNum(), stud1.getEmail(),
                math.getTitle(), math.getCode(), math.getTerm(), asn1.getName(), stud1.getGrade(asn1), stud1.calcAverage());
        beans.add(bean1);

        JavaBean bean2 = new JavaBean(stud1.getFirstName(), stud1.getLastName(), stud1.getNum(), stud1.getEmail(),
                math.getTitle(), math.getCode(), math.getTerm(), asn2.getName(), stud1.getGrade(asn2), stud1.calcAverage());
        beans.add(bean2);
        
        beans.add(new JavaBean(stud1.getFirstName(), stud1.getLastName(), stud1.getNum(), stud1.getEmail(),
                math.getTitle(), math.getCode(), math.getTerm(), asn3.getName(), stud1.getGrade(asn3), stud1.calcAverage()));

        beans.add(new JavaBean(stud1.getFirstName(), stud1.getLastName(), stud1.getNum(), stud1.getEmail(),
                math.getTitle(), math.getCode(), math.getTerm(), midterm.getName(), stud1.getGrade(midterm), stud1.calcAverage()));

        beans.add(new JavaBean(stud1.getFirstName(), stud1.getLastName(), stud1.getNum(), stud1.getEmail(),
                math.getTitle(), math.getCode(), math.getTerm(), finalExam.getName(), stud1.getGrade(finalExam), stud1.calcAverage()));

        return beans;
    }
}
