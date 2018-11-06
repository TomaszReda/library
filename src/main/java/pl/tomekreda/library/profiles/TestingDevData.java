package pl.tomekreda.library.profiles;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.UserRepository;

import java.util.UUID;

@Component
@TestProfile
@DevProfile
public class TestingDevData implements CommandLineRunner {

    @Autowired
    private  UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {

        User owner = new User( "tomek", "reda", "tomekreda@op.pl", "123456789", "test", null, null);
        userRepository.save(owner);

        User casual = new User( "kasia", "reda", "kasiareda@op.pl", "987654321", "test", null, null);
        userRepository.save(casual);
    }
}
