package com.webapps.practices.servicebus_subscriber;

import java.util.List;

import lombok.Data;

@Data
public class Temp {
	
	String operation;
	List<Long> inputList;

}
