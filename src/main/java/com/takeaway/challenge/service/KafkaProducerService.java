package com.takeaway.challenge.service;

import java.time.LocalDateTime;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.takeaway.challenge.constants.AppConstants;
import com.takeaway.challenge.vo.EmployeeEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Naveen Kumashi
 */

@Slf4j
@Service
public class KafkaProducerService {        
    private KafkaTemplate<String, String> kafkaTemplate;
 
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
    	this.kafkaTemplate = kafkaTemplate;
    }
    
    public void sendMessage(String message) {
    	EmployeeEvent event = new EmployeeEvent();
    	event.setEventName(message);
    	event.setEventTimestamp(LocalDateTime.now());
        this.kafkaTemplate.send(AppConstants.TOPIC_NAME, message);
        log.info(String.format("Message sent -> %s", event));
    }
}
