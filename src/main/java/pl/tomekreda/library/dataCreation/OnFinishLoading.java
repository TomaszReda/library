package pl.tomekreda.library.dataCreation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.UserRepository;

@Component
public class OnFinishLoading implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        userRepository.save(new User("Tomek", "Reda", "tomekreda12@op.pl", 123456789, passwordEncoder.encode("password"),null,null));
    }
}

