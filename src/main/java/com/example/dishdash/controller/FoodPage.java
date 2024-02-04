package com.example.dishdash.controller;

import com.example.dishdash.entity.Cart;
import com.example.dishdash.entity.Food;
import com.example.dishdash.entity.Kitchen;
import com.example.dishdash.entity.Users;
import com.example.dishdash.service.ServiceCart;
import com.example.dishdash.service.ServiceFood;
import com.example.dishdash.service.ServiceKitchen;
import com.example.dishdash.service.ServiceUsers;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/food")
@AllArgsConstructor
public class FoodPage {
    private final ServiceKitchen serviceKitchen;
    private final ServiceFood serviceFood;
    private final ServiceUsers serviceUsers;
    private final ServiceCart serviceCart;

    @GetMapping(path = "/")
    public String getFoodPage(@RequestParam(name = "name_kitchen") String nameKitchen, @RequestParam(required = false, name = "name_category") String nameCategory,Model model, HttpSession httpSession, Principal principal) {
//        List<Kitchen> kitchens = serviceKitchen.findAll();
        Kitchen kitchen = serviceKitchen.findByName(nameKitchen);
 //       List<Food> foods = serviceFood.findAllByNameCategoryAndKitchenCustom(nameCategory,nameKitchen);
//        model.addAttribute("foods", foods);
//        System.out.println(nameKitchen);
//        if (!StringUtils.isBlank(nameKitchen) && !StringUtils.isBlank(nameCategory)) {
//            List<Food> foods = serviceFood.findAllByNameCategoryAndKitchen(nameKitchen, nameCategory);
//            model.addAttribute("foods", foods);
//        } else if (StringUtils.isBlank(nameKitchen) && !StringUtils.isBlank(nameCategory)) {
//            List<Food> foods = serviceFood.findAllByNameCategory(nameCategory);
//            model.addAttribute("foods", foods);
//        } else if (StringUtils.isBlank(nameKitchen) && StringUtils.isBlank(nameCategory)) {
//            List<Food> foods = serviceFood.findAllByNameCategory("Рекомендации");
//            List<Food> foodss = serviceFood.findAllByNameCategoryAndKitchen("Рекомендации");
//            model.addAttribute("foods", foods);
 //       }// else if (!StringUtils.isBlank(nameKitchen) && StringUtils.isBlank(nameCategory)) {
//            List<Food> foods = serviceFood.findAllByNameCategory("Рекомендации");
//            model.addAttribute("foods", foods);
//        }
//        if (nameCategory == null) {
//            nameCategory = "Рекомендации";
//        }
//        model.addAttribute("nameKitchen", kitchen.getNameRu());
     //   model.addAttribute("kitchens", kitchens);
//        model.addAttribute("nameKitchen", nameKitchen);
//        model.addAttribute("nameCategory", nameCategory);
        if (principal == null) {
            if (httpSession.getAttribute("store") == null) {
                httpSession.setAttribute("store", new ArrayList<Cart>());
            }
            ArrayList<Cart> list = (ArrayList<Cart>) httpSession.getAttribute("store");
            model.addAttribute("count", list.size());
        } else {
            String username = principal.getName();
            Users user = serviceUsers.findByEmail(username);
            model.addAttribute("count", user.getCarts().size());
        }
        model.addAttribute("kitchen",kitchen);
        return "food";
    }

    @PostMapping("/add")
    public String addInCart(@RequestParam("id") Long id,@RequestParam(required = true, name = "name_kitchen") String nameKitchen, @RequestParam(required = false, name = "name_category") String nameCategory, HttpSession httpSession, Principal principal,Model model) {
//        List<Kitchen> kitchens = serviceKitchen.findAll();
//        if (!StringUtils.isBlank(nameKitchen) && !StringUtils.isBlank(nameCategory)) {
////            List<Food> foods = serviceFood.findAllByNameCategoryAndKitchen(nameKitchen, nameCategory);
////            model.addAttribute("foods", foods);
//        } else if (StringUtils.isBlank(nameKitchen) && !StringUtils.isBlank(nameCategory)) {
//            List<Food> foods = serviceFood.findAllByNameCategory(nameCategory);
//            model.addAttribute("foods", foods);
//        } else if (StringUtils.isBlank(nameKitchen) && StringUtils.isBlank(nameCategory)) {
//            List<Food> foods = serviceFood.findAllByNameCategory("Рекомендации");
//            model.addAttribute("foods", foods);
//        } else if (!StringUtils.isBlank(nameKitchen) && StringUtils.isBlank(nameCategory)) {
//            List<Food> foods = serviceFood.findAllByNameCategory("Рекомендации");
//            model.addAttribute("foods", foods);
//        }
//        if (nameCategory == null) {
//            nameCategory = "Рекомендации";
//        }
        Kitchen kitchen = serviceKitchen.findByName(nameKitchen);
        model.addAttribute("kitchen", kitchen);
//        model.addAttribute("nameKitchen", nameKitchen);
//        model.addAttribute("nameCategory", nameCategory);
        Food food = serviceFood.findById(id);
        if (principal == null) {
            if (httpSession.getAttribute("store") == null) {
                httpSession.setAttribute("store", new ArrayList<Cart>());
            }
            ArrayList<Cart> list = (ArrayList<Cart>) httpSession.getAttribute("store");
            for (Cart cart : list) {
                if (cart.getFood().equals(food)) {
                    cart.setCount(cart.getCount() + 1);
                    Long temp = cart.getFood().getPrice() * cart.getCount();
                    cart.setTotalPrice(temp);
                    return "/food";
                }
            }
            list.add(new Cart(null, food, 1, food.getPrice()));
            return "/food";
        } else {
            String username = principal.getName();
            Users user = serviceUsers.findByEmail(username);
            Cart cart = serviceCart.findByUserAndFood(user.getId(), food.getId());
            if (cart != null) {
                cart.setCount(cart.getCount() + 1);
                Long temp = cart.getFood().getPrice() * cart.getCount();
                cart.setTotalPrice(temp);
            } else {
                cart = new Cart(user, food, 1, food.getPrice());
            }
            serviceCart.save(cart);
            return "/food";
        }
    }
}