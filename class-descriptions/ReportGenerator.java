package ca.uwo.csd.cs2212.team10;

import java.io.IOException;

/**
 * Class used to generate and send PDF reports
 * 
 * @author Team 10
 */
public class ReportGenerator{

	/**
	 * Generates a PDF report for a student in a specified course. Throws an IOException if the file could not be written.
	 * @param student The requested student
	 * @param course The course the student is in
	 * @param fileName The filename
	 */
	public static void generatePDFReport(Student student, Course course, String fileName) throws IOException
	{
	}
	
	/**
	 * Sends emails containing PDF reports for one or more students
	 * @param students An array of requested students
	 * @param course The course the students are in
	 * @param emails An array of the email addresses to send to
	 */
	public static void sendEmailReports(Student[] students, Course course, String[] emails)
	{
	}
}
