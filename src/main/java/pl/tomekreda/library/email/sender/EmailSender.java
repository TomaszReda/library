package pl.tomekreda.library.email.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.Executors;

@Service
public class EmailSender {

    @Qualifier("getJavaMailSender")
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String emailAddressFrom;

    public void sendEmail(String to, String title, String content) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail,true);
            helper.setTo(to);
            helper.setFrom(emailAddressFrom);
            helper.setSubject(title);
            helper.setText(content,true);
        } catch (MessagingException e) {
            //TODO What to do if there was an error in creating email
        }
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                javaMailSender.send(mail);
            }
        });
    }
}