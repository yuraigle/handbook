package ru.orlovs.handbook.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * all requests -> index.html
 */

@Component
public class SpaResourceResolver extends PathResourceResolver {

    @Override
    protected Resource getResource(String resourcePath, Resource location) throws IOException {
        Resource resource = location.createRelative(resourcePath);
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            return new ClassPathResource("webapp-dist/index.html");
        }
    }
}
