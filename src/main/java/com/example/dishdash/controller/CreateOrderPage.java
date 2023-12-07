package com.example.dishdash.controller;

import com.example.dishdash.entity.Cart;
import com.example.dishdash.entity.OrderUser;
import com.example.dishdash.entity.Users;
import com.example.dishdash.service.ServiceCart;
import com.example.dishdash.service.ServiceOrderUser;
import com.example.dishdash.service.ServiceUsers;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
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
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class CreateOrderPage {
    private final ServiceUsers serviceUsers;
    private final ServiceOrderUser serviceOrderUser;
    private final ServiceCart serviceCart;

    @GetMapping("/")
    public String getOrderPage(Model model, Principal principal) {
        HashMap<String, String> errors = new HashMap<>();
        Users user = getAuthorized(principal);

        model.addAttribute("carts", user.getCarts());
        model.addAttribute("user", user);
        model.addAttribute("errors", errors);
        return "createOrderPage";
    }

    @PostMapping("/add")
    @Transactional
    @Modifying
    public String createNewOrder(@RequestParam("city") String city, @RequestParam("contact") String contact, @RequestParam("phone") String phone,
                                 @RequestParam("address") String address, @RequestParam("home") String home, @RequestParam("apartments") String apartments,
                                 @RequestParam("driveway") String driveway, Model model,Principal principal) {
        Users user = getAuthorized(principal);
        HashMap<String, String> errors = new HashMap<>();
        if (StringUtils.isBlank(city)) {
            errors.put("city", "Значение не может быть пустым");
        }
        if (StringUtils.isBlank(contact)) {
            errors.put("contact", "Значение не может быть пустым");
        }
        if (StringUtils.isBlank(address)) {
            errors.put("address", "Значение не может быть пустым");
        }
        if (StringUtils.isBlank(home)) {
            errors.put("home", "Значение не может быть пустым");
        }
        if (StringUtils.isBlank(apartments)) {
            errors.put("apartments", "Значение не может быть пустым");
        }
        if (StringUtils.isBlank(driveway)) {
            errors.put("driveway", "Значение не может быть пустым");
        }

        if (StringUtils.isBlank(phone)) {
            errors.put("phone", "Значение не может быть пустым");
        } else if (phone.length() != 11) {
            errors.put("phone", "Номер был введен не правильно");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("carts", user.getCarts());
            model.addAttribute("user", user);
            return "createOrderPage";
        } else {
            List<Cart> carts = user.getCarts();
            user.setCarts(null);
            serviceUsers.save(user);
            for (Cart cart : carts) {
                serviceCart.delete(cart);
            }
            serviceOrderUser.save(new OrderUser(Long.parseLong(phone), "Заказ готовится", "Улица: " + address + " ,дом: " + home + " ,кв: " + apartments + " ,подъезд: " + driveway));
            return "redirect:/";
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