package com.lynn.reverbtime.RoomComponent;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
Table configuration class. Adds items to room component table.
*/

@Configuration
public class rcConfig {
    @Bean
    public CommandLineRunner demo(rcRepository repository) {
        return (args) -> {
            // save a few roomComponents
            repository.save(new RoomComponent("Concrete", 0.7));
            repository.save(new RoomComponent("Wood", 0.4));
            repository.save(new RoomComponent("Drywall", 0.5));
        };
    }
}