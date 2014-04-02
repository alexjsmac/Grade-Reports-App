package ca.uwo.csd.cs2212.team10;

import org.junit.Test;
import org.junit.Before;
import static junit.framework.Assert.*;
import java.text.DecimalFormat;

public class TestCommonFunctions {
	
	private Double grade;
	private CommonFunctions common;
	
	
	@Before
	public void setup(){
		grade = -1.0;
	}
	
	@Test
	public void testFormatGradeNoGrade(){
		assertEquals("--.--%", common.formatGrade(grade));
	}
	
	@Test
	public void testFormatGrade(){
		grade = 78.5;
		assertEquals("78.5%",common.formatGrade(grade));
	}

}
