package ru.orlovs.handbook.security;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.orlovs.handbook.domain.Account;
import ru.orlovs.handbook.domain.AccountRepo;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;

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

        AccountDetails user = loadUserByUsername(req.getUsername());
        user.setPassword(null);

        return new LoginResponse(token, user);
    }

    @Override
    public AccountDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account u = accountRepo.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new AccountDetails(u.getId(), u.getUsername(), u.getPassword(), u.getRole());
    }

    @PostConstruct
    public void seedUsers() {
        if (accountRepo.count() == 0) {
            RegisterRequest req = new RegisterRequest();
            req.setUsername("orlov");
            req.setPassword("asdasd");
            req.setName("Yuri Orlov");
            register(req);
        }

        log.info("1st user created. orlov : asdasd");
    }
}
