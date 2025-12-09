package com.qbatz.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/smartstay/payment")
@CrossOrigin("*")
public class UIController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
