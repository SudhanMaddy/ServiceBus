package com.webapps.practices.servicebus_subscriber.controller;

import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriber")
public class SubscriberController {
	
	@Autowired private JdbcTemplate jdbcTemplate;
	
	@PostMapping("{operation}/output")
	public Long processInputs(@PathVariable String operation , @RequestBody List<Long> inputList) {
		Long result;
		switch(operation) {
		case "sum":
			result = inputList.stream().mapToLong(Long::longValue).sum();
			break;
		case "avg":
			result = (long)inputList.stream().mapToLong(Long::longValue).average().getAsDouble();
			break;
		default:
			result = 0L;
			break;
		}
		String insertQuery = "Insert INTO ServiceBus(input,operation,output)Values(?,?,?)";
		String input = String.join(",", inputList.stream().map(String::valueOf).collect(Collectors.toList()));
        Object[] params = new Object[] { input,operation,result };
        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
		jdbcTemplate.update(insertQuery, params, types);
		return result;
	}

}
