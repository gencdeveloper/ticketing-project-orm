package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketingProjectOrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketingProjectOrmApplication.class, args);
	}

	@Bean
	public ModelMapper mapper(){ //if you added 3.part pack to SpringBoot as an external and you want to reach out its object,
								//you have to implement with @Bean anation then you can use it whenever you want!
		return new ModelMapper();
	}
}
