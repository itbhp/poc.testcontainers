package it.twinsbrains.poc.testcontainers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static it.twinsbrains.poc.testcontainers.MessagingRabbitmqApplication.topicExchangeName;

@Component
public class Runner implements CommandLineRunner {

  private final RabbitTemplate rabbitTemplate;
  private final Receiver receiver;

  public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
    this.receiver = receiver;
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Sending message...");
    rabbitTemplate.convertAndSend(topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
    boolean messageReceivedInTime = receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    if (!messageReceivedInTime) {
      System.out.println("message not received in time");
    }
  }
}
