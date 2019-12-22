/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaTestController {
	@Autowired
	KafkaMessageProducer kafkaMessageProducer;

	@PostMapping("/add/{topic}")
	public void addIdCustomer(@PathVariable String topic, @RequestBody String body) {
		kafkaMessageProducer.sendMessage(topic, body);
	}
}
