package pl.tomekreda.library.model.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@DiscriminatorColumn(name = "MESSAGE_TYPE",discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Message {


    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 400)
    private String content;

    private String title;

    @Enumerated(value = EnumType.STRING)
    private MessageDisplay messageDisplay;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateRead;

    public Message(String content, String title,MessageDisplay messageDisplay) {
        this.content = content;
        this.title = title;
        this.messageDisplay=messageDisplay;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", dataCreate=" + dataCreate +
                ", dateRead=" + dateRead +
                '}';
    }
}
