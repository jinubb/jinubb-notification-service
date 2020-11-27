package com.example.noti.fcm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;

@SpringBootApplication(scanBasePackages={"com.example"})
public class KeyshareNotiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeyshareNotiApplication.class, args);
	}

}
