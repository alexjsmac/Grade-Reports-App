package ca.uwo.csd.cs2212.team10;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.Before;

public class TestStudent{
	private Student student;
	
	@Before
	public void setup(){
		student = new Student("John", "Doe", "jdoe@example.com","123456789");
	}
	
	@Test
	public void testConstructorSetsAttributes(){
		student = new Student("John", "Doe", "jdoe@example.com", "123456789");
		
		Assert.assertEquals("John", student.getFirstName());
		Assert.assertEquals("Doe", student.getLastName());
		Assert.assertEquals("jdoe@example.com", student.getEmail());
		Assert.assertEquals("123456789", student.getNum());
	}
	
	@Test
	public void testSetFirstName(){
		student.setFirstName("Jane");
		Assert.assertEquals("Jane", student.getFirstName());
	}
	
	@Test
	public void testSetLastName(){
		student.setLastName("Foo");
		Assert.assertEquals("Foo", student.getLastName());
	}
	
	@Test
	public void testSetEmail(){
		student.setEmail("jfoo@example.com");
		Assert.assertEquals("jfoo@example.com", student.getEmail());
	}
	
	@Test
	public void testSetStudentNumber(){
		student.setNum("987654321");
		Assert.assertEquals("987654321", student.getNum());
	}
}