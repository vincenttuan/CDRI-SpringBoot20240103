package com.example.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {
	
	@GetMapping(value = "/investor")
    public String investor() {
        return "investor";
    }
    
}
