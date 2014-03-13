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
		deliverable = new Deliverable("Foo1", Deliverable.ASSIGNMENT_TYPE, 0);
    }
    
    @Test
    public void testConstructorSetsAttributes(){
        course = new Course("foo", "bar", "daz");
        
        assertEquals("foo", course.getTitle());
        assertEquals("bar", course.getCode());
        assertEquals("daz", course.getTerm());
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
    public void testAddStudent(){
        course.addStudent(student);
		assertTrue(course.getStudentList().contains(student));
		
		course.removeStudent(student);
    }
	
	@Test
    public void testRemoveStudent(){
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
    public void testAddStudentAlsoAddsGrades(){
        course.addDeliverable(deliverable);
		course.addStudent(student);
		
		assertEquals(student.getGrade(deliverable), 0.0);
		
		course.removeStudent(student);
		course.removeDeliverable(deliverable);
    }
	
	@Test
    public void testAddDeliverableAlsoAddsGradesToStudents(){
		course.addStudent(student);
		course.addDeliverable(deliverable);
		
		assertEquals(student.getGrade(deliverable), 0.0);
		
		course.removeDeliverable(deliverable);
		course.removeStudent(student);
    }
	
	@Test
    public void testRemoveDeliverableAlsoRemovesGradesFromStudents(){
        course.addStudent(student);
		course.addDeliverable(deliverable);
		
		course.removeDeliverable(deliverable);
		
		assertNull(student.getGrade(deliverable));
    }
}
