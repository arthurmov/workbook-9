package com.pluralsight.webdemo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    // this method will respond to http://localhost:8080/
    @RequestMapping(path="/", method= RequestMethod.GET)
    public String index() {
        return "<h1>Hello World!</h1> <p>We hope you are having a great day!</p>";
    }
}
