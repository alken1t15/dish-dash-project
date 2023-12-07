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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartPage {
    private final ServiceUsers serviceUsers;
    private final ServiceFood serviceFood;
    private final ServiceCart serviceCart;

    @GetMapping("/")
    public String getCartPage(Model model, HttpSession httpSession, Principal principal) {
        Users user = getAuthorized(principal);
        System.out.println(httpSession.getAttribute("store"));
        System.out.println(httpSession);

        model.addAttribute("carts", user.getCarts());
        return "cart";
    }

    @PostMapping("/add")
    public String addInCart(@RequestParam("id") Long id, HttpSession httpSession, Principal principal) {
//        Users user = getAuthorized(principal);

        if (principal == null) {
            if (httpSession.getAttribute("store") == null) {
                httpSession.setAttribute("store", new ArrayList<String>());
            }
            ArrayList<String> list = (ArrayList<String>) httpSession.getAttribute("store");
            list.add("fsdfsdf");
            System.out.println(list);
            return "redirect:/";
        } else {
            Users user = serviceUsers.findByEmail(principal.getName());
            Food food = serviceFood.findById(id);
            Cart cart = serviceCart.findByUserAndFood(user.getId(), food.getId());
            if (cart != null) {
                cart.setCount(cart.getCount() + 1);
                Long temp = cart.getFood().getPrice() * cart.getCount();
                cart.setTotalPrice(temp);
            } else {
                cart = new Cart(user, food, 1, food.getPrice());
            }
            serviceCart.save(cart);
            return "redirect:/";
        }
    }



    @PostMapping("/edit")
    public void editCartPage(@RequestParam("id") Long id, @RequestParam("setting") String setting) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication.isAuthenticated()) {
            username = authentication.getName();
        } else {
            WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
            username = webAuthenticationDetails.getRemoteAddress();
        }
        Users user = serviceUsers.findByEmail(username);
        List<Cart> carts = user.getCarts();
        Cart cartUser = null;
        for (Cart cart : carts) {
            if (cart.getId() == id) {
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
                    serviceCart.delete(cartUser);
                } else {
                    cartUser.setCount(cartUser.getCount() - 1);
                    serviceCart.save(cartUser);
                }
            }
            case "delete" -> serviceCart.delete(cartUser);
        }
    }

    private Users getAuthorized(Principal principal) {
        String username;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (principal != null) {
            username = principal.getName();
        } else {
            WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
            username = webAuthenticationDetails.getRemoteAddress();
        }
        Users user = serviceUsers.findByEmail(username);
        if (user == null) {
            user = serviceUsers.save(new Users(username));
        }
        return user;
    }
}
