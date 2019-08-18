package com.webapps.practices.servicebus_publisher.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/publisher")
public class PublisherController {
	
	@PostMapping("{operation}/input")
	public Mono<Long> getInputs(@PathVariable String operation, @RequestBody List<Long> inputList) {
		Mono<Long> result = WebClient.create("http://localhost:8090/")
			.post()
			.uri("/subscriber/" + operation + "/output")
			.accept(MediaType.APPLICATION_JSON)
			.syncBody(inputList)
			.retrieve()
			.bodyToMono(Long.class)
			.map(value -> value)
			.log();
		return result;
	}
}
