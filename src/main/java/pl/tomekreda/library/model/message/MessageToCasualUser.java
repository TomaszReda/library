package pl.tomekreda.library.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.tomekreda.library.model.task.TaskForUser;
import pl.tomekreda.library.model.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Data
public class MessageToCasualUser extends Message {

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private TaskForUser taskForUser;

    public MessageToCasualUser(String content, String title, User user, TaskForUser taskForUser) {
        super(content, title);
        this.user = user;
        this.taskForUser = taskForUser;
    }

    public MessageToCasualUser() {

    }

    @Override
    public String toString() {
        return "MessageToCasualUser{" +
                '}';
    }
}
