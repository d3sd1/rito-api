/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaTestListener {

    @KafkaListener(topics = "testtopic", groupId = "${message.group.name:profegroup}")
    public void listenTopic1(String message) {
        System.out.println("Recieved Message of test in  listener: " + message);
    }
}
