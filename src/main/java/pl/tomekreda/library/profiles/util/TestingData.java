package pl.tomekreda.library.profiles.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tomekreda.library.model.book.BookCategory;
import pl.tomekreda.library.model.email.EmailTemplate;
import pl.tomekreda.library.model.email.EmailTemplateType;
import pl.tomekreda.library.repository.BookCategoryRepository;
import pl.tomekreda.library.repository.EmailTemplateRepository;

import javax.transaction.Transactional;
import java.io.InputStream;

@Component
@Transactional
@Slf4j
public class TestingData {

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;


    public void createBookCategory() {
        BookCategory bookCat = null;
        for (String bookCategory : TestingProfilesUtils.bookCategories) {
            if (bookCategoryRepository.findFirstByCategoryType(bookCategory) == null) {
                bookCat = new BookCategory(bookCategory);
                bookCategoryRepository.save(bookCat);
            }
        }
    }
    public void createTemplate() {
        int lenght;
        try {
            byte[] bytes;
            InputStream template;

            if (emailTemplateRepository.findFirstByEmailTemplateType(EmailTemplateType.RESET_PASSWORD_MESSAGE) == null) {
                bytes = new byte[100000];
                template = this.getClass().getClassLoader().getResourceAsStream("templates/mail/resetPasswordMessage.html");
                lenght = template.read(bytes);
                String headerString = new String(bytes).substring(0, lenght).trim();
                emailTemplateRepository.save(new EmailTemplate(headerString, "Opis", EmailTemplateType.RESET_PASSWORD_MESSAGE));
            }
            if (emailTemplateRepository.findFirstByEmailTemplateType(EmailTemplateType.RESET_PASSWORD_NEW_PASSWORD) == null) {
                bytes = new byte[100000];
                template = this.getClass().getClassLoader().getResourceAsStream("templates/mail/resetPasswordNewPassword.html");
                lenght = template.read(bytes);
                String headerString = new String(bytes).substring(0, lenght).trim();
                emailTemplateRepository.save(new EmailTemplate(headerString, "Opis", EmailTemplateType.RESET_PASSWORD_NEW_PASSWORD));
            }
            if (emailTemplateRepository.findFirstByEmailTemplateType(EmailTemplateType.REGISTER_CASUAL_USER) == null) {
                bytes = new byte[100000];
                template = this.getClass().getClassLoader().getResourceAsStream("templates/mail/registrationCasualUser.html");
                lenght = template.read(bytes);
                String headerString = new String(bytes).substring(0, lenght).trim();
                emailTemplateRepository.save(new EmailTemplate(headerString, "Opis", EmailTemplateType.REGISTER_CASUAL_USER));
            }
            if (emailTemplateRepository.findFirstByEmailTemplateType(EmailTemplateType.REGISTER_LIBRARY_OWNER) == null) {
                bytes = new byte[100000];
                template = this.getClass().getClassLoader().getResourceAsStream("templates/mail/registrationLibraryOwnerUser.html");
                lenght = template.read(bytes);
                String headerString = new String(bytes).substring(0, lenght).trim();
                emailTemplateRepository.save(new EmailTemplate(headerString, "Opis", EmailTemplateType.REGISTER_LIBRARY_OWNER));
            }
            if (emailTemplateRepository.findFirstByEmailTemplateType(EmailTemplateType.HEADER) == null) {
                bytes = new byte[100000];
                template = this.getClass().getClassLoader().getResourceAsStream("templates/mail/header.html");
                lenght = template.read(bytes);
                String headerString = new String(bytes).substring(0, lenght).trim();
                emailTemplateRepository.save(new EmailTemplate(headerString, "Opis", EmailTemplateType.HEADER));
            }
            if (emailTemplateRepository.findFirstByEmailTemplateType(EmailTemplateType.FOOTER) == null) {
                bytes = new byte[100000];
                template = this.getClass().getClassLoader().getResourceAsStream("templates/mail/footer.html");
                lenght = template.read(bytes);
                String headerString = new String(bytes).substring(0, lenght).trim();
                emailTemplateRepository.save(new EmailTemplate(headerString, "Opis", EmailTemplateType.FOOTER));
            }
        } catch (Exception e) {
            log.error("Exception in loading emails to database");
            log.error(e.getMessage());
        }
    }
}
