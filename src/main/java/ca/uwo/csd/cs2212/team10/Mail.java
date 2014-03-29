package ca.uwo.csd.cs2212.team10;

import java.io.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.InputStream;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import net.sf.jasperreports.engine.*;

/**
 * Mail class used to send email containing PDF report to students
 * @author team 10
 */
public class Mail {
    
  public Mail(Student recipient)throws Exception{
        
    Properties properties = loadProperties();
    Session session = getSession(properties);
    sendMessage(session, properties, recipient);
  }
 
  private static Session getSession(final Properties properties) {
 
    Session session = Session.getInstance(properties,
        new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                
                String username = properties.getProperty("smtp.username");
                String password = properties.getProperty("smtp.password");
                return new PasswordAuthentication(username, password);
            }
        }
    );
 
    return session;
  }
 
  private static void sendMessage(Session session, Properties properties, Student recipient) throws Exception {
    
    Message msg = new MimeMessage(session);
    String senderName = properties.getProperty("sender.name");
    String senderEmail = properties.getProperty("sender.email");
 
    Address sender = new InternetAddress(senderEmail, senderName);
    msg.setFrom(sender);
 
    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient.getEmail()));
 
    msg.setSubject(properties.getProperty("message.subject"));
    
    Multipart multiPart = new MimeMultipart();
    
    MimeBodyPart textPart = new MimeBodyPart();
    textPart.setText(loadTemplate("email.text.vm", recipient.getFirstName(), senderName), "utf -8");
    
    MimeBodyPart htmlPart = new MimeBodyPart();
    htmlPart.setContent(loadTemplate("email.html.vm", recipient.getFirstName(), senderName), "text/html; charset =utf -8");
    
    MimeBodyPart fileAttachmentPart = new MimeBodyPart();
  
    OutputStream output = new ByteArrayOutputStream();
    JasperPrint printer = JasperFillManager.fillReport();
    JasperExportManager.exportReportToPdfStream(printer, output);
    DataSource source = new ByteArrayDataSource(((ByteArrayOutputStream)output).toByteArray()"application/pdf");
    
    fileAttachmentPart.setDataHandler(new DataHandler(source));
    fileAttachmentPart.setFileName("Grade_Report.pdf");
            
    multiPart.addBodyPart(textPart);
    multiPart.addBodyPart(htmlPart);
    multiPart.addBodyPart(fileAttachmentPart);
    
    msg.setContent(multiPart);
    
    Transport.send(msg);
  }
 
  private static Properties loadProperties() throws Exception {
      Properties properties = new Properties();
      InputStream stream = Mail.class.getClassLoader().getResourceAsStream("mail.properties");
      properties.load(stream);
      
      return properties;
  }
  
  private static String loadTemplate(String filename, String recipientName, String senderName) {
      
      VelocityEngine ve = new VelocityEngine();
      ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
      ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
              
      Template template = ve.getTemplate(filename);
      
      VelocityContext context = new VelocityContext();
      context.put("studentName", recipientName);
      context.put("profName", senderName);
      
      StringWriter out = new StringWriter();
      template.merge(context, out);
      
      return out.toString();
  }
}
  
