package it.twinsbrains.poc.testcontainers;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@Component
@SuppressWarnings("unused")
public class Receiver {

    private AtomicReference<CountDownLatch> latch = new AtomicReference<>(new CountDownLatch(1));

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.get().countDown();
    }


    public CountDownLatch getLatch() {
        return latch.get();
    }

    public void reset() {
        latch.set(new CountDownLatch(1));
    }

}
