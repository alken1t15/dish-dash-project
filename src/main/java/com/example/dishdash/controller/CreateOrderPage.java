package com.example.dishdash.controller;

import com.example.dishdash.entity.Users;
import com.example.dishdash.service.ServiceUsers;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
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
        String username = null;
        if (authentication.isAuthenticated()) {
            username = authentication.getName();
        }
        else {
            WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
            username = webAuthenticationDetails.getRemoteAddress();
        }
        Users user = serviceUsers.findByEmail(username);
        if (user == null){
            serviceUsers.save(new Users(username));
        }

        model.addAttribute("carts",user.getCarts());
        model.addAttribute("user",user);
        return "createOrderPage";
    }
}
