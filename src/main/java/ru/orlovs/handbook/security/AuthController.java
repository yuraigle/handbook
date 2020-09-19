package ru.orlovs.handbook.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    public String register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req);
        return "OK";
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @GetMapping("/me")
    public UserDetails me() {
        Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authenticate.getPrincipal();
    }
}
