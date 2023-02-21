public class MailSender {
    public static String enviarEmail(String strSenderEmailID,
        String strSenderPassword,
        String strEmailSMTPserver, 
        String strEmailServerPort, 
        String lstReceiverEmailID, 
        String strEmailSubject, 
        String strEmailBody, 
        String strEncode) 
    {
        String respuesta = "default";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp","C:\\emailsica\\SendEmail.jar","SendEmail", strSenderEmailID
	,strSenderPassword
	,strEmailSMTPserver
	,strEmailServerPort
	,lstReceiverEmailID
	,strEmailSubject
	,strEmailBody);

            Process proceso = processBuilder.start();
            int codigoSalida = proceso.waitFor();
            if (codigoSalida == 0) {
                respuesta = "El proceso se realizo correctamemte.";
            } else {
                respuesta = "No se pudo realizar el proceso: " +codigoSalida;
            }
            proceso.destroy();
        } catch (Exception e) {
            e.printStackTrace(); 
            respuesta =  e.getMessage();
        }
        return respuesta;
    }
};  