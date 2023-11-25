package com.example.dishdash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfilePage {
    @GetMapping("/")
    public String getProfilePage(){
        return "profile";
    }

    @GetMapping("/editContact")
    public String getEditContactPage(){
        return "editContactInfo";
    }

    @GetMapping("/editPassword")
    public String getEditPasswordPage(){
        return "editPassword";
    }
}