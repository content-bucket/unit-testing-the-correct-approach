package dev.shivamnagpal.unit.test.demo.fakers;

import dev.shivamnagpal.unit.test.demo.dtos.core.JsonSerializable;
import dev.shivamnagpal.unit.test.demo.managers.KafkaManager;

public class KafkaManagerFaker implements KafkaManager {
    @Override
    public void sendSingleMessage(String topic, JsonSerializable message) {

    }
}
