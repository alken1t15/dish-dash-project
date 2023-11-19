package com.example.dishdash.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartPage {
    @GetMapping("/")
    public String getCartPage(){
        return "cart";
    }
}
