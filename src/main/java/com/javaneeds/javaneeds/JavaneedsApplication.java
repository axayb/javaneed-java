package com.javaneeds.javaneeds;

import com.javaneeds.javaneeds.pojo.FileStorageProp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProp.class
})
public class JavaneedsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(JavaneedsApplication.class, args);
	}

}
