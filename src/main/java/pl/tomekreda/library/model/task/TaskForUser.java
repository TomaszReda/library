package pl.tomekreda.library.model.task;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
public class TaskForUser extends Task {

    @Enumerated(value = EnumType.STRING)
    private TaskForUserType taskForUserType;
}
