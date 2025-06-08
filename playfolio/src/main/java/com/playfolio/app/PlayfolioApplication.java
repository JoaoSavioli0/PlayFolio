package com.playfolio.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class PlayfolioApplication {

	public static void main(String[] args) {
		        Dotenv dotenv = Dotenv.configure()
                              .filename(".env.dev")
                              .ignoreIfMissing()
                              .load();

        dotenv.entries().forEach(entry ->
            System.setProperty(entry.getKey(), entry.getValue())
        );

		SpringApplication.run(PlayfolioApplication.class, args);
	}

}
