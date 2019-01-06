package pl.tomekreda.library.model.task;

import lombok.Data;
import pl.tomekreda.library.model.library.Library;

import javax.persistence.*;

@Entity
@Data
public class TaskForLibrary extends Task {

    @Enumerated(value = EnumType.STRING)
    private TaskForLibraryType taskForLibraryType;




}
