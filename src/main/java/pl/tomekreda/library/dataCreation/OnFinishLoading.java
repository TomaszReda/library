package pl.tomekreda.library.dataCreation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.model.user.UserRoleEnum;
import pl.tomekreda.library.model.user.UserRoles;
import pl.tomekreda.library.repository.UserRepository;

@Component
public class OnFinishLoading implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        User owner=new User("Tomek", "Reda", "owner@local", 123456789, passwordEncoder.encode("password"),null,null);
        UserRoles userOwnerRole=new UserRoles();
        userOwnerRole.setUserRole(UserRoleEnum.LIBRARY_OWNER);
        owner.getUserRoles().add(userOwnerRole);
        userRepository.save(owner);


        User casual=new User("Kasia", "Reda", "worker@local", 123456789, passwordEncoder.encode("password"),null,null);
        UserRoles userCasualRole=new UserRoles();
        userCasualRole.setUserRole(UserRoleEnum.LIBRARY_OWNER);
        owner.getUserRoles().add(userCasualRole);
        userRepository.save(casual);
    }
}

