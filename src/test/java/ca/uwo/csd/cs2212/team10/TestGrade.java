package ca.uwo.csd.cs2212.team10;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.Before;

public class TestGrade{
	private Grade grade;
	private Deliverable deliverable;
	private float grade;
	
	@Before
	public void setup(){
		deliverable = new deliverable("Assignment 1", "Assignment", 25);
		grade = new float("86.7");
	}
	
	@Test
	public void testConstructorSetsAttriutes(){
		grade = new Grade(deliverable,grade);
		float confirm = 86.7;
		
		Assert.assertEquals(deliverable,grade.getDeliverable());
		Assert.assertEquals(confirm, grade.getGrade());
	}
	
	@Test
	public void testSetGrade(){
		float set = 75.5;
		grade.setGrade(set);
		Assert.assertEquals(set, grade.getGrade());
	}
}