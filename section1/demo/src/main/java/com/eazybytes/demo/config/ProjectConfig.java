package com.eazybytes.demo.config;

import com.eazybytes.demo.beans.Vehicle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    Vehicle vehicle() {
        var veh = new Vehicle();
        veh.setName("Tesla");
        return veh;
    }

    @Bean
    String hello() {
        return "Hello World";
    }

    @Bean
    Integer luckyNumber() {
        return 16;
    }
}
