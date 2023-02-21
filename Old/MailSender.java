import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;

public class MailSender {
    private String senderEmailID = "";
    private String senderPassword = "";
    private String emailSMTPserver = "";
    private String emailServerPort = "";
    private boolean todoOK=true;
    private String delimitador =";";
    InternetAddress[] receiverEmailIDs = null;
    private String receiverEmailID = "";
    private String decode ="text/html; charset=utf-8";
    String emailSubject = null;
    String emailBody = null;
    
    public MailSender(String senderEmailID, String senderPassword, String emailSMTPserver, String emailServerPort){
        this.senderEmailID = senderEmailID;
        this.senderPassword = senderPassword;
        this.emailSMTPserver = emailSMTPserver;
        this.emailServerPort = emailServerPort;
        if(senderEmailID.equals("") || senderPassword.equals("") || emailSMTPserver.equals("")||emailServerPort.equals("")){
            this.todoOK = false;
        }
    }
    public MailSender(){
        this.todoOK = false;
    }
    
     private class SMTPAuthenticator extends javax.mail.Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(senderEmailID, senderPassword);
        }
    }
    public String enviar(String strReceiverEmailID, String emailSubject, String emailBody, String decode)
    {   
        if(!decode.equals("")){
            this.decode = decode;
        }
        return enviar(strReceiverEmailID, emailSubject, emailBody);
    }
    
    
    public String enviar(String strReceiverEmailID, String emailSubject, String emailBody)
    {
        if(!todoOK){
            return "Falta Configuracion";
        }
        if(decode.equals("")){
            decode = "text/html; charset=utf-8";
        }
        String[] lstReceiverEmailID= strReceiverEmailID.split(delimitador);
        InternetAddress[] temp = new InternetAddress[lstReceiverEmailID.length];
        for(int i=0;i<lstReceiverEmailID.length;i++){
            try {
                temp[i]= new InternetAddress(lstReceiverEmailID[i]);
            } catch (AddressException ex) {
                Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.receiverEmailIDs= temp;
        this.emailSubject=emailSubject;
        this.emailBody=emailBody;

        Properties props = new Properties();        
        props.put("mail.smtp.user", senderEmailID);
        props.put("mail.smtp.host", emailSMTPserver);
        props.put("mail.smtp.port", emailServerPort);
        //props.put("mail.transport.protocol", "smtp");        
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required ", "true");        
        //props.put("mail.smtp.debug", "true");
        //props.put("mail.smtp.socketFactory.port", emailServerPort);
        props.put("mail.smtp.auth", "true");        
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        //props.put("mail.imap.ssl.checkserveridentity", "false");       
        //props.put("mail.smtp.ssl.trust", "*");        
        //props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");        
        //props.put("mail.smtp.socketFactory.fallback", "false");
        //props.put("mail.smtp.starttls.enable", "false");
        //props.put("mail.session.mail.smtp.auth.plain.disable", "true");                
        //SecurityManager security = System.getSecurityManager();
        
        try
        {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            //preparando el mensaje
            MimeMessage msg = new MimeMessage(session);
            //Titulo
            msg.setSubject(emailSubject);
            //Cuerpo y Formato
            msg.setContent(emailBody, this.decode);
            msg.setFrom(new InternetAddress(senderEmailID));
            msg.addRecipients(Message.RecipientType.TO, receiverEmailIDs);
            //msg.addRecipient(Message.RecipientType.TO, new InternetAddress(strReceiverEmailID));
            
            Transport.send(msg);
            return "Exito";
        }
        catch (Exception mex)
        {
            mex.printStackTrace();
            return mex.toString();
        }
    }
    //tiene que ser un metodo static para poder usarlo desde pl/sql
    public static String enviarEmail(String strSenderEmailID,
                                String strSenderPassword,
                                String strEmailSMTPserver, 
                                String strEmailServerPort, 
                                String lstReceiverEmailID, 
                                String strEmailSubject, 
                                String strEmailBody, 
                                String strEncode) {
      //se crea el objeto
      MailSender objMailSender = new MailSender(strSenderEmailID, strSenderPassword, strEmailSMTPserver, strEmailServerPort);
      //se usa el objeto
      return objMailSender.enviar(lstReceiverEmailID, strEmailSubject, strEmailBody);
    }
}; 
