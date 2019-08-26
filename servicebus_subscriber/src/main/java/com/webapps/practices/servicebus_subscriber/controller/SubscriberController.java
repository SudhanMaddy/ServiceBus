package com.webapps.practices.servicebus_subscriber.controller;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriber")
public class SubscriberController {
	
	@Autowired private JdbcTemplate jdbcTemplate;
	
	@PostMapping("{operation}/output")
	@KafkaListener(topics="servicebustest")
	public void processInputs(@RequestParam String temp) {
//		Long result;
//		switch(temp.getOperation()) {
//		case "sum":
//			result = temp.getInputList().stream().mapToLong(Long::longValue).sum();
//			break;
//		case "avg":
//			result = (long)temp.getInputList().stream().mapToLong(Long::longValue).average().getAsDouble();
//			break;
//		default:
//			result = 0L;
//			break;
//		}
		String insertQuery = "Insert INTO ServiceBus(input,operation,output)Values(?,?,?)";
//		String input = String.join(",", temp.getInputList().stream().map(String::valueOf).collect(Collectors.toList()));
        Object[] params = new Object[] { "5,2",temp,7};
        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
		jdbcTemplate.update(insertQuery, params, types);
	}

}
