package com.example.dishdash.controller;

import com.example.dishdash.entity.Cart;
import com.example.dishdash.entity.Food;
import com.example.dishdash.entity.Kitchen;
import com.example.dishdash.entity.Users;
import com.example.dishdash.service.ServiceFood;
import com.example.dishdash.service.ServiceHistory;
import com.example.dishdash.service.ServiceKitchen;
import com.example.dishdash.service.ServiceUsers;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainPage {
    private final ServiceKitchen serviceKitchen;
    private final ServiceFood serviceFood;
    private final ServiceUsers serviceUsers;
    private final ServiceHistory serviceHistory;

    @GetMapping("/")
    public String getMainPage(Model model, HttpSession httpSession, Principal principal){
        List<Kitchen> kitchens = serviceKitchen.findAll();
        model.addAttribute("kitchens",kitchens);
        if (principal == null) {
            if (httpSession.getAttribute("store") == null) {
                httpSession.setAttribute("store", new ArrayList<Cart>());
            }
            ArrayList<Cart> list = (ArrayList<Cart>) httpSession.getAttribute("store");
            model.addAttribute("count", list.size());
            List<Food> foods = serviceFood.findAllByNameCategory("Рекомендации");
            model.addAttribute("foods",foods);
        } else {
            String username = principal.getName();
            Users user = serviceUsers.findByEmail(username);
            List<Food> foods = serviceHistory.findByPopularFoodUser();
            model.addAttribute("foods",foods);
            model.addAttribute("count", user.getCarts().size());
        }
        return "index";
    }
}