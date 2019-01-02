package pl.tomekreda.library.profiles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
@ProdProfile
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdProfilesData implements CommandLineRunner {

    private final BookCategoryRepository bookCategoryRepository;

    private final EmailTemplateRepository emailTemplateRepository;


    @Override
    public void run(String... args) throws Exception {
        this.createBookCategory();

    }

    private void createBookCategory() {
        BookCategory bookCategory = null;
        if (bookCategoryRepository.findFirstByCategoryType("Fantasy").equals(null)) {
            bookCategory = new BookCategory("Fantasy");
            bookCategoryRepository.save(bookCategory);
        }

        if (bookCategoryRepository.findFirstByCategoryType("Biografie/Autobiografie").equals(null)) {
            bookCategory = new BookCategory("Biografie/Autobiografie");
            bookCategoryRepository.save(bookCategory);
        }
        if (bookCategoryRepository.findFirstByCategoryType("Młodzieżowa").equals(null)) {
            bookCategory = new BookCategory("Młodzieżowa");
            bookCategoryRepository.save(bookCategory);
        }
        if (bookCategoryRepository.findFirstByCategoryType("Naukowa").equals(null)) {
            bookCategory = new BookCategory("Naukowa");
            bookCategoryRepository.save(bookCategory);
        }
        if (bookCategoryRepository.findFirstByCategoryType("Sportowa").equals(null)) {
            bookCategory = new BookCategory("Sportowa");
            bookCategoryRepository.save(bookCategory);
        }
        if (bookCategoryRepository.findFirstByCategoryType("Bajka").equals(null)) {
            bookCategory = new BookCategory("Bajka");
            bookCategoryRepository.save(bookCategory);
        }
        if (bookCategoryRepository.findFirstByCategoryType("Historyczna").equals(null)) {
            bookCategory = new BookCategory("Historyczna");
            bookCategoryRepository.save(bookCategory);
        }
        if (bookCategoryRepository.findFirstByCategoryType("Horror").equals(null)) {
            bookCategory = new BookCategory("Horror");
            bookCategoryRepository.save(bookCategory);
        }
        if (bookCategoryRepository.findFirstByCategoryType("Przygodowa").equals(null)) {
            bookCategory = new BookCategory("Przygodowa");
            bookCategoryRepository.save(bookCategory);
        }
        if (bookCategoryRepository.findFirstByCategoryType("Inna").equals(null)) {
            bookCategory = new BookCategory("Inna");
            bookCategoryRepository.save(bookCategory);
        }
    }


    private void CreateTemplate() {
//        EmailTemplate emailTemplate = new EmailTemplate();
//        emailTemplate.setContent();
//        emailTemplate.setDescription("Wysyłanie zapytania o zmiane hasła");
//        emailTemplate.setEmailTemplateType(EmailTemplateType.RESET_PASSWORD_MESSAGE);
//        emailTemplateRepository.save(emailTemplate);

        int lenght;
        try {
            byte bytes[];
            InputStream template;
            String toSave;

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

        } catch (Exception e) {
            log.error("Exception in loading emails to database");
            log.error(e.getMessage());
        }
    }
}
