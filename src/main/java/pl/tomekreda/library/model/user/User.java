package pl.tomekreda.library.model.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue
    private UUID IDDD;

    private String firstnameasdasdadada;


}