package com.takeaway.challenge.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.takeaway.challenge.constants.AppConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Naveen Kumashi
 */

@Slf4j
@Service
public class KafKaProducerService {        
    private KafkaTemplate<String, String> kafkaTemplate;
 
    public KafKaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
    	this.kafkaTemplate = kafkaTemplate;
    }
    
    public void sendMessage(String message) {        
        this.kafkaTemplate.send(AppConstants.TOPIC_NAME, message);
        log.info(String.format("Message sent -> %s", message));
    }
}
