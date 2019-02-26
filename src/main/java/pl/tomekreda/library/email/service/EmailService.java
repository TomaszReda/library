package pl.tomekreda.library.email.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.tomekreda.library.email.sender.EmailSender;
import pl.tomekreda.library.model.email.EmailTemplate;
import pl.tomekreda.library.model.email.EmailTemplateType;
import pl.tomekreda.library.repository.EmailTemplateRepository;
import pl.tomekreda.library.utils.EmailUtils;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
@Slf4j
public class EmailService {

    @Value("${spring.application.url}")
    String applicationUrl;

    private final EmailTemplateRepository emailTemplateRepository;

    private final EmailSender emailSender;

    @Qualifier("emailTemplateEngine")
    @Autowired
    private TemplateEngine stringTemplateEngine;

    public void sendEmailaResetPassword(String to, UUID token) {
        try {
            String head = null;
            String footer = null;
            EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.RESET_PASSWORD_MESSAGE).orElse(null);
            EmailTemplate headerTemplate = emailTemplateRepository.findById(EmailTemplateType.HEADER).orElse(null);
            EmailTemplate footerTemplate = emailTemplateRepository.findById(EmailTemplateType.FOOTER).orElse(null);

            if (headerTemplate != null)
                head = headerTemplate.getContent();
            if (footerTemplate != null) {
                footer = footerTemplate.getContent();
            }

            Context context = new Context();
            String url = applicationUrl + "/reset/password/" + token;
            context.setVariable("passwordResetUrl", url);
            if (emailTemplate != null) {
                String body = stringTemplateEngine.process(head + emailTemplate.getContent() + footer, context);
                emailSender.sendEmail(to, EmailUtils.RESET_DATA_MESSAGE_TITTLE, body);
            }
        } catch (Exception ex) {
            log.error("[Email Service]=Error sendEmailaResetPassword");
        }
    }


    public void sendEmailNewPassword(String to, String newPassword) {
        try {
            EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.RESET_PASSWORD_NEW_PASSWORD).orElse(null);
            EmailTemplate headerTemplate = emailTemplateRepository.findById(EmailTemplateType.HEADER).orElse(null);
            EmailTemplate footerTemplate = emailTemplateRepository.findById(EmailTemplateType.FOOTER).orElse(null);
            String footer = null;
            String head = null;

            if (footerTemplate != null) {
                footer = footerTemplate.getContent();
            }
            if (headerTemplate != null)
                head = headerTemplate.getContent();


            Context context = new Context();
            context.setVariable("newPassword", newPassword);
            if (emailTemplate != null) {
                String body = stringTemplateEngine.process(head + emailTemplate.getContent() + footer, context);
                emailSender.sendEmail(to, EmailUtils.RESET_DATA_NEW_DATA, body);
            }
        } catch (Exception ex) {
            log.error("[Email Service]=Error sendEmailNewPassword");
        }
    }


    public void sendRegisterEmailToCasualUser(String to, UUID token) {
        try {

            EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.REGISTER_CASUAL_USER).orElse(null);
            EmailTemplate headerTemplate = emailTemplateRepository.findById(EmailTemplateType.HEADER).orElse(null);
            EmailTemplate footerTemplate = emailTemplateRepository.findById(EmailTemplateType.FOOTER).orElse(null);
            String footer = null;
            String head = null;

            if (footerTemplate != null) {
                footer = footerTemplate.getContent();
            }
            if (headerTemplate != null)
                head = headerTemplate.getContent();

            Context context = new Context();
            context.setVariable("activationUrl", applicationUrl + "/user/activation/" + token);
            if (emailTemplate != null) {

                String body = stringTemplateEngine.process(head + emailTemplate.getContent() + footer, context);
                emailSender.sendEmail(to, EmailUtils.REGISTER_CASUAL_USER, body);
            }
        } catch (Exception ex) {
            log.error("[Email Service]=Error sendRegisterEmailToCasualUser");
        }
    }


    public void sendRegisterEmailToLibraryOwner(String to, UUID token) {
        try {

            EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.REGISTER_LIBRARY_OWNER).orElse(null);
            EmailTemplate headerTemplate = emailTemplateRepository.findById(EmailTemplateType.HEADER).orElse(null);
            EmailTemplate footerTemplate = emailTemplateRepository.findById(EmailTemplateType.FOOTER).orElse(null);
            String footer = null;
            String head = null;

            if (footerTemplate != null) {
                footer = footerTemplate.getContent();
            }
            if (headerTemplate != null)
                head = headerTemplate.getContent();
            Context context = new Context();
            context.setVariable("activationUrl", applicationUrl + "/user/activation/" + token);
            if (emailTemplate != null) {

                String body = stringTemplateEngine.process(head + emailTemplate.getContent() + footer, context);
                emailSender.sendEmail(to, EmailUtils.REGISTER_LIBRARY_OWNER, body);
            }
        } catch (Exception ex) {
            log.error("[Email Service]=Error sendRegisterEmailToCasualUser");
        }
    }
}


