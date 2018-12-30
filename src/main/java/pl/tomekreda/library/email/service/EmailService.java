package pl.tomekreda.library.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.email.sender.EmailSender;
import pl.tomekreda.library.model.email.EmailTemplate;
import pl.tomekreda.library.model.email.EmailTemplateType;
import pl.tomekreda.library.repository.EmailTemplateRepository;
import pl.tomekreda.library.utils.EmailUtils;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class EmailService {

    private final EmailTemplateRepository emailTemplateRepository;

    private final EmailSender emailSender;

    public void sendEmailaResetPassword(String to) {
        EmailTemplate emailTemplate = emailTemplateRepository.findById(EmailTemplateType.RESET_PASSWORD_MESSAGE).orElse(null);

        emailSender.sendEmail(to, EmailUtils.RESET_PASSWORD_MESSAGE_TITTLE, emailTemplate.getContent());

    }
}
