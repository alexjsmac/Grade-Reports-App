package ca.uwo.csd.cs2212.team10;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.Before;

public class TestGradebook{
    private Gradebook gradebook;
    private Course course;
    
    @Before
    public void setup(){
		this.gradebook = new Gradebook();
		this.course = new Course("foo", "bar", "daz");
    }
    
    @Test
    public void testCourseListInitiallyEmpty(){
		gradebook = new Gradebook();
		Assert.assertTrue(gradebook.getCourseList().isEmpty());
    }
    
    @Test
    public void testActiveCourseInitiallyNull(){
		gradebook = new Gradebook();
		Assert.assertNull(gradebook.getActiveCourse());
    }
    
    @Test
    public void testAddAndRemoveCourse(){
		gradebook.addCourse(course);
		Assert.assertTrue(gradebook.getCourseList().contains(course));
		
		gradebook.removeCourse(course);
		Assert.assertFalse(gradebook.getCourseList().contains(course));
    }
    
    @Test
    public void testSetActiveCourse(){
		gradebook.addCourse(course);
		gradebook.setActiveCourse(course);
		
		Assert.assertSame(course, gradebook.getActiveCourse());
		
		gradebook.removeCourse(course);
    }
	
	@Test
    public void testSetActiveCourseToNull(){
		gradebook.setActiveCourse(null);
		
		Assert.assertNull(gradebook.getActiveCourse());
    }
}
