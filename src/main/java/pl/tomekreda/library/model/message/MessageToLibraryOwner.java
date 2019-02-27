package pl.tomekreda.library.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.task.TaskForLibrary;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class MessageToLibraryOwner extends Message {

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Library library;

    @OneToOne(cascade = CascadeType.ALL)
    private TaskForLibrary taskForLibrary;

    public MessageToLibraryOwner(String content, String title, Library library, TaskForLibrary taskForLibrary,MessageDisplay messageDisplay) {
        super(content, title,messageDisplay);
        this.library = library;
        this.taskForLibrary = taskForLibrary;
    }

    public MessageToLibraryOwner(String content, String title, Library library,MessageDisplay messageDisplay) {
        super(content, title,messageDisplay);
        this.library = library;
    }

    public MessageToLibraryOwner() {
    }

    @Override
    public String toString() {
        return "MessageToLibraryOwner{" +
                '}';
    }
}
