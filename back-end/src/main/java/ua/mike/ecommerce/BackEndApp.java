package ua.mike.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BackEndApp {

    public static void main(String[] args) {
        SpringApplication.run(BackEndApp.class, args);
    }

}
