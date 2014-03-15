package ca.uwo.csd.cs2212.team10;

import org.junit.Test;
import org.junit.Before;
import static junit.framework.Assert.*;

public class TestCourse{
    private Course course;
    private Student student;
    private Deliverable deliverable;
    
    @Before
    public void setup(){
        course = new Course("foo", "bar", "daz");
        student = new Student("foo", "bar", "daz", "1");
        deliverable = new Deliverable("Foo", Deliverable.ASSIGNMENT_TYPE, 0);
    }
    
    @Test
    public void testConstructorSetsAttributesAndCreatesEmptyLists(){
        course = new Course("foo", "bar", "daz");
        
        assertEquals("foo", course.getTitle());
        assertEquals("bar", course.getCode());
        assertEquals("daz", course.getTerm());
        
        assertTrue(course.getStudentList().isEmpty());
        assertTrue(course.getDeliverableList().isEmpty());
    }
    
    @Test
    public void testSetTitle(){
        course.setTitle("title");
        assertEquals("title", course.getTitle());
    }
    
    @Test
    public void testSetCode(){
        course.setCode("code");
        assertEquals("code", course.getCode());
    }
    
    @Test
    public void testSetTerm(){
        course.setTerm("term");
        assertEquals("term", course.getTerm());
    }
    
    @Test
    public void testAddStudent() throws DuplicateStudentException{
        course.addStudent(student);
        assertTrue(course.getStudentList().contains(student));
        
        course.removeStudent(student);
    }
    
    @Test
    public void testRemoveStudent() throws DuplicateStudentException{
        course.addStudent(student);
        
        course.removeStudent(student);
        assertFalse(course.getStudentList().contains(student));
    }
    
    @Test
    public void testAddDeliverable(){
        course.addDeliverable(deliverable);
        assertTrue(course.getDeliverableList().contains(deliverable));
        
        course.removeDeliverable(deliverable);
    }
    
    @Test
    public void testRemoveDeliverable(){
        course.addDeliverable(deliverable);
        
        course.removeDeliverable(deliverable);
        assertFalse(course.getDeliverableList().contains(deliverable));
    }
    
    @Test
    public void testAddStudentAlsoAddsGrades() throws DuplicateStudentException{
        course.addDeliverable(deliverable);
        course.addStudent(student);
        
        assertEquals(0.0, student.getGrade(deliverable));
        
        course.removeStudent(student);
        course.removeDeliverable(deliverable);
    }
    
    @Test
    public void testAddDeliverableAlsoAddsGradesToStudents() throws DuplicateStudentException{
        course.addStudent(student);
        course.addDeliverable(deliverable);
        
        assertEquals(0.0, student.getGrade(deliverable));
        
        course.removeDeliverable(deliverable);
        course.removeStudent(student);
    }
    
    @Test
    public void testRemoveDeliverableAlsoRemovesGradesFromStudents() throws DuplicateStudentException{
        course.addStudent(student);
        course.addDeliverable(deliverable);
        
        course.removeDeliverable(deliverable);
        
        assertNull(student.getGrade(deliverable));
    }
    
    @Test(expected = DuplicateStudentException.class)
    public void testAddingDuplicateStudentThrowsException() throws DuplicateStudentException{
        course.addStudent(student);
        course.addStudent(new Student("foo", "bar", student.getEmail(), student.getNum()));
    }
    
    @Test
    public void testToStringReturnsAString(){
        assertNotNull(course.toString());
    }
}
