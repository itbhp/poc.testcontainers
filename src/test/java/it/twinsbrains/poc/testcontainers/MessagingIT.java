package it.twinsbrains.poc.testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class MessagingIT {
  @Container
  public static RabbitMQContainer rabbitMQContainer =
      new RabbitMQContainer(
              DockerImageName.parse("rabbitmq").withTag("3.7.25-management-alpine")
      );

  @DynamicPropertySource
  static void neo4jProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.rabbitmq.port", () -> rabbitMQContainer.getMappedPort(5672));
  }

  @Autowired
  private Receiver receiver;

  @Test
  void it_should_work(CapturedOutput output) throws InterruptedException {
    assertThat(receiver.getLatch().await(300, TimeUnit.MILLISECONDS), is(true));
    assertThat(output.getAll(), containsString("Hello from RabbitMQ!"));
  }
}
