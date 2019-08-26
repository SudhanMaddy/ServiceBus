package com.webapps.practices.servicebus_publisher.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/publisher")
public class PublisherController {
	
	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	@PostMapping("{operation}/input")
	public void getInputs(@PathVariable String operation, @RequestBody List<Long> inputList) throws JsonProcessingException {
		Map<String,Object> temp = new HashMap<>();
		temp.put("operation", operation);
		temp.put("input", inputList);
		String input = new ObjectMapper().writeValueAsString(temp);
//		Mono<Long> result = WebClient.create("http://localhost:8090/")
//			.post()
//			.uri("/subscriber/" + operation + "/output")
//			.accept(MediaType.APPLICATION_JSON)
//			.syncBody(inputList)
//			.retrieve()
//			.bodyToMono(Long.class)
//			.map(value -> value)
//			.log();
//		return result;
		kafkaTemplate.send("servicebustest", "producer");
	}
}
