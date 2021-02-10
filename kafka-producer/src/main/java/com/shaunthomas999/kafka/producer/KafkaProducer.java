package com.shaunthomas999.kafka.producer;

import com.shaunthomas999.kafka.model.Greeting;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaProducer {

  private final KafkaTemplate<String, Greeting> kafkaTemplate;

  public void sendMessage(String topicName, Greeting greeting) {
    kafkaTemplate.send(topicName, greeting);
  }
}
