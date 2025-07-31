package co.za.carrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.addListeners((ApplicationListener<ApplicationStartedEvent>) event -> {
            System.out.println("Application has started successfully!");
        });
        app.run(args);
    }
}