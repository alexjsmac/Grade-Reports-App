package ca.uwo.csd.cs2212.team10;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.Before;

public class TestDeliverable{
	
	private Deliverable deliverable;
	
	@Before
	public void setup(){
		deliverable = new Deliverable("Assignment 1", "Assignment", 25);
	}
	
	@Test
	public void testConstructorSetAttributes(){
		Assert.assertEquals("Assignment 1", deliverable.getName());
		Assert.assertEquals("Assignment", deliverable.getType());
		Assert.assertEquals(25, deliverable.getWeight());
	}
	
	@Test
	public void testSetName(){
		deliverable.setName("Exam 1");
		Assert.assertEquals("Exam 1", deliverable.getName());
	}
	
	@Test
	public void testSetType(){
		deliverable.setType("Exam");
		Assert.assertEquals("Exam", deliverable.getType());
	}
	
	@Test
	public void testSetWeight(){
		deliverable.setWeight(50);
		Assert.assertEquals(50, deliverable.getWeight());
	}
}