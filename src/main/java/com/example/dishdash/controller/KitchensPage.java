package com.example.dishdash.controller;

import com.example.dishdash.entity.Food;
import com.example.dishdash.entity.Kitchen;
import com.example.dishdash.service.ServiceCart;
import com.example.dishdash.service.ServiceFood;
import com.example.dishdash.service.ServiceKitchen;
import com.example.dishdash.service.ServiceUsers;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/kitchen")
@AllArgsConstructor
public class KitchensPage {
    private final ServiceKitchen serviceKitchen;

    @GetMapping("/")
    public String getPage(Model model){
        List<Kitchen> kitchens = serviceKitchen.findAll();
        model.addAttribute("kitchens", kitchens);
        return "kitchens";
    }
}