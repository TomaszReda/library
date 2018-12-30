package pl.tomekreda.library.model.email;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class EmailTemplate {

    @Enumerated(EnumType.STRING)
    @Id
    EmailTemplateType emailTemplateType;

    @Column(length = 1000)
    private String content;

    private String description;


    public EmailTemplate(String content, String description, EmailTemplateType emailTemplateType) {
        this.content = content;
        this.description = description;
        this.emailTemplateType = emailTemplateType;
    }

}
