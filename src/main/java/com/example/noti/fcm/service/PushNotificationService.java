package com.example.noti.fcm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.noti.fcm.api.FCMBackend;
import com.example.noti.fcm.model.PushNotificationRequest;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PushNotificationService {

	@Autowired
    private FCMBackend fcmService;

    public String sendPushNotification(PushNotificationRequest request) throws 
    InterruptedException, ExecutionException {
    	return fcmService.sendMessage(request.getCustomData(), request);
    }

    public String sendPushNotificationWithoutData(PushNotificationRequest request) 
    		throws InterruptedException, ExecutionException {
    	return fcmService.sendMessageWithoutData(request);
    }

    public String sendPushNotificationToToken(PushNotificationRequest request) 
    		throws InterruptedException, ExecutionException {
    	return fcmService.sendMessageToToken(request);
    }
    public String sendPushNotificationToTokenWithData(PushNotificationRequest request, Map<String, String> data) 
    		throws InterruptedException, ExecutionException {
    	return fcmService.sendMessageToTokenWithData(request, data);
    }
}
