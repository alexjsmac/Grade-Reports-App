package ca.uwo.csd.cs2212.team10;

import org.junit.Test;
import org.junit.Before;
import static junit.framework.Assert.*;

public class TestStudent{
    private Student student;
    private Deliverable asn1;
    private Deliverable asn2;
    private Deliverable exam1;
    private Deliverable exam2;
    
    @Before
    public void setup(){
        student = new Student("John", "Doe", "1234567890", "jdoe@example.com");
        
        asn1 = new Deliverable("Asn1", Deliverable.ASSIGNMENT_TYPE, 5);
        asn2 = new Deliverable("Asn2", Deliverable.ASSIGNMENT_TYPE, 10);
        exam1 = new Deliverable("Midterm", Deliverable.EXAM_TYPE, 35);
        exam2 = new Deliverable("Final", Deliverable.EXAM_TYPE, 50);
    }
    
    @Test
    public void testConstructorSetsAttributes(){
        student = new Student("John", "Doe", "1234567890", "jdoe@example.com");
        
        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("1234567890", student.getNum());
        assertEquals("jdoe@example.com", student.getEmail());
    }
    
    @Test
    public void testSetFirstName(){
        student.setFirstName("Jane");
        assertEquals("Jane", student.getFirstName());
    }
    
    @Test
    public void testSetLastName(){
        student.setLastName("Foo");
        assertEquals("Foo", student.getLastName());
    }
    
    @Test
    public void testSetEmail(){
        student.setEmail("jfoo@example.com");
        assertEquals("jfoo@example.com", student.getEmail());
    }
    
    @Test
    public void testSetStudentNumber(){
        student.setNum("987654321");
        assertEquals("987654321", student.getNum());
    }
    
    @Test
    public void testSetGrade(){
        student.setGrade(asn1, 90.0);
        assertEquals(90.0, student.getGrade(asn1));
        
        student.removeGrade(asn1);
    }
    
    @Test
    public void testRemoveGrade(){
        student.setGrade(asn1, 90.0);
        
        student.removeGrade(asn1);
        assertNull(student.getGrade(asn1));
    }
    
    @Test
    public void testCalcAveragesWithNoGrades(){
        assertEquals(0.0, student.calcAverage());
        assertEquals(0.0, student.calcAverage(Deliverable.ASSIGNMENT_TYPE));
        assertEquals(0.0, student.calcAverage(Deliverable.EXAM_TYPE));
    }
    
    @Test
    public void testCalcAveragesWithGrades(){
        student.setGrade(asn1, 80.1);
        student.setGrade(asn2, 72.4);
        student.setGrade(exam1, 86.5);
        student.setGrade(exam2, 92.0);
        
        assertEquals((80.1*5+72.4*10+86.5*35+92.0*50)/(5+10+35+50), student.calcAverage());
        assertEquals((80.1*5+72.4*10)/(10+5), student.calcAverage(Deliverable.ASSIGNMENT_TYPE));
        assertEquals((86.5*35+92.0*50)/(35+50), student.calcAverage(Deliverable.EXAM_TYPE));
        
        student.removeGrade(asn1);
        student.removeGrade(asn2);
        student.removeGrade(exam1);
        student.removeGrade(exam2);
    }
}
