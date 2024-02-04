package com.example.dishdash.controller;

import com.example.dishdash.entity.Cart;
import com.example.dishdash.entity.Food;
import com.example.dishdash.entity.Users;
import com.example.dishdash.service.ServiceCart;
import com.example.dishdash.service.ServiceFood;
import com.example.dishdash.service.ServiceUsers;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartPage {
    private final ServiceUsers serviceUsers;
    private final ServiceFood serviceFood;
    private final ServiceCart serviceCart;

    @GetMapping("/")
    public String getCartPage(Model model, HttpSession httpSession, Principal principal) {
        if (principal == null) {
            if (httpSession.getAttribute("store") == null) {
                httpSession.setAttribute("store", new ArrayList<Cart>());
            }
            ArrayList<Cart> list = (ArrayList<Cart>) httpSession.getAttribute("store");
            model.addAttribute("carts", list);
            model.addAttribute("count", list.size());
        } else {
            String username = principal.getName();
            Users user = serviceUsers.findByEmail(username);
            model.addAttribute("carts", user.getCarts());
            model.addAttribute("count", user.getCarts().size());
        }
        return "cart";
    }

    @PostMapping("/add")
    public String addInCart(@RequestParam("id") Long id, HttpSession httpSession, Principal principal) {
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
                    return "redirect:/#category";
                }
            }
            list.add(new Cart(null, food, 1, food.getPrice()));
            return "redirect:/#category";
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
            return "redirect:/#category";
        }
    }


    @PostMapping("/edit")
    public String editCartPage(@RequestBody Map<String, String> requestMap, HttpSession httpSession, Principal principal) {
        String id = requestMap.get("id");
        String setting = requestMap.get("setting");
        if (principal == null) {
            ArrayList<Cart> list = (ArrayList<Cart>) httpSession.getAttribute("store");
            for (Cart cart : list) {
                if (cart.getFood().getId() == Long.parseLong(id)) {
                    switch (setting) {
                        case "plus" -> cart.setCount(cart.getCount() + 1);
                        case "minus" -> {
                            if (cart.getCount() == 1) {
                                list.remove(cart);
                            } else {
                                cart.setCount(cart.getCount() - 1);
                            }
                        }
                        case "delete" -> {
                            list.remove(cart);
                        }

                    }
                }
            }
        } else {
            String username = principal.getName();
            Users user = serviceUsers.findByEmail(username);
            List<Cart> carts = user.getCarts();
            Cart cartUser = null;
            for (Cart cart : carts) {
                if (cart.getFood().getId() == Long.parseLong(id)) {
                    cartUser = cart;
                }
            }
            switch (setting) {
                case "plus" -> {
                    cartUser.setCount(cartUser.getCount() + 1);
                    serviceCart.save(cartUser);
                }
                case "minus" -> {
                    if (cartUser.getCount() == 1) {
                        cartUser.setUser(null);
                        serviceCart.delete(cartUser);
                    } else {
                        cartUser.setCount(cartUser.getCount() - 1);
                        serviceCart.save(cartUser);
                    }
                }
                case "delete" ->{
                    cartUser.setUser(null);
                    serviceCart.delete(cartUser);
                }
            }
        }
        return "/cart";
    }
}