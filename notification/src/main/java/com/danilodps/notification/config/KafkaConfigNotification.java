package com.danilodps.notification.config;

import com.danilodps.notification.record.DepositResponse;
import com.danilodps.notification.record.SigninResponse;
import com.danilodps.notification.record.SignupResponse;
import com.danilodps.notification.record.TransferResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfigNotification {

    private final KafkaProperties kafkaProperties;

    public static final String DEPOSIT_NOTIFICATION_TOPIC = "deposit.created.notification";
    public static final String TRANSFER_NOTIFICATION_TOPIC = "transfer.created.notification";
    public static final String SIGN_UP = "signup.notification";
    public static final String SIGN_IN = "signin.notification";

    public KafkaConfigNotification(KafkaProperties kafkaProperties) { this.kafkaProperties = kafkaProperties; }

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    }

    @Bean
    ConsumerFactory<String, DepositResponse> depositResponseConsumerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrap().servers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-deposit-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, DepositResponse.class.getName());
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, DepositResponse> depositResponseConcurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, DepositResponse> depositResponseConsumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, DepositResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(depositResponseConsumerFactory);

        return factory;
    }

    @Bean
    ConsumerFactory<String, TransferResponse> transferResponseConsumerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrap().servers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-transfer-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, TransferResponse.class.getName());
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, TransferResponse> transferResponseConcurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, TransferResponse> transferResponseConsumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, TransferResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(transferResponseConsumerFactory);

        return factory;
    }

    @Bean
    ConsumerFactory<String, SignupResponse> signUpResponseConsumerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrap().servers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-signup-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, SignupResponse.class.getName());
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, SignupResponse> signUpResponseConcurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, SignupResponse> signUpResponseConsumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, SignupResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(signUpResponseConsumerFactory);

        return factory;
    }

    @Bean
    ConsumerFactory<String, SigninResponse> signInResponseConsumerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrap().servers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-signin-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, SigninResponse.class.getName());
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, SigninResponse> signInResponseConcurrentKafkaListenerContainerFactory(
            ConsumerFactory<String, SigninResponse> signinResponseConsumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, SigninResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(signinResponseConsumerFactory);

        return factory;
    }

}
