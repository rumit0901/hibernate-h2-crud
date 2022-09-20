package com.in28minutes.springboot.jpa.hibernate.h2.example.student.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @PostMapping({"","/"})
    public Map<String, Object> test(@RequestBody @Base64Encoded String content) {
        return Map.of("success", true, "cotent", content);
    }
}
