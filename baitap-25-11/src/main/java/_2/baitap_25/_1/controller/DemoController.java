package _2.baitap_25._1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, anyone can access this endpoint!";
    }

    @GetMapping("/secured")
    public String secured() {
        return "This is a secured endpoint, authentication required!";
    }
}