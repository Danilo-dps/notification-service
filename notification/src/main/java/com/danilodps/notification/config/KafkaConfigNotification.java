package com.danilodps.notification.config;

import com.danilodps.commons.application.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.JacksonJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableKafka
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfigNotification {

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    private final KafkaProperties kafkaProperties;

    @Bean("consumerFactory")
    ConsumerFactory<String, Object> consumerFactory(){
        log.info("Criando: consumerFactory");
        Map<String, Object> configConsumerFactory = new HashMap<>();
        configConsumerFactory.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrap().servers());
        configConsumerFactory.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        configConsumerFactory.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configConsumerFactory.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configConsumerFactory);
    }

    @Bean("listenerContainerFactory")
    ConcurrentKafkaListenerContainerFactory<String, Object> listenerContainerFactory(
            @Qualifier("consumerFactory") ConsumerFactory<String, Object> consumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setRecordMessageConverter(new JacksonJsonMessageConverter());
        return factory;
    }

}