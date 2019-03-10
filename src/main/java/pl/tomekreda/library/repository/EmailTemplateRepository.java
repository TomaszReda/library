package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tomekreda.library.model.email.EmailTemplate;
import pl.tomekreda.library.model.email.EmailTemplateType;

@RepositoryRestResource
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, EmailTemplateType> {

    EmailTemplate findFirstByEmailTemplateType(EmailTemplateType emailTemplateType);

}
