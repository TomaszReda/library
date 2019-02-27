package pl.tomekreda.library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.model.user.UserRoles;
import pl.tomekreda.library.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserLoginService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)   {
        User user = userRepository.findUserByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRoles userRoles : user.getUserRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userRoles.getUserRole().toString()));
        }
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        authorities);
        return userDetails;
    }


}
