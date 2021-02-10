package com.shaunthomas999.kafka.consumer;

import com.shaunthomas999.kafka.model.Greeting;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  private final String bootstrapAddress;
  private final String groupId;

  public KafkaConsumerConfig(@Value("${kafka.bootstrapAddress}") final String bootstrapAddress,
                             @Value("${kafka.group.id}") final String groupId) {
    this.bootstrapAddress = bootstrapAddress;
    this.groupId = groupId;
  }

  @Bean
  public ConsumerFactory<String, Greeting> consumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Greeting.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Greeting> kafkaListenerContainerFactory() {
    val factory = new ConcurrentKafkaListenerContainerFactory<String, Greeting>();
    factory.setConsumerFactory(consumerFactory());

    //factory.setRecordFilterStrategy(record -> record.value().contains("World"));

    return factory;
  }
}
