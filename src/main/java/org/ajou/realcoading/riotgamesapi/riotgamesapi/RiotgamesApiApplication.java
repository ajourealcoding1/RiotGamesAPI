package org.ajou.realcoading.riotgamesapi.riotgamesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RiotgamesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiotgamesApiApplication.class, args);
    }

}
