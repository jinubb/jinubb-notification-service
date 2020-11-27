package com.example.noti.fcm.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.ResponseContainer;
import com.example.noti.fcm.model.PushNotificationRequest;
import com.example.noti.fcm.service.PushNotificationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PushNotificationController {

    private PushNotificationService notificationService;
    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.notificationService = pushNotificationService;
    }

    @PostMapping("/notification/topic")
    public ResponseContainer<String> sendNotification(@RequestBody PushNotificationRequest request) {
    	ResponseContainer<String> response = ResponseContainer.emptyResponse();
    	try {
        	notificationService.sendPushNotificationWithoutData(request);
        	response.setSuccess(true);
    	} catch(Exception e) {
    		log.error("sendNotification: {}", e);
    		response.setError(e);
    	}
        return response;
    }

    @PostMapping("/notification/token")
    public ResponseContainer<String> sendNotificationToToken(@RequestBody PushNotificationRequest request) {
    	ResponseContainer<String> response = ResponseContainer.emptyResponse();
    	try {
        	notificationService.sendPushNotificationToToken(request);
        	response.setSuccess(true);
    	} catch(Exception e) {
    		log.error("sendNotificationToToken: {}", e);
    		response.setError(e);
    	}
        return response;
    }

    @PostMapping("/notification/data")
    public ResponseContainer<String> sendDataNotification(@RequestBody PushNotificationRequest request) {
    	ResponseContainer<String> response = ResponseContainer.emptyResponse();
    	try {
        	notificationService.sendPushNotification(request);
    	} catch(Exception e) {
    		log.error("sendDataNotification: {}", e);
    		response.setError(e);
    	}
        return response;
    }
}
