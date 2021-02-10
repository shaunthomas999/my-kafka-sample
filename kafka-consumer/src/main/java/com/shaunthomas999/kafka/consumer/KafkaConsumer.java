package com.shaunthomas999.kafka.consumer;

import com.shaunthomas999.kafka.model.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

  @KafkaListener(
    topics = "${kafka.topic.name}",
    groupId = "${kafka.group.id}",
    containerFactory = "kafkaListenerContainerFactory"
  )
  public void listen(Greeting greeting) {
    log.info("Received Messasge: " + greeting);
  }
}
