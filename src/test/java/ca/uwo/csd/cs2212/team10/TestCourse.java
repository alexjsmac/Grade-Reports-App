package ca.uwo.csd.cs2212.team10;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.Before;

public class TestCourse{
    private Course course;
    
    @Before
    public void setup(){
        this.course = new Course("foo", "bar", "daz");
    }
    
    @Test
    public void testConstructorSetsAttributes(){
        course = new Course("foo", "bar", "daz");
        
        Assert.assertEquals("foo", course.getTitle());
        Assert.assertEquals("bar", course.getCode());
        Assert.assertEquals("daz", course.getTerm());
    }
    
    @Test
    public void testSetTitle(){
        course.setTitle("title");
        Assert.assertEquals("title", course.getTitle());
    }
    
    @Test
    public void testSetCode(){
        course.setCode("code");
        Assert.assertEquals("code", course.getCode());
    }
    
    @Test
    public void testSetTerm(){
        course.setTerm("term");
        Assert.assertEquals("term", course.getTerm());
    }
}