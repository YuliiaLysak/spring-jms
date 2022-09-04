package edu.lysak.springjms.receiver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @JmsListener(destination = "order-queue", containerFactory = "warehouseFactory")
    public void receiveMessage(String message) {
        System.out.println(message);
    }
}
