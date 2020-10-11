package ru.orlovs.handbook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true);
    }

    // если фронт отдельно на nginx-е, удалить
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // all requests -> SPA
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/webapp-dist/")
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new SpaResourceResolver());
    }

}
