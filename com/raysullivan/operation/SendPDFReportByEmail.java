package raysullivan.operation;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**	
 * 
 * @author rsullivan
 *
 */
public class SendPDFReportByEmail {
	/**
	 * ByExchange	send using Red Robin exchange account
	 * 
	 * @param from	[user]@redrobin.com
	 * 
	 * @param pass	user's network password
	 * 
	 * @param to	"To" email address
	 * 
	 * @param subject	email subject
	 * 
	 * @param body	email body
	 */
	private final static String keyString = "sendEmailReport@";
	/**
	 * util
	 */
	private static AutomationDriverUtil util = new AutomationDriverUtil();
	public static void ByExchange(String from, String pass, String to, String subject, String body) throws Exception {
        Properties props = System.getProperties();
        String password = AESencrp.decrypt(pass, keyString);
        String host = "smtp.redrobin.com";
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
        	//Set from address
            message.setFrom(new InternetAddress(from));
             message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
           //Set subject
            message.setSubject(subject);
            message.setText(body);
            BodyPart objMessageBodyPart = new MimeBodyPart();
            objMessageBodyPart.setText("Please Find The Attached Report File!");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(objMessageBodyPart);
            objMessageBodyPart = new MimeBodyPart();
            //Set path to the pdf report file
            String filename = util.getTestReportPath()+ "\\"+util.getTestReportName(); 
            //Create data source to attach the file in mail
            DataSource source = new FileDataSource(filename);
            objMessageBodyPart.setDataHandler(new DataHandler(source));
            objMessageBodyPart.setFileName(filename);
            multipart.addBodyPart(objMessageBodyPart);
            message.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
	/**
	 * ByGmail	send using Gmail account
	 * 
	 * @param from	[user]@gmail.com
	 * 
	 * @param pass	gmail password
	 * 
	 * @param to	"To" email address
	 * 
	 * @param subject	email subject
	 * 
	 * @param body	email body
	 * @throws Exception 
	 */
	public static void ByGmail(String from, String pass, String to, String subject, String body) throws Exception {
        Properties props = System.getProperties();
        String password = AESencrp.decrypt(pass, keyString);
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
        	//Set from address
            message.setFrom(new InternetAddress(from));
             message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
           //Set subject
            message.setSubject(subject);
            message.setText(body);
            BodyPart objMessageBodyPart = new MimeBodyPart();
            objMessageBodyPart.setText("Please Find The Attached Report File!");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(objMessageBodyPart);
            objMessageBodyPart = new MimeBodyPart();
            //Set path to the pdf report file
            String filename = util.getTestReportPath()+ "\\"+util.getTestReportName();  
            //Create data source to attach the file in mail
            DataSource source = new FileDataSource(filename);
            objMessageBodyPart.setDataHandler(new DataHandler(source));
            objMessageBodyPart.setFileName(filename);
            multipart.addBodyPart(objMessageBodyPart);
            message.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}