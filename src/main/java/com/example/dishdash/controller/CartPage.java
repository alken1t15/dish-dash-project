package com.example.dishdash.controller;

import com.example.dishdash.entity.Cart;
import com.example.dishdash.entity.Food;
import com.example.dishdash.entity.Users;
import com.example.dishdash.service.ServiceCart;
import com.example.dishdash.service.ServiceFood;
import com.example.dishdash.service.ServiceUsers;
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

import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartPage {
    private final ServiceUsers users;
    private final ServiceFood serviceFood;
    private final ServiceCart serviceCart;
    @GetMapping("/")
    public String getCartPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (!authentication.isAuthenticated()) {
            username = authentication.getName();
        }
        else {
            WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
            username = webAuthenticationDetails.getRemoteAddress();
        }
        Users user = users.findByEmail(username);
        if (user== null){
            user = users.save(new Users(username));
        }

        model.addAttribute("carts",user.getCarts());
        return "cart";
    }

    @PostMapping("/add")
    public String addInCart(@RequestParam("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (!authentication.isAuthenticated()) {
             username = authentication.getName();
        }
        else {
            WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
            username = webAuthenticationDetails.getRemoteAddress();
        }
        Users user = users.findByEmail(username);
        if (user == null){
          user =  users.save(new Users(username));
        }
        Food food = serviceFood.findById(id);
        Cart cart = serviceCart.findByUserAndFood(user.getId(),food.getId());
        if (cart!= null){
            cart.setCount(cart.getCount()+1);
            Long temp = cart.getFood().getPrice() * cart.getCount();
            cart.setTotalPrice(temp);
        }
        else {
            cart = new Cart(user,food,1, food.getPrice());
        }
        serviceCart.save(cart);
        return "redirect:/";
    }

    @PostMapping("/edit")
    public void editCartPage(@RequestParam("id") Long id,@RequestParam("setting") String setting){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication.isAuthenticated()) {
            username = authentication.getName();
        }
        else {
            WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
            username = webAuthenticationDetails.getRemoteAddress();
        }
        Users user = users.findByEmail(username);
        List<Cart> carts = user.getCarts();
        Cart cartUser = null;
        for (Cart cart : carts){
            if (cart.getId() == id){
                cartUser = cart;
            }
        }
        switch (setting){
           case  "plus" -> {cartUser.setCount(cartUser.getCount()+1);
               serviceCart.save(cartUser);}
            case "minus" -> {
                if (cartUser.getCount()==1){
                    serviceCart.delete(cartUser);
                }else {
                    cartUser.setCount(cartUser.getCount() - 1);
                    serviceCart.save(cartUser);
                }
            }
            case "delete" -> serviceCart.delete(cartUser);
        }
    }
}