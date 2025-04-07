package dev.shivamnagpal.unit.test.demo.managers;

import dev.shivamnagpal.unit.test.demo.dtos.core.JsonSerializable;

public interface KafkaManager {
    void sendSingleMessage(String topic, JsonSerializable message);
}
