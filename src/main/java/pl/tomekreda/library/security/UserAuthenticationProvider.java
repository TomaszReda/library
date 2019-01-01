package pl.tomekreda.library.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.model.user.UserRoles;
import pl.tomekreda.library.model.user.UserState;
import pl.tomekreda.library.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //Get User to take roles
        User user = userService.getUserByEmail(authentication.getPrincipal().toString());
        if (user == null) {
            log.debug("Authentication Failed: User with this email does not exist.");
            throw new IllegalArgumentException("Niepoprawny email lub hasło");
        }

        String password = authentication.getCredentials().toString();
        if (passwordEncoder.matches(password,user.getPassword())) {
            if (user.getUserState().equals(UserState.ACTIVE)) {
                List<GrantedAuthority> userRoleEnums = new ArrayList<>();
                for (UserRoles userRoles : user.getUserRoles())
                    userRoleEnums.add(new SimpleGrantedAuthority("ROLE_" + userRoles.getUserRole().toString()));

                // use the credentials
                // and authenticate against the third-party system
                return new UsernamePasswordAuthenticationToken(
                        user.getEmail(), password, userRoleEnums);
            } else if (user.getUserState().equals(UserState.BLOCKED)) {
                log.debug("Authentication Failed: User is suspended");
                throw new IllegalArgumentException("Konto zostało zablokowane!");
            } else {
                log.debug("Authentication Failed: User is not active");
                throw new IllegalArgumentException("Konto nie zostało jeszcze aktywowane");
            }

        } else {
            log.debug("Authentication Failed: Bad password");
            throw new IllegalArgumentException("Niepoprawny email lub hasło");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}