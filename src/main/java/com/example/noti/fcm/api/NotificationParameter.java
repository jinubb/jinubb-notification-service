package com.example.noti.fcm.api;

import lombok.ToString;

@ToString
public enum NotificationParameter {
	SOUND("default"),
	COLOR("#FFFF00");

	private String value;
	
	private NotificationParameter(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
