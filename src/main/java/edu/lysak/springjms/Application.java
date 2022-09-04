package edu.lysak.springjms;

import edu.lysak.springjms.service.Sender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//		Sender sender = context.getBean(Sender.class);
//		sender.sendMessage("order-queue", "Hello!");
    }

}
