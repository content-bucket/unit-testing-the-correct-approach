package dev.shivamnagpal.unit.test.demo.managers.impl;

import dev.shivamnagpal.unit.test.demo.constants.Constants;
import dev.shivamnagpal.unit.test.demo.dtos.core.JsonSerializable;
import dev.shivamnagpal.unit.test.demo.managers.KafkaManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaManagerImpl implements KafkaManager {
    private final KafkaTemplate<String, JsonSerializable> jsonKafkaTemplate;

    public KafkaManagerImpl(
            @Qualifier(Constants.JSON_SERIALIZABLE_KAFKA_TEMPLATE) KafkaTemplate<String, JsonSerializable> jsonKafkaTemplate
    ) {
        this.jsonKafkaTemplate = jsonKafkaTemplate;
    }

    @Override
    public void sendSingleMessage(String topic, JsonSerializable message) {
        jsonKafkaTemplate.send(topic, message);
        jsonKafkaTemplate.flush();
    }
}
