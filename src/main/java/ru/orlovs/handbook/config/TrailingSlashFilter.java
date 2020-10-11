package ru.orlovs.handbook.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

// если фронт отдельно на nginx-е, удалить
@Component
public class TrailingSlashFilter extends OncePerRequestFilter {

    // js|css|html|ico|svg|ttf|woff2|whatever
    private final Pattern staticFileRx = Pattern
            .compile("^.*\\.[a-z]{2,4}[0-9]?$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse resp,
            FilterChain chain
    ) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (!uri.endsWith("/")
                && !uri.contains("/api/")
                && !staticFileRx.matcher(uri).matches()
        ) {
            ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(req);
            builder.replacePath(String.format("%s/", builder.build().getPath()));
            resp.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
            resp.setHeader(HttpHeaders.LOCATION, builder.toUriString());
        } else {
            chain.doFilter(req, resp);
        }
    }
}
