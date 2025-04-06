package dev.shivamnagpal.unit.test.demo.configurations;

import dev.shivamnagpal.unit.test.demo.dtos.core.JsonSerializable;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import dev.shivamnagpal.unit.test.demo.constants.Constants;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    @Bean(Constants.JSON_SERIALIZABLE_PRODUCER_FACTORY)
    public ProducerFactory<String, JsonSerializable> jsonSerializableInternalProducerFactory(
            KafkaProperties kafkaProperties
    ) {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(Constants.JSON_SERIALIZABLE_KAFKA_TEMPLATE)
    public KafkaTemplate<String, JsonSerializable> jsonSerializableInternalKafkaTemplate(
            @Qualifier(Constants.JSON_SERIALIZABLE_PRODUCER_FACTORY) ProducerFactory<String, JsonSerializable> internalClusterProducerFactory
    ) {
        return new KafkaTemplate<>(internalClusterProducerFactory);
    }

}
