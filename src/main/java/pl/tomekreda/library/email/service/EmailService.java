package pl.tomekreda.library.email.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.tomekreda.library.LibraryApplication;
import pl.tomekreda.library.model.email.EmailTemplate;
import pl.tomekreda.library.model.email.EmailTemplateType;
import pl.tomekreda.library.rabbitmq.RabbitMqConfiguration;
import pl.tomekreda.library.repository.EmailTemplateRepository;
import pl.tomekreda.library.utils.EmailUtils;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
@Slf4j
public class EmailService {

    @Value("${spring.application.url}")
    String applicationUrl;

    private final EmailTemplateRepository emailTemplateRepository;

    private final RabbitTemplate rabbitTemplate;

    @Qualifier("emailTemplateEngine")
    @Autowired
    private TemplateEngine stringTemplateEngine;

    private void sendToQueue(String title,String to,String body){
        Map<String,String> emailMap=new HashMap<>();
        emailMap.put("to",to);
        emailMap.put("title",EmailUtils.RESET_DATA_MESSAGE_TITTLE);
        emailMap.put("body",body);
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.SFG_MESSAGE_QUEUE, emailMap);
    }
    public void sendEmailaResetPassword(String to, UUID token) {
        try {
            String head = getHeader();
            String footer = getFooter();
            EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.RESET_PASSWORD_MESSAGE).orElse(null);


            Context context = new Context();
            String url = applicationUrl + "/reset/password/" + token;
            context.setVariable("passwordResetUrl", url);
            if (emailTemplate != null) {
                String body = stringTemplateEngine.process(head + emailTemplate.getContent() + footer, context);
                Map<String,String> emailMap=new HashMap<>();
                sendToQueue(EmailUtils.RESET_DATA_MESSAGE_TITTLE,to,body);
            }
        } catch (Exception ex) {
            log.error("[Email Service]=Error sendEmailaResetPassword");
        }
    }

    public void sendEmailNewPassword(String to, String newPassword) {
        try {
            EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.RESET_PASSWORD_NEW_PASSWORD).orElse(null);
            String head = getHeader();
            String footer = getFooter();
            Context context = new Context();
            context.setVariable("newPassword", newPassword);
            if (emailTemplate != null) {
                String body = stringTemplateEngine.process(head + emailTemplate.getContent() + footer, context);
                sendToQueue(EmailUtils.RESET_DATA_NEW_DATA,to,body);

            }
        } catch (Exception ex) {
            log.error("[Email Service]=Error sendEmailNewPassword");
        }
    }


    public void sendRegisterEmailToCasualUser(String to, UUID token) {
        try {

            EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.REGISTER_CASUAL_USER).orElse(null);

            String footer = getFooter();
            String head = getHeader();
            Context context = new Context();
            context.setVariable("activationUrl", applicationUrl + "/user/activation/" + token);
            if (emailTemplate != null) {

                String body = stringTemplateEngine.process(head + emailTemplate.getContent() + footer, context);
                sendToQueue(EmailUtils.REGISTER_CASUAL_USER,to,body);

            }
        } catch (Exception ex) {
            log.error("[Email Service]=Error sendRegisterEmailToCasualUser");
        }
    }


    public void sendRegisterEmailToLibraryOwner(String to, UUID token) {
        try {
            System.err.println("aaaaaaaaaaaaaaaaa");
            EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.REGISTER_LIBRARY_OWNER).orElse(null);
            String footer = getFooter();
            String head = getHeader();

            Context context = new Context();
            context.setVariable("activationUrl", applicationUrl + "/user/activation/" + token);
            if (emailTemplate != null) {

                String body = stringTemplateEngine.process(head + emailTemplate.getContent() + footer, context);
                sendToQueue(EmailUtils.REGISTER_LIBRARY_OWNER,to,body);

            }
        } catch (Exception ex) {
            log.error("[Email Service]=Error sendRegisterEmailToCasualUser");
        }
    }

    private String getHeader() {
        EmailTemplate headerTemplate = emailTemplateRepository.findById(EmailTemplateType.HEADER).orElse(null);
        if (headerTemplate != null)
            return headerTemplate.getContent();
        else
            return null;
    }

    private String getFooter() {
        EmailTemplate footerTemplate = emailTemplateRepository.findById(EmailTemplateType.FOOTER).orElse(null);
        if (footerTemplate != null)
            return footerTemplate.getContent();
        else
            return null;
    }
}


