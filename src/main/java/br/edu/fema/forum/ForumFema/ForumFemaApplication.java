package br.edu.fema.forum.ForumFema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
//@ComponentScan({"br.edu.fema.a"})
public class ForumFemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumFemaApplication.class, args);
	}


}
