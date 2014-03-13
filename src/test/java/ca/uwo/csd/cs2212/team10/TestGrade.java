package ca.uwo.csd.cs2212.team10;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.Before;

public class TestGrade{
	
	private Grade grade;
	private Deliverable deliverable;
	
	@Before
	public void setup(){
		deliverable = new Deliverable("Assignment 1", "Assignment", 25);
		grade = new Grade(deliverable,86.5f);
	}
	
	@Test
	public void testConstructorSetAttributes(){
		Assert.assertEquals(deliverable, grade.getDeliverable());
		Assert.assertEquals(86.5f, grade.getGrade());
	}
	
	@Test
	public void testSetGrade(){
		grade.setGrade(74.2f);
		Assert.assertEquals(74.2f, grade.getGrade());
	}
}