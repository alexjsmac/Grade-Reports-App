package ca.uwo.csd.cs2212.team10;

import java.io.*;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.xml.*;

public class ReportGenerator {

    private final static String REPORT_FILENAME = "grade_report.jrxml";

    /* Attributes */
    JasperReport report;

    public ReportGenerator() {
        try { 
            InputStream reportStream = AppLauncher.class.getClassLoader().getResourceAsStream(REPORT_FILENAME);
            JasperDesign jasperDesign = JRXmlLoader.load(reportStream);
            report = JasperCompileManager.compileReport(jasperDesign);
        } catch (Exception e){ }
    }
    
    private JasperPrint fillReport(Course course, Student student) throws JRException {
        Collection<JavaBean> beans = new ArrayList<JavaBean>();
        List<Deliverable> deliverables = course.getDeliverableList();
        
        for (Deliverable d : deliverables)
            beans.add(new JavaBean(d.getName(), student.getGrade(d), course.calcAverage(d)));
        
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beans);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("courseTitle", course.getTitle());
        parameters.put("courseCode", course.getCode());
        parameters.put("courseTerm", course.getTerm());
        parameters.put("firstName", student.getFirstName());
        parameters.put("lastName", student.getLastName());
        parameters.put("email", student.getEmail());
        parameters.put("number", student.getNum());
        parameters.put("average", student.calcAverage());
        parameters.put("asnAverage", student.calcAverage(0));
        parameters.put("examAverage", student.calcAverage(1));

        return JasperFillManager.fillReport(report, parameters, beanColDataSource);
    }

    public void exportPDF(String directory, Course course, List<Student> students) throws JRException {
        for (Student s : students){
            JasperExportManager.exportReportToPdfFile(fillReport(course, s), directory + s.getLastName() + "-" + s.getFirstName() + "-" + s.getNum() + ".pdf");
        }
    }

    public void sendReportByEmail(String smtpServer, String username, String password, String fromAddress,
            List<Student> students) {
        // For Marcus to do
    }     
}
