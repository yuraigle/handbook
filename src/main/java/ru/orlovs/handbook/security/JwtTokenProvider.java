package ru.orlovs.handbook.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(Authentication authentication) {
        AccountDetails account = (AccountDetails) authentication.getPrincipal();
        String subj = account.getId() + "|" + account.getUsername() + "|" +
                account.getPassword() + "|" + account.getRole();
        Date now = new Date();
        Date till = Date.from(now.toInstant().plusMillis(validityInMilliseconds));

        return Jwts.builder()
                .setSubject(subj)
                .setIssuedAt(now)
                .setExpiration(till)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Expired or invalid JWT token");
            return false;
        }
    }

    public Authentication getAuthenticationFromToken(String token) {
        String subj = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        String[] u = subj.split("\\|");

        AccountDetails account = new AccountDetails(Long.parseLong(u[0]), u[1], u[2], u[3]);
        Set<GrantedAuthority> roles = Collections.singleton(new SimpleGrantedAuthority(u[3]));
        return new UsernamePasswordAuthenticationToken(account, u[1], roles);
    }
}
