import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class SendEmail {

    public static void main(String[] args)
	{
		String resp  			  = "";
		String strSenderEmailID   = args[0];
		String strSenderPassword  = args[1]; 
		String strEmailSMTPserver = args[2]; 
		String strEmailServerPort = args[3]; 
		String lstReceiverEmailID = args[4]; 
		String strEmailSubject    = args[5]; 
		String strEmailBody 	  = args[6]; 
		String decode ="text/html; charset=utf-8";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", strEmailSMTPserver);
		props.put("mail.smtp.port", strEmailServerPort);
		props.put("mail.smtp.socketFactory.port", strEmailServerPort);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		try {
			// Crear una nueva sesión de correo electrónico
			Session session = Session.getInstance(props, null);
			// Crear un nuevo mensaje de correo electrónico
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(strSenderEmailID));
			message.addRecipients(Message.RecipientType.TO, lstReceiverEmailID);
			message.setSubject(strEmailSubject);
			message.setContent(strEmailBody, decode);
			
			// Enviar el mensaje de correo electrónico
			Transport transport = session.getTransport("smtp");
			transport.connect(strSenderEmailID, strSenderPassword);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close(); 
			
			resp = "Mensaje enviado correctamente";
		} catch (AddressException e) {
			e.printStackTrace();
			resp = e.getMessage();
		} catch (MessagingException e) {
			e.printStackTrace();
			resp = e.getMessage();
		}
		savelog("[ Send To: " + lstReceiverEmailID + " - " + Calendar.getInstance().getTime() +" ]: " + resp); 
	}

    public static void savelog(String respuesta) {
        try {
            FileWriter writer = new FileWriter("C:\\emailsica\\log.txt", true);
            writer.write(respuesta +" \n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}