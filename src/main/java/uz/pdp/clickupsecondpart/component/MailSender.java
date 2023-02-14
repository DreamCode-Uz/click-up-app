package uz.pdp.clickupsecondpart.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

    private final JavaMailSender mailSender;

    @Autowired
    public MailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendMail(String subject, String body, String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (MailException e) {
            return false;
        }
        return true;
    }
}
