package pl.tomekreda.library.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import pl.tomekreda.library.email.sender.EmailSender;
import pl.tomekreda.library.model.email.EmailTemplate;
import pl.tomekreda.library.model.email.EmailTemplateType;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.EmailTemplateRepository;
import pl.tomekreda.library.utils.EmailUtils;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class EmailService {

    @Value("${spring.application.url}")
    String applicationUrl;

    private final EmailTemplateRepository emailTemplateRepository;

    private final EmailSender emailSender;

    @Autowired
    private TemplateEngine stringTemplateEngine;


    public void sendEmailaResetPassword(String to, UUID token) {
        try {

            EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.RESET_PASSWORD_MESSAGE).orElse(null);
            Context context = new Context();
            String url = applicationUrl + "/reset/password/"+token;
            context.setVariable("passwordResetUrl", url);

            String body = stringTemplateEngine.process("resetPasswordMessage", context);
            emailSender.sendEmail(to, EmailUtils.RESET_PASSWORD_MESSAGE_TITTLE, body);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void sendEmailNewPassword(String to,String newPassword) {
        try {

            EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.RESET_PASSWORD_NEW_PASSWORD).orElse(null);
            Context context = new Context();
            context.setVariable("newPassword",newPassword);

            String body = stringTemplateEngine.process("resetPasswordNewPassword", context);
            emailSender.sendEmail(to, EmailUtils.RESET_PASSWORD_NEW_PASSWORD, body);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public void sendRegisterEmailToCasualUser(String to,UUID token) {
        try {

            EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.REGISTER_CASUAL_USER).orElse(null);
            Context context = new Context();
            context.setVariable("activationUrl",applicationUrl+"/user/activation/"+token);

            String body = stringTemplateEngine.process("registrationCasualUser", context);
            emailSender.sendEmail(to, EmailUtils.REGISTER_CASUAL_USER, body);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void sendRegisterEmailToLibraryOwner(String to,UUID token) {
        try {

            EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.REGISTER_LIBRARY_OWNER).orElse(null);
            Context context = new Context();
            context.setVariable("activationUrl",applicationUrl+"/user/activation/"+token);

            String body = stringTemplateEngine.process("registrationLibraryOwnerUser", context);
            emailSender.sendEmail(to, EmailUtils.REGISTER_LIBRARY_OWNER, body);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


