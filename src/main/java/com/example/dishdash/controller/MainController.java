package com.example.dishdash.controller;

import com.example.dishdash.entity.Users;
import com.example.dishdash.repository.RepositoryUsers;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MainController {
    private final PasswordEncoder passwordEncoder;
    private final RepositoryUsers repositoryUsers;
    @GetMapping("/login")
    public String loginPage(){
        return "signIn";
    }

    @GetMapping("/registration")
    public String registrationPage(){
        return "signUp";
    }

    @PostMapping("/registration")
    public String registrationAction(@RequestParam("contact") String contact, @RequestParam("phone") String phone, @RequestParam("email") String email,
                                     @RequestParam("country") String country, @RequestParam("city")  String city, @RequestParam("password") String password,
                                     @RequestParam("confirm") String confirm, Model model){
        if (StringUtils.isBlank(contact)){
            return "signUp";
        }else if(StringUtils.isBlank(contact)){
            return "signUp";
        }
        Users users = new Users();
        users.setContact(contact);
        users.setPhone(Long.parseLong(phone));
        users.setEmail(email);
        users.setCountry(country);
         users.setCity(city);
         users.setPassword(passwordEncoder.encode(password));
         repositoryUsers.save(users);
        return "redirect:/login";
    }
}