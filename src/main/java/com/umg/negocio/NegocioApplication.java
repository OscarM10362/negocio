package com.umg.negocio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.umg.negocio", "com.umg.persistencia"})
@ComponentScan(basePackages = {"com.umg.negocio", "com.umg.persistencia"}) 
@EnableJpaRepositories(basePackages = "com.umg.persistencia.repository")
@EntityScan(basePackages = "com.umg.persistencia.entidades")
public class NegocioApplication {

	public static void main(String[] args) {
		SpringApplication.run(NegocioApplication.class, args);
	}

}
