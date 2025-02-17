package com.golem.lab;

//import com.golem.lab.files.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
//@EnableConfigurationProperties(StorageProperties.class)
public class IsLab14Application {

	public static void main(String[] args) {
		SpringApplication.run(IsLab14Application.class, args);
	}

}
