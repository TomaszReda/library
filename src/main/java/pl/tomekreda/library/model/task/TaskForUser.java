package pl.tomekreda.library.model.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.message.MessageToCasualUser;
import pl.tomekreda.library.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Getter
@Setter
public class TaskForUser extends Task {

    @Enumerated(value = EnumType.STRING)
    private TaskForUserType taskForUserType;

    @OneToOne(mappedBy = "taskForUser", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MessageToCasualUser messageToCasualUser;

    public TaskForUser(User user,  LocalDateTime dateExpiration, TaskStatus taskStatus, Book book, Library library, TaskForUserType taskForUserType) {
        super(user, dateExpiration, taskStatus, book, library);
        this.taskForUserType = taskForUserType;
    }




    @Override
    public String toString() {
        return "TaskForUser{" +
                super.toString() +
                "taskForUserType=" + taskForUserType +
                ", messageToCasualUser=" + messageToCasualUser +
                '}';
    }
}
