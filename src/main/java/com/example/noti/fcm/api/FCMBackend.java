package com.example.noti.fcm.api;

import com.example.noti.fcm.model.PushNotificationRequest;
import com.google.firebase.messaging.*;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class FCMBackend {

    public String sendMessage(Map<String, String> data, PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageWithData(data, request);
        String messageId = sendAndGetResponse(message);
        log.info("Sent message with data. Topic: {}, response: {}", request.getTopic(), messageId);
        return messageId;
    }

    public String sendMessageWithoutData(PushNotificationRequest request) throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageWithoutData(request);
        String messageId = sendAndGetResponse(message);
        log.info("Sent message without data. Topic: {}, response: {}", request.getTopic(), messageId);
        return messageId;
    }

    public String sendMessageToToken(PushNotificationRequest request)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request);
        String messageId = sendAndGetResponse(message);
        log.info("Sent message to token. Device token: {}, response: {}",request.getToken(), messageId);
        return messageId;
    }
    public String sendMessageToTokenWithData(PushNotificationRequest request, Map<String, String> data)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request, data);
        String messageId = sendAndGetResponse(message);
        log.info("Sent message to token. Device token: {}, response: {}",request.getToken(), messageId);
        return messageId;
    }
    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(
                		AndroidNotification.builder()
                			.setSound(NotificationParameter.SOUND.getValue())
                			.setColor(NotificationParameter.COLOR.getValue())
                			.setTag(topic).build()
                ).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
        		.setAps(
        				Aps.builder()
        				.setCategory(topic)
        				.setThreadId(topic).build()
        		).build();
    }

    private Message getPreconfiguredMessageToToken(PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request)
        		.setToken(request.getToken())
                .build();
    }
    private Message getPreconfiguredMessageToToken(PushNotificationRequest request, Map<String, String> data) {
        return getPreconfiguredMessageBuilder(request).putAllData(data)
        		.setToken(request.getToken())
                .build();
    }
    private Message getPreconfiguredMessageWithoutData(PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request)
        		.setTopic(request.getTopic())
                .build();
    }

    private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request).putAllData(data).setTopic(request.getTopic())
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return Message.builder()
        		.setApnsConfig(apnsConfig)
        		.setAndroidConfig(androidConfig)
                .setNotification(
                		new Notification(
                				request.getTitle(), request.getMessage()
                		)
                );
    }
}
