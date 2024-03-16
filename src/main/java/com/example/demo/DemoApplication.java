package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;


@SpringBootApplication
@EnableCaching
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// @Autowired
	// private CacheManager cacheManager;

	// @EventListener
	// public void onApplicationEvent(ApplicationReadyEvent event) {
	// 	cacheManager.getCache("youstream_cache").clear();
	// }

}
