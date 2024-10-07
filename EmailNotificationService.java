import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailNotificationService {

    private String apiKey = "sua-chave-api";

    public void sendEmail(String to, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.rdstation.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("usuario@exemplo.com", "senha");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("usuario@exemplo.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email enviado com sucesso!");
        } catch (MessagingException e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EmailNotificationService emailService = new EmailNotificationService();
        emailService.sendEmail("cliente@exemplo.com", "Assunto Importante", "Corpo do email.");
    }
}
