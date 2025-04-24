package io.github.joaoVitorLeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer { // Extensão para transformar a nossa aplicação de Standalone para uma aplicação Web
    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}

