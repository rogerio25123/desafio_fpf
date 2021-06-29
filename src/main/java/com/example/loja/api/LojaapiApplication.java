package com.example.loja.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.loja.api.config.property.LojaApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(LojaApiProperty.class)
public class LojaapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaapiApplication.class, args);
	}

}
