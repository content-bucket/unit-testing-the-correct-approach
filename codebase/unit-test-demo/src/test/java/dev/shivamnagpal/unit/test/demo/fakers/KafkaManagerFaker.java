package dev.shivamnagpal.unit.test.demo.fakers;

import dev.shivamnagpal.unit.test.demo.dtos.core.JsonSerializable;
import dev.shivamnagpal.unit.test.demo.managers.KafkaManager;

import java.util.ArrayList;
import java.util.List;

public class KafkaManagerFaker implements KafkaManager {
    private final List<KafkaRecord> sentKafkaRecords = new ArrayList<>();

    @Override
    public void sendSingleMessage(String topic, JsonSerializable message) {
        sentKafkaRecords.add(new KafkaRecord(topic, message));
    }

    public List<KafkaRecord> getSentKafkaRecords() {
        return sentKafkaRecords;
    }

    public record KafkaRecord(String topic, JsonSerializable message) {
    }
}
