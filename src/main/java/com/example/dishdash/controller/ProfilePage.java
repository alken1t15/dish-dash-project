package com.example.dishdash.controller;

import com.example.dishdash.entity.Users;
import com.example.dishdash.service.ServiceUsers;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfilePage {
    private final ServiceUsers serviceUsers;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/")
    public String getProfilePage(){
        return "profile";
    }

    @GetMapping("/editContact")
    public String getEditContactPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = serviceUsers.findByEmail(username);
        HashMap<String,String> errors = new HashMap<>();
        model.addAttribute("errors",errors);
        model.addAttribute("user",user);
        return "editContactInfo";
    }

    @PostMapping("/edit")
    public String editInformationAboutUser(@RequestParam("contact") String contact, @RequestParam("phone") String phone, @RequestParam("email") String email, Model model){
        HashMap<String,String> errors = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = serviceUsers.findByEmail(authentication.getName());
        if (StringUtils.isBlank(contact)){
            errors.put("contact","Значение не может быть пустым");
        }
        if(StringUtils.isBlank(email)){
            errors.put("email","Значение не может быть пустым");
        }else {
            Users userNew = serviceUsers.findByEmail(email);
            if (userNew != null && user.getId() != userNew.getId()){
                errors.put("email","Такой аккаунт уже есть");
            }
        }

        if(StringUtils.isBlank(phone)){
            errors.put("phone","Значение не может быть пустым");
        }else if(phone.length()!=11){
            errors.put("phone","Номер был введен не правильно");
        }

        if (!errors.isEmpty()){
            model.addAttribute("errors",errors);
            model.addAttribute("user",user);
            return "editContactInfo";
        }

        user.setEmail(email);
        user.setPhone(Long.valueOf(phone));
        user.setContact(contact);
        serviceUsers.save(user);
        return "redirect:/logout";
    }

    @GetMapping("/editPassword")
    public String getEditPasswordPage(Model model){
        HashMap<String,String> errors = new HashMap<>();
        model.addAttribute("errors",errors);
        return "editPassword";
    }

    @PostMapping("/edit/password")
    public String editPasswordUser(@RequestParam("curpass") String password, @RequestParam("newpass") String newPassword, Model model){
        HashMap<String,String> errors = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users user = serviceUsers.findByEmail(authentication.getName());
        if (StringUtils.isBlank(password)){
            errors.put("curpass","Значение не может быть пустым");
        }
        else if (!passwordEncoder.matches(password,user.getPassword())){
            errors.put("curpass","Пароли не совпадают");
        }


        if(StringUtils.isBlank(newPassword)){
            errors.put("newpass","Значение не может быть пустым");
        }

        if (!errors.isEmpty()){
            model.addAttribute("errors",errors);
            return "editPassword";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        serviceUsers.save(user);
        return "redirect:/logout";
    }
}