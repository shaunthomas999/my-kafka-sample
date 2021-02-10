package com.shaunthomas999.kafka.producer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

  private String bootstrapAddress;
  private Topic topic;

  @Data
  public static class Topic {
    private String name;
  }
}
