package com.base.common.controller;

import com.base.common.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@RestController
@Slf4j
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private KafkaTemplate kafkaTemplate;
    private final List<String> CONSUMED_MESSAGES = new ArrayList<>();


    @PostMapping(value = "/sendMessage")
    public String sendMessageToKafkaTopic(@RequestBody JSONObject jsonObject) {
        kafkaService.sendMessage(jsonObject.toString());
        return "OK!";

    }


    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consume(String m) {
        log.info("Message consumed: {}", m);
        CONSUMED_MESSAGES.add(m);
    }

    @GetMapping("/pub/{m}")
    public void produce(@PathVariable String m) {
        log.info("Message produced: {}", m);
        kafkaTemplate.send(TOPIC, m);
    }

    @GetMapping("/get")
    public List<String> get() {
        log.info("Get consumed messages");
        List<String> l = new ArrayList<>(CONSUMED_MESSAGES);
        CONSUMED_MESSAGES.clear();
        return l;
    }

}
