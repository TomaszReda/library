package pl.tomekreda.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pl.tomekreda.library.model.user.AuthenticationResponse;
import pl.tomekreda.library.model.user.Credentials;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.AddUserCasualRequest;
import pl.tomekreda.library.request.AddUserLibraryOwnerRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse login(@RequestBody Credentials credentials, HttpServletRequest request) {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());

        final Authentication authentication = this.authenticationManager.authenticate(token);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        final HttpSession httpSession = request.getSession(true);

        httpSession.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return new AuthenticationResponse(authentication.getName(), httpSession.getId());

    }


    public ResponseEntity registerCasualUser(AddUserCasualRequest user) {
        try {
            User tmp = new User();
            tmp.setEmail(user.getEmail());
            tmp.setFirstname(user.getFirstname());
            tmp.setLastname(user.getLastname());
            tmp.setPhoneNumber(user.getPhoneNumber());
            tmp.setPassword(passwordEncoder.encode(user.getPassword()));
            tmp.setUserCasual(null);
            tmp.setUserMenager(null);
            userRepository.save(tmp);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity registerOwnerUser(AddUserLibraryOwnerRequest user) {
        try {
            User tmp = new User();
            tmp.setPhoneNumber(user.getPhoneNumber());
            tmp.setEmail(user.getEmail());
            tmp.setFirstname(user.getFirstname());
            tmp.setLastname(user.getLastname());
            tmp.setPassword(passwordEncoder.encode(user.getPassword()));
            tmp.setUserCasual(null);
            tmp.setUserMenager(null);
            userRepository.save(tmp);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
