package ca.uwo.csd.cs2212.team10;

import org.junit.Test;
import org.junit.Before;
import static junit.framework.Assert.*;

public class TestCommonFunctions {
	
	Double grade;
	CommonFunctions common;
	
	@Before
	public void setup(){
		grade = -1.0;
	}
	
	@Test
	public void testFormatGrade(){
		assertEquals("--.--%", common.formatGrade(grade));
	}

}
