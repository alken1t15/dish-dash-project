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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public String registrationPage(Model model){
        HashMap<String,String> errors = new HashMap<>();
        model.addAttribute("errors",errors);
        return "signUp";
    }

    @PostMapping("/registration")
    public String registrationAction(@RequestParam("contact") String contact, @RequestParam("phone") String phone, @RequestParam("email") String email,
                                     @RequestParam("country") String country, @RequestParam("city")  String city, @RequestParam("password") String password,
                                     @RequestParam("confirm") String confirm, Model model){
        HashMap<String,String> errors = new HashMap<>();
        if (StringUtils.isBlank(contact)){
            errors.put("contact","Значение не может быть пустым");
        }
        if(StringUtils.isBlank(email)){
            errors.put("email","Значение не может быть пустым");
        }
        if(StringUtils.isBlank(country)){
            errors.put("country","Значение не может быть пустым");
        }
        if(StringUtils.isBlank(city)){
            errors.put("city","Значение не может быть пустым");
        }

        if(StringUtils.isBlank(phone)){
            errors.put("phone","Значение не может быть пустым");
        }else if(phone.length()!=11){
            errors.put("phone","Номер был введен не правильно");
        }

        if (StringUtils.isBlank(password)){
            errors.put("password","Значение не может быть пустым");
        }
        if (StringUtils.isBlank(confirm)){
            errors.put("confirm","Значение не может быть пустым");
        }
        if (!password.equals(confirm)){
            errors.put("confirm","Значение должны совпадать");
        }


        if (!errors.isEmpty()){
            model.addAttribute("errors",errors);
            return "signUp";
        }
        Users users = new Users(contact,Long.parseLong(phone),email,country,city,passwordEncoder.encode(password));
        repositoryUsers.save(users);
        return "redirect:/login";
    }
}