package com.kisnahc.usedmarket.usedmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityListeners;

@EnableJpaAuditing
@SpringBootApplication
public class UsedMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsedMarketApplication.class, args);
	}

}
