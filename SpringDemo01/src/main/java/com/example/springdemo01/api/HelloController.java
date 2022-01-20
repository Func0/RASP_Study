package com.example.springdemo01.api;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @RequestMapping("/")
    public String index(){
        return "hellohellohellohello";
    }
    @RequestMapping("/hello")
    public String hello(){
        System.out.println();
        return "hello world.";
            }
  @GetMapping("/greeting")
    public String greeting(){
        return "greeting";
    }
    @PostMapping("/post")
    public String hello01(@RequestParam("name") String name,@RequestParam("age") int age){

        return "name:"+name+age;
    }

}
