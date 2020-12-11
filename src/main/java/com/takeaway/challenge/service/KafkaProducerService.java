package com.takeaway.challenge.service;

import java.time.LocalDateTime;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.takeaway.challenge.constants.AppConstants;
import com.takeaway.challenge.vo.EmployeeEventVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Naveen Kumashi
 */

@Slf4j
@Service
public class KafkaProducerService {        
    private KafkaTemplate<String, Object> kafkaTemplate;
 
    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
    	this.kafkaTemplate = kafkaTemplate;
    }
    
    public void sendMessage(String data) {
    	EmployeeEventVO event = new EmployeeEventVO();
    	event.setEventName(data);
    	event.setEventTimestamp(LocalDateTime.now().toString());
    	
        this.kafkaTemplate.send(AppConstants.TOPIC_NAME, event);
        log.info(String.format("Message sent -> %s", event));
    }
}
