package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
public class DemoController {

    @RequestMapping(path ="/home", method = {RequestMethod.GET, RequestMethod.POST})
            // consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String sayHello(){
        return "Hello World";
    }

}
