package com.shaunthomas999.kafka.producer;

import com.shaunthomas999.kafka.model.Greeting;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class ProducerRestController {

  private final KafkaProducer kafkaProducer;
  private final KafkaProperties kafkaProperties;

  @PostMapping("/message")
  public ResponseEntity<Void> postMessage(@RequestBody Greeting greeting) {
    log.info("Received message: {}", greeting);

    kafkaProducer.sendMessage(kafkaProperties.getTopic().getName(), greeting);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
