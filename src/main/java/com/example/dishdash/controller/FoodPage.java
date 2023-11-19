package com.example.dishdash.controller;

import com.example.dishdash.entity.Food;
import com.example.dishdash.entity.Kitchen;
import com.example.dishdash.service.ServiceFood;
import com.example.dishdash.service.ServiceKitchen;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/food")
@AllArgsConstructor
public class FoodPage {
    private final ServiceKitchen serviceKitchen;
    private final ServiceFood serviceFood;

    @GetMapping(path = "/")
    public String getFoodPage(@RequestParam(required = false,name = "name_kitchen") String nameKitchen, @RequestParam(required = false, name = "name_category") String nameCategory, Model model){
        List<Kitchen> kitchens = serviceKitchen.findAll();
        if (!StringUtils.isBlank(nameKitchen) && !StringUtils.isBlank(nameCategory)){
            List<Food> foods = serviceFood.findAllByNameCategoryAndKitchen(nameKitchen,nameCategory);
            model.addAttribute("foods",foods);
        }
        else if (StringUtils.isBlank(nameKitchen) && !StringUtils.isBlank(nameCategory)){
            List<Food> foods = serviceFood.findAllByNameCategory(nameCategory);
            model.addAttribute("foods",foods);
        }
        else if (StringUtils.isBlank(nameKitchen) && StringUtils.isBlank(nameCategory)){
            List<Food> foods = serviceFood.findAllByNameCategory("Рекомендации");
            model.addAttribute("foods",foods);
        }
        else if(!StringUtils.isBlank(nameKitchen) && StringUtils.isBlank(nameCategory)){
            List<Food> foods = serviceFood.findAllByNameCategory("Рекомендации");
            model.addAttribute("foods",foods);
        }
        model.addAttribute("kitchens",kitchens);
        model.addAttribute("nameKitchen",nameKitchen);
        return "food";
    }
}
