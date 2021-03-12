package com.base.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(String msg) {
        kafkaTemplate.send(TOPIC, msg);
    }
}
