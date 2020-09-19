package ru.orlovs.handbook.security;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.orlovs.handbook.domain.Account;
import ru.orlovs.handbook.domain.AccountRepo;

import java.time.ZonedDateTime;
import java.util.Collections;

@Log4j2
@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest req) {
        boolean exists = accountRepo.findByUsername(req.getUsername()).isPresent();
        if (exists) {
            throw new AuthenticationServiceException("Username already exists");
        }

        Account account = new Account();
        account.setUsername(req.getUsername());
        account.setPassword(passwordEncoder.encode(req.getPassword()));
        account.setName(req.getName());
        account.setCreatedAt(ZonedDateTime.now());
        account.setRole("member");
        accountRepo.save(account);
    }

    public LoginResponse login(LoginRequest req) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        String token = jwtTokenProvider.createToken(authenticate);
        return new LoginResponse(token);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepo.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return new User(account.getUsername(), account.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(account.getRole()))
        );
    }
}
