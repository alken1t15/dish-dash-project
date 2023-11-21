package com.example.dishdash.controller;

import com.example.dishdash.entity.Users;
import com.example.dishdash.service.ServiceUsers;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class CreateOrderPage {
    private final ServiceUsers serviceUsers;
    @GetMapping("/")
    public String getOrderPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = serviceUsers.findByEmail(username);

        model.addAttribute("carts",user.getCarts());
        return "createOrderPage";
    }
}
