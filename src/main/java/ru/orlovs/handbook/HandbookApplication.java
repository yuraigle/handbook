package ru.orlovs.handbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HandbookApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(HandbookApplication.class, args);
    }

}
