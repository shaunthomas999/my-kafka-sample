package com.shaunthomas999.kafka.streams;

import com.shaunthomas999.kafka.model.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {

  private final String bootstrapAddress;
  private final String applicationId;
  private final String topicName;

  public KafkaStreamsConfig(@Value("${kafka.bootstrapAddress}") final String bootstrapAddress,
                            @Value("${kafka.stream.applicationId}") final String applicationId,
                            @Value("${kafka.topic.name}") final String topicName) {
    this.bootstrapAddress = bootstrapAddress;
    this.applicationId = applicationId;
    this.topicName = topicName;
  }

  @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
  public KafkaStreamsConfiguration kStreamsConfigs() {
    log.info("Applying config");

    Map<String, Object> props = new HashMap<>();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

    return new KafkaStreamsConfiguration(props);
  }

  @Bean
  public KStream<String, Greeting> kStream(StreamsBuilder kStreamBuilder) {
    log.info("Starting kStream");

    JsonSerde<Greeting> greetingSerde = new JsonSerde<>(Greeting.class);
    KStream<String, Greeting> greetingStream = kStreamBuilder.stream(topicName, Consumed.with(Serdes.String(), greetingSerde));

    greetingStream
        .filter( (k, v) -> !v.getName().isEmpty())
        .mapValues( v -> v.getMsg().concat(" >>"))
//        .to("greeting-topic-processed");
        .foreach((k,v) -> log.info("received {}", v));

    return greetingStream;
  }
}
