package co.za.carrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.addListeners(event -> {
            if (event.toString().contains("Started")) {
                System.out.println("Application has started successfully!");
            }
        });
        app.run(args);
    }
}
