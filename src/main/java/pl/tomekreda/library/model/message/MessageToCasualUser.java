package pl.tomekreda.library.model.message;

import lombok.Data;
import pl.tomekreda.library.model.task.TaskForUser;
import pl.tomekreda.library.model.user.User;

import javax.persistence.*;

@Entity
@Data
public class MessageToCasualUser extends Message {

    @ManyToOne(cascade = CascadeType.ALL)
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
}
