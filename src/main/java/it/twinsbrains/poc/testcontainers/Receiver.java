package it.twinsbrains.poc.testcontainers;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@SuppressWarnings("unused")
public class Receiver {

    private final CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }


    public CountDownLatch getLatch() {
        return latch;
    }

}
