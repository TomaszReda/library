package pl.tomekreda.library.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.model.user.AuthenticationResponse;
import pl.tomekreda.library.model.user.Credentials;
import pl.tomekreda.library.request.AddUserCasualRequest;
import pl.tomekreda.library.request.AddUserLibraryOwnerRequest;
import pl.tomekreda.library.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthService authService;

    @GetMapping(value = "/resource")
    public Map<String, String> getResource() {
        Map<String, String> resource = new HashMap<>();
        resource.put("resource", "here is some resource");
        return resource;
    }

    @GetMapping(value = "/logout")
    public ResponseEntity logout(HttpSession session) {

        session.invalidate();
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/login")
    public AuthenticationResponse login(@RequestBody Credentials credentials, HttpServletRequest request) {
        return authService.login(credentials, request);
    }


    @PostMapping(value = "/registerOwner")
    public ResponseEntity registerOwner(@RequestBody AddUserLibraryOwnerRequest user) {
        return authService.registerOwnerUser(user);
    }


    @PostMapping(value = "/registerCasual")
    public ResponseEntity registerCasual(@RequestBody AddUserCasualRequest user) {
        return authService.registerCasualUser(user);
    }


    // Adnotacje do zabezpieczenia endpointów na podstawie ról
//    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
//    @PreAuthorize("hasAuthority('ROLE_CASUAL_USER')")

}
