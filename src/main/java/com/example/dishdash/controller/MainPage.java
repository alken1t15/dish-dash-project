package com.example.dishdash.controller;

import com.example.dishdash.entity.Food;
import com.example.dishdash.entity.Kitchen;
import com.example.dishdash.service.ServiceFood;
import com.example.dishdash.service.ServiceKitchen;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainPage {
    private final ServiceKitchen serviceKitchen;
    private final ServiceFood serviceFood;

    @GetMapping("/")
    public String getMainPage(Model model){
        List<Kitchen> kitchens = serviceKitchen.findAll();
        List<Food> foods = serviceFood.findAllByNameCategory("Рекомендации");
        model.addAttribute("kitchens",kitchens);
        model.addAttribute("foods",foods);

        return "index";
    }
}